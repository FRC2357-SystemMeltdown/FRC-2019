/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Subsystems;

import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.Commands.DriveProportional;
import frc.robot.Other.Utility;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.ctre.phoenix.sensors.PigeonIMU_StatusFrame;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * Drive subsystem, controls 4 motors, 2 encoders, and 1 gyro.
 */
public class DriveSub extends SubsystemBase {
  public WPI_TalonSRX leftMaster = new WPI_TalonSRX(RobotMap.CAN_ID_LEFT_DRIVE);
  public WPI_TalonSRX leftSlave = new WPI_TalonSRX(RobotMap.CAN_ID_LEFT_DRIVE_SLAVE);
  public WPI_TalonSRX rightMaster = new WPI_TalonSRX(RobotMap.CAN_ID_RIGHT_DRIVE);
  public WPI_TalonSRX rightSlave = new WPI_TalonSRX(RobotMap.CAN_ID_RIGHT_DRIVE_SLAVE);

  private PigeonIMU pidgey = new PigeonIMU(RobotMap.CAN_ID_PIGEON_IMU);
  private double[] yawPitchRoll = new double[RobotMap.GYRO_AXIS_TOTAL];
  private GyroPIDInterface gyroPidIntf;
  private PIDController gyroPid;

  private class GyroPIDInterface implements PIDSource, PIDOutput {
    public double output = 0;

    @Override
    public void pidWrite(double output) {
      this.output = output;
    }

    @Override
    public void setPIDSourceType(PIDSourceType pidSource) {

    }

    @Override
    public PIDSourceType getPIDSourceType() {
      return PIDSourceType.kDisplacement;
    }

    @Override
    public double pidGet() {
      return getYaw(false);
    }
  }

