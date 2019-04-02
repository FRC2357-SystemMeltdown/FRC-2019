package frc.robot.Commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.Other.PIDValues;
import frc.robot.Other.Utility;
import frc.robot.Subsystems.VisionSub;
import frc.robot.Subsystems.VisionSub.TargetType;
import frc.robot.Subsystems.VisionSub.VisionTarget;

public class AutoSquareUpCommand extends Command {
  private class TurnPIDSource implements PIDSource, PIDOutput {
    @Override
    public void setPIDSourceType(PIDSourceType pidSource) {
      System.err.println("TurnPIDSource: Something tried to setPIDSourceType!");
    }

    @Override
    public PIDSourceType getPIDSourceType() {
      return PIDSourceType.kDisplacement;
    }

    @Override
    public double pidGet() {
      VisionTarget target = Robot.VISION_SUB.getLastVisibleTarget();
      return target != null ? target.getX() : 0;
    }

    @Override
    public void pidWrite(double output) {
      int encoderDifferential = (int) output;

      int speed = Robot.OI.getEncoderSpeed();

      int leftVelocity = speed - encoderDifferential;
      int rightVelocity = speed + encoderDifferential;

      Utility.clamp(leftVelocity, -RobotMap.MAX_ENCODER_VELOCITY, RobotMap.MAX_ENCODER_VELOCITY);
      Utility.clamp(rightVelocity, -RobotMap.MAX_ENCODER_VELOCITY, RobotMap.MAX_ENCODER_VELOCITY);

      // TODO: Abstract this within DriveSub.
      Robot.DRIVE_SUB.leftMaster.set(ControlMode.Velocity, leftVelocity);
      Robot.DRIVE_SUB.rightMaster.set(ControlMode.Velocity, rightVelocity);
    }
  }

  private VisionSub.TargetType targetType;
  private TurnPIDSource turnPidSource;
  private PIDController turnPid;
  private double[] targetRotationArray;
  private int targetRotationArrayIndex;
  private double targetDistance;
  private double desiredDistance;

  public AutoSquareUpCommand(TargetType targetType, double desiredDistance) {
    requires(Robot.DRIVE_SUB);
    requires(Robot.VISION_SUB);
    setName("SQUARE UP");

    this.targetType = targetType;
    this.desiredDistance = desiredDistance;
    this.turnPidSource = new TurnPIDSource();
    this.targetRotationArray = new double[RobotMap.PID_VISION_TARGET_ROTATION_SAMPLES];
    this.targetRotationArrayIndex = 0;
    this.targetDistance = Double.MAX_VALUE;

    PIDValues yawPIDValues = RobotMap.PID_ADJUST_YAW;

    // TODO: Abstract this.
    turnPid = new PIDController(
      yawPIDValues.kp,
      yawPIDValues.ki,
      yawPIDValues.kd,
      turnPidSource,
      turnPidSource,
      0.05
    );
    turnPid.setOutputRange(-yawPIDValues.peak, yawPIDValues.peak);
  }

  @Override
  protected void initialize() {
    super.initialize();

    turnPid.reset();
    turnPid.setSetpoint(0);
    turnPid.enable();
  }

  protected double getTargetRotationAverage() {
    double sum = 0;
    for (double sample : targetRotationArray) {
      sum += sample;
    }
    return sum / targetRotationArray.length;
  }

  protected double calculateSetpoint() {
    double rotationMod = -getTargetRotationAverage() * RobotMap.PID_VISION_TARGET_ROTATION_TO_X_OFFSET;
    double setpoint = rotationMod;

    if (isClose()) {
      setpoint = -0.5;
    }

    //System.out.println("set: " + ((int)setpoint*100) + " rot: " + ((int)getTargetRotationAverage()*100) + " dist: " + ((int)targetDistance*100) + " err: " + turnPid.getError());
    return setpoint;
  }

  protected boolean isClose() {
    return targetDistance < desiredDistance + RobotMap.PID_VISION_TARGET_DISTANCE_CENTERING_THRESHOLD_INCHES;
  }

  @Override
  protected void execute() {
    VisionTarget currentTarget = Robot.VISION_SUB.acquireTarget(targetType);
    if (currentTarget != null) {
      targetDistance = currentTarget.getInchesFromTarget();
      targetRotationArray[targetRotationArrayIndex] = currentTarget.getTargetRotationDegrees();
      targetRotationArrayIndex = targetRotationArrayIndex < targetRotationArray.length - 1 ? targetRotationArrayIndex + 1 : 0;
    }
    turnPid.setSetpoint(calculateSetpoint());
  }

  @Override
  protected void end() {
    super.end();
    turnPid.disable();
  }

  @Override
  protected boolean isFinished() {
    boolean finished =
      targetDistance <= desiredDistance &&
      getTargetRotationAverage() < RobotMap.PID_VISION_TARGET_ROTATION_ACCURACY &&
      turnPid.getError() < RobotMap.PID_VISION_TURN_ERR_ACCURACY;

    return finished;
  }
}
