/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Subsystems;

import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.Commands.ProportionalDriveCommand;
import frc.robot.Other.Utility;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
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
 * Add your docs here.
 */
public class DriveSub extends SubsystemBase {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public WPI_TalonSRX leftMaster = new WPI_TalonSRX(RobotMap.CAN_ID_LEFT_DRIVE);
  public WPI_TalonSRX leftSlave = new WPI_TalonSRX(RobotMap.CAN_ID_LEFT_DRIVE_SLAVE);
  public WPI_TalonSRX rightMaster = new WPI_TalonSRX(RobotMap.CAN_ID_RIGHT_DRIVE);
  public WPI_TalonSRX rightSlave = new WPI_TalonSRX(RobotMap.CAN_ID_RIGHT_DRIVE_SLAVE);

  private PigeonIMU gyro = new PigeonIMU(RobotMap.CAN_ID_PIGEON_IMU);
  private double[] yawPitchRoll = new double[RobotMap.GYRO_AXIS_TOTAL];
  private GyroPIDInterface gyroPidIntf;
  private PIDController gyroPid;

  private static final int SPEED_PID_SLOT = 0;
  private static final int POS_PID_SLOT = 1;

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

  public DriveSub(){
    leftSlave.follow(leftMaster);
    rightSlave.follow(rightMaster);

    leftMaster.configFactoryDefault();
    rightMaster.configFactoryDefault();

    rightMaster.setInverted(true);
    rightSlave.setInverted(true);

    leftMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
    rightMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);

    rightMaster.setSensorPhase(false);

    rightMaster.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 5);
    leftMaster.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 5);
    gyro.setStatusFramePeriod(PigeonIMU_StatusFrame.CondStatus_9_SixDeg_YPR, 5);

    // configure PIDs
    Utility.configTalonPID(leftMaster, SPEED_PID_SLOT, RobotMap.PID_SPEED_LEFT_DRIVE);
    //Utility.configTalonPID(leftMaster, POS_PID_SLOT, RobotMap.PID_POS_LEFT_DRIVE);
    Utility.configTalonPID(rightMaster, SPEED_PID_SLOT, RobotMap.PID_SPEED_RIGHT_DRIVE);
    //Utility.configTalonPID(rightMaster, POS_PID_SLOT, RobotMap.PID_POS_RIGHT_DRIVE);

    // leftMaster.selectProfileSlot(SPEED_PID_SLOT, 0);
    // rightMaster.selectProfileSlot(SPEED_PID_SLOT, 0);

    leftMaster.configClosedloopRamp(5);
    rightMaster.configClosedloopRamp(5);

    leftMaster.configOpenloopRamp(RobotMap.DRIVE_RAMP_SECONDS);
    rightMaster.configOpenloopRamp(RobotMap.DRIVE_RAMP_SECONDS);

    leftMaster.setNeutralMode(NeutralMode.Brake);
    rightMaster.setNeutralMode(NeutralMode.Brake);

    leftMaster.configNeutralDeadband(RobotMap.DRIVE_MOTOR_DEADBAND);
    rightMaster.configNeutralDeadband(RobotMap.DRIVE_MOTOR_DEADBAND);

    resetSensors();

    gyroPidIntf = new GyroPIDInterface();
    gyroPid = new PIDController(RobotMap.PID_GYRO.kp, RobotMap.PID_GYRO.ki, RobotMap.PID_GYRO.kd, gyroPidIntf, gyroPidIntf, 0.01);
    gyroPid.setOutputRange(-0.4, 0.4);
    gyroPid.enable();
  }

  public void resetSensors() {
    // reset sensors
    rightMaster.getSensorCollection().setQuadraturePosition(0, 0);
    leftMaster.getSensorCollection().setQuadraturePosition(0, 0);
    gyro.setYaw(0);
    gyro.setAccumZAngle(0);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new ProportionalDriveCommand());
  }

  /**
   *
   * @param speed The desired speed in inches/sec
   * @param turn The desired turn rate in degrees/sec
   */
  public void PIDDrive(double speed, double turn) {
    double degrees = 90 * (Robot.OI.getDriverController().getBButton() ? 1 : 0);
    gyroPid.setSetpoint(degrees);
    double turnFeedback = gyroPidIntf.output;
    leftMaster.set(ControlMode.Velocity, 0, DemandType.ArbitraryFeedForward, -turnFeedback * 1023);
    rightMaster.set(ControlMode.Velocity, 0, DemandType.ArbitraryFeedForward, turnFeedback * 1023);
  }

  public void rotateToAngle(double degrees) {
    leftMaster.selectProfileSlot(SPEED_PID_SLOT, 0);
    rightMaster.selectProfileSlot(SPEED_PID_SLOT, 0);

    gyroPid.setSetpoint(degrees);
    double turnFeedback = gyroPidIntf.output;
    leftMaster.set(ControlMode.Velocity, 0, DemandType.ArbitraryFeedForward, -turnFeedback * 1023);
    rightMaster.set(ControlMode.Velocity, 0, DemandType.ArbitraryFeedForward, turnFeedback * 1023);
  }

  public void moveForwardDistance(double inches) {
    resetSensors();
    leftMaster.selectProfileSlot(POS_PID_SLOT, 0);
    rightMaster.selectProfileSlot(POS_PID_SLOT, 0);
    double encoderPosition = inches * RobotMap.ENCODER_TICKS_PER_ROTATION / RobotMap.WHEEL_CIRCUMFERENCE_INCHES;
    leftMaster.set(ControlMode.Position, encoderPosition);
    rightMaster.set(ControlMode.Position, encoderPosition);
  }

  /**
   *
   * @return The gyro's current yaw value in degrees
   */
  public double getYaw(boolean moduloOutput) {
    gyro.getYawPitchRoll(yawPitchRoll);
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

  /**
   * @param speedInchesPerSecond The desired speed in inches/sec
   * @return The speed converted to encoder ticks/100ms
   */
  private double calcMotorControlSpeed(double speedInchesPerSecond) {
    double speedInchesPer100ms = speedInchesPerSecond * 100 / RobotMap.MILLISECONDS_PER_SECOND;
    double speedRotationsPer100ms = speedInchesPer100ms / RobotMap.WHEEL_CIRCUMFERENCE_INCHES;
    double speedTicksPer100ms = speedRotationsPer100ms * RobotMap.ENCODER_TICKS_PER_ROTATION;
    return speedTicksPer100ms;
  }

  public void tankDrive(double leftSpeed, double rightSpeed) {
    leftMaster.set(ControlMode.PercentOutput, leftSpeed);
    rightMaster.set(ControlMode.PercentOutput, rightSpeed);
  }
}