  public void configure() {
    int timeout = RobotMap.TALON_TIMEOUT_MS;

    leftMaster.set(ControlMode.PercentOutput, 0.0);
    rightMaster.set(ControlMode.PercentOutput, 0.0);

    leftMaster.configFactoryDefault();
    leftSlave.configFactoryDefault();
    rightMaster.configFactoryDefault();
    rightSlave.configFactoryDefault();
    pidgey.configFactoryDefault();

    leftMaster.setNeutralMode(NeutralMode.Brake);
    rightMaster.setNeutralMode(NeutralMode.Brake);

    leftMaster.configSelectedFeedbackSensor(
      FeedbackDevice.QuadEncoder,
      RobotMap.TALON_PID_PRIMARY,
      timeout
    );
    rightMaster.configSelectedFeedbackSensor(
      FeedbackDevice.QuadEncoder,
      RobotMap.TALON_PID_PRIMARY,
      timeout
    );

    leftMaster.setInverted(false);
    leftMaster.setSensorPhase(false);
    leftSlave.setInverted(false);
    leftSlave.follow(leftMaster);

    rightMaster.setInverted(true);
    rightMaster.setSensorPhase(false);
    rightSlave.setInverted(true);
    rightSlave.follow(rightMaster);

    leftMaster.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 20);
    rightMaster.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 20);
    pidgey.setStatusFramePeriod(PigeonIMU_StatusFrame.CondStatus_9_SixDeg_YPR, 5);

    leftMaster.configNeutralDeadband(RobotMap.DRIVE_MOTOR_DEADBAND);
    rightMaster.configNeutralDeadband(RobotMap.DRIVE_MOTOR_DEADBAND);

    leftMaster.configOpenloopRamp(RobotMap.DRIVE_RAMP_SECONDS);
    rightMaster.configOpenloopRamp(RobotMap.DRIVE_RAMP_SECONDS);

    leftMaster.configNominalOutputForward(0, timeout);
    leftMaster.configNominalOutputReverse(0, timeout);
		leftMaster.configPeakOutputReverse(-1.0, timeout);
    leftMaster.configPeakOutputForward(+1.0, timeout);
    rightMaster.configNominalOutputForward(0, timeout);
    rightMaster.configNominalOutputReverse(0, timeout);
		rightMaster.configPeakOutputForward(+1.0, timeout);
    rightMaster.configPeakOutputReverse(-1.0, timeout);

    Utility.configTalonPID(rightMaster, RobotMap.TALON_SLOT_VELOCITY, RobotMap.PID_DRIVE_SPEED);
    Utility.configTalonPID(rightMaster, RobotMap.TALON_SLOT_DISTANCE, RobotMap.PID_DRIVE_POS);

    /**
		 * 1ms per loop.  PID loop can be slowed down if need be.
		 * For example,
		 * - if sensor updates are too slow
		 * - sensor deltas are very small per update, so derivative error never gets large enough to be useful.
		 * - sensor movement is very slow causing the derivative error to be near zero.
		 */
    int closedLoopTimeMs = 1;

    rightMaster.configClosedLoopPeriod(RobotMap.TALON_SLOT_DISTANCE, closedLoopTimeMs, timeout);
    rightMaster.configClosedLoopPeriod(RobotMap.TALON_SLOT_TURNING, closedLoopTimeMs, timeout);
    rightMaster.configClosedLoopPeriod(RobotMap.TALON_SLOT_VELOCITY, closedLoopTimeMs, timeout);

    resetSensors();

    /*
    gyroPidIntf = new GyroPIDInterface();
    gyroPid = new PIDController(RobotMap.PID_GYRO.kp, RobotMap.PID_GYRO.ki, RobotMap.PID_GYRO.kd, gyroPidIntf,
        gyroPidIntf, 0.01);
    gyroPid.setOutputRange(-0.4, 0.4);
    gyroPid.enable();
    */
  }

  public void resetSensors() {
    rightMaster.getSensorCollection().setQuadraturePosition(0, 0);
    leftMaster.getSensorCollection().setQuadraturePosition(0, 0);
    /*
    gyro.setYaw(0);
    gyro.setAccumZAngle(0);
    */
  }

  @Override
  public void initDefaultCommand() {
    setFailsafeActive(isFailsafeActive());
  }

  @Override
  public void setFailsafeActive(boolean failsafeActive) {
    super.setFailsafeActive(failsafeActive);

    if (failsafeActive) {
      setDefaultCommand(new DriveProportional());
    } else {
      // TODO: Make this the encoder drive command when it's ready.
      setDefaultCommand(new DriveProportional());
    }
  }

  public int getForwardError() {
    return rightMaster.getClosedLoopError();
  }

  /**
   *
   * @param speed The desired speed in inches/sec
   * @param turn The desired turn rate in degrees/sec
   */
  public void PIDDrive(double speed, double turn) {
    if (Robot.getInstance().isFailsafeActive()) {
      System.err.println("DriveSub: Cannot use PIDDrive while in failsafe");
      return;
    }

    // TODO: Add this back in after refactoring.
    /*
    double degrees = 90 * (Robot.OI.getDriverController().getBButton() ? 1 : 0);
    gyroPid.setSetpoint(degrees);
    double turnFeedback = gyroPidIntf.output;
    leftMaster.set(ControlMode.Velocity, 0, DemandType.ArbitraryFeedForward, -turnFeedback * 1023);
    rightMaster.set(ControlMode.Velocity, 0, DemandType.ArbitraryFeedForward, turnFeedback * 1023);
    */
  }

  public void rotateToAngle(double degrees) {
    if (Robot.getInstance().isFailsafeActive()) {
      System.err.println("DriveSub: Cannot use rotateToAngle while in failsafe");
      return;
    }

    /*
    leftMaster.selectProfileSlot(SPEED_PID_SLOT, 0);
    rightMaster.selectProfileSlot(SPEED_PID_SLOT, 0);

    gyroPid.setSetpoint(degrees);
    double turnFeedback = gyroPidIntf.output;
    leftMaster.set(ControlMode.Velocity, 0, DemandType.ArbitraryFeedForward, -turnFeedback * 1023);
    rightMaster.set(ControlMode.Velocity, 0, DemandType.ArbitraryFeedForward, turnFeedback * 1023);
    */
  }

  public void moveForwardDistance(double inches) {
    if (Robot.getInstance().isFailsafeActive()) {
      System.err.println("DriveSub: Cannot use moveForwardDistance while in failsafe");
      return;
    }

    resetSensors();
    leftMaster.selectProfileSlot(RobotMap.TALON_SLOT_DISTANCE, RobotMap.TALON_PID_PRIMARY);
    rightMaster.selectProfileSlot(RobotMap.TALON_SLOT_DISTANCE, RobotMap.TALON_PID_PRIMARY);

    double targetPosition = inches * RobotMap.ENCODER_TICKS_PER_ROTATION / RobotMap.WHEEL_CIRCUMFERENCE_INCHES;
    rightMaster.set(ControlMode.Position, targetPosition);
    leftMaster.follow(rightMaster, FollowerType.AuxOutput1);
    //rightMaster.set(ControlMode.Position, targetPosition);
  }

  /**
   *
   * @return The gyro's current yaw value in degrees
   */
  public double getYaw(boolean moduloOutput) {
    pidgey.getYawPitchRoll(yawPitchRoll);
    double yaw = yawPitchRoll[RobotMap.GYRO_AXIS_YAW];
    if(moduloOutput) {
      if(yaw >= 180){
        yaw -= 360;
      }
      if(yaw <= -180){
        yaw += 360;
      }
    }
    return yaw;
  }

  public void tankDrive(double leftSpeed, double rightSpeed) {
    leftMaster.set(ControlMode.PercentOutput, leftSpeed);
    rightMaster.set(ControlMode.PercentOutput, rightSpeed);
  }
}
