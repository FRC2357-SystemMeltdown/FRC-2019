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
import frc.robot.Subsystems.VisionSub.VisionTarget;

public class AutoCenterOnVisionTargetCommand extends Command {

  private class VisionCenterPIDSource implements PIDSource, PIDOutput {
    @Override
    public void setPIDSourceType(PIDSourceType pidSource) {
      System.err.println("VisionCenterPIDSource: Something tried to setPIDSourceType!");
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

      int leftVelocity = -encoderDifferential;
      int rightVelocity = +encoderDifferential;

      Utility.clamp(leftVelocity, -RobotMap.MAX_ENCODER_VELOCITY, RobotMap.MAX_ENCODER_VELOCITY);
      Utility.clamp(rightVelocity, -RobotMap.MAX_ENCODER_VELOCITY, RobotMap.MAX_ENCODER_VELOCITY);

      // TODO: Abstract this within DriveSub.
      Robot.DRIVE_SUB.leftMaster.set(ControlMode.Velocity, leftVelocity);
      Robot.DRIVE_SUB.rightMaster.set(ControlMode.Velocity, rightVelocity);
    }
  }

  private VisionSub.TargetType type;
  private double centerOffset;
  private VisionCenterPIDSource pidSource;
  private PIDController pid;

  public AutoCenterOnVisionTargetCommand(VisionSub.TargetType type, double centerOffset) {
    requires(Robot.DRIVE_SUB);
    requires(Robot.VISION_SUB);
    this.type = type;
    this.centerOffset = centerOffset;

    this.pidSource = new VisionCenterPIDSource();

    PIDValues yawPIDValues = RobotMap.PID_VISION_YAW;

    // TODO: Abstract this.
    pid = new PIDController(
      yawPIDValues.kp,
      yawPIDValues.ki,
      yawPIDValues.kd,
      pidSource,
      pidSource,
      0.05
    );
    pid.setOutputRange(-yawPIDValues.peak, yawPIDValues.peak);
  }

  @Override
  protected void initialize() {
    super.initialize();

    pid.reset();
    pid.setSetpoint(centerOffset);
    pid.enable();
  }

  @Override
  protected void execute() {
    Robot.VISION_SUB.acquireTarget(type);
  }

  @Override
  protected void end() {
    super.end();
    pid.disable();
  }

  @Override
  protected boolean isFinished() {
    VisionTarget target = Robot.VISION_SUB.getLastVisibleTarget();
    if (target != null) {
      double error = pid.getError();
      System.out.println("error: " + error);
      return Math.abs(error) <= RobotMap.PID_VISION_CENTER_ACCURACY;
    }
    return false;
  }
}
