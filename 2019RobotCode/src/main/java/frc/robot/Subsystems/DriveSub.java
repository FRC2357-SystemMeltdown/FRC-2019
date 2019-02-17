/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Subsystems;

import frc.robot.RobotMap;
import frc.robot.Commands.ProportionalDriveCommand;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * Add your docs here.
 */
public class DriveSub extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public WPI_TalonSRX leftMaster = new WPI_TalonSRX(RobotMap.CAN_ID_LEFT_DRIVE);
  public WPI_TalonSRX leftSlave = new WPI_TalonSRX(RobotMap.CAN_ID_LEFT_DRIVE_SLAVE);
  public WPI_TalonSRX rightMaster = new WPI_TalonSRX(RobotMap.CAN_ID_RIGHT_DRIVE);
  public WPI_TalonSRX rightSlave = new WPI_TalonSRX(RobotMap.CAN_ID_RIGHT_DRIVE_SLAVE);
  public DifferentialDrive drive = new DifferentialDrive(leftMaster, rightMaster);

  private PigeonIMU gyro = new PigeonIMU(RobotMap.CAN_ID_PIGEON_IMU);
  private double[] yawPitchRoll = new double[RobotMap.GYRO_AXIS_TOTAL];
  public GyroPID gyroPID = new GyroPID();

  public DriveSub(){
    leftSlave.follow(leftMaster);
    rightSlave.follow(rightMaster);

    leftMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
    rightMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);

    configTalon(leftMaster,
     RobotMap.PID_P_LEFT_DRIVE,
     RobotMap.PID_I_LEFT_DRIVE,
     RobotMap.PID_D_LEFT_DRIVE,
     RobotMap.PID_F_LEFT_DRIVE);
    configTalon(rightMaster,
     RobotMap.PID_P_RIGHT_DRIVE,
     RobotMap.PID_I_RIGHT_DRIVE,
     RobotMap.PID_D_RIGHT_DRIVE,
     RobotMap.PID_F_RIGHT_DRIVE);
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
    gyroPID.setSetpoint(turn);

    double turnModifierInchesPerSecond = gyroPID.getOutput() * RobotMap.MAX_VELOCITY_INCHES_PER_SECOND;
    double left = speed - turnModifierInchesPerSecond;
    double right = speed + turnModifierInchesPerSecond;

    double leftTicksPer100ms = calcMotorControlSpeed(left);
    double rightTicksPer100ms = calcMotorControlSpeed(right);

    leftMaster.set(ControlMode.Velocity, leftTicksPer100ms);
    rightMaster.set(ControlMode.Velocity, rightTicksPer100ms);
  }

  /**
   *
   * @return The gyro's current Yaw value
   */
  public double getYaw() {
    gyro.getYawPitchRoll(yawPitchRoll);
    if(yawPitchRoll[RobotMap.GYRO_AXIS_YAW] >= 180){
      yawPitchRoll[RobotMap.GYRO_AXIS_YAW] -= 360;
    }
    if(yawPitchRoll[RobotMap.GYRO_AXIS_YAW] <= -180){
      yawPitchRoll[RobotMap.GYRO_AXIS_YAW] += 360;
    }
    return yawPitchRoll[RobotMap.GYRO_AXIS_YAW];
  }

  public void setTurnRateSetpoint(double setpoint) {
    gyroPID.setSetpoint(setpoint);
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

  /**
   *
   * @param talon The talon SRX to config
   * @param kP The proportional value to set
   * @param kI The interval value to set
   * @param kD The derivative value to set
   * @param kF The feedforward value to set
   */
  private void configTalon(WPI_TalonSRX talon, double kP, double kI, double kD, double kF) {
    talon.config_kP(0, kP);
    talon.config_kI(0, kI);
    talon.config_kD(0, kD);
    talon.config_kF(0, kF);
  }

}
