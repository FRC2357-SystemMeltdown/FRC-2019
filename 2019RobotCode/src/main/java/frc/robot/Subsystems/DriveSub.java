/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Subsystems;

import frc.robot.RobotMap;
import frc.robot.Commands.ProportionalDriveCommand;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class DriveSub extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public WPI_TalonSRX leftMaster = new WPI_TalonSRX(RobotMap.CAN_ID_LEFT_DRIVE);
  public WPI_TalonSRX leftSlave = new WPI_TalonSRX(RobotMap.CAN_ID_LEFT_DRIVE_SLAVE);
  public SpeedControllerGroup left = new SpeedControllerGroup(leftMaster, leftSlave);
  public WPI_TalonSRX rightMaster = new WPI_TalonSRX(RobotMap.CAN_ID_RIGHT_DRIVE);
  public WPI_TalonSRX rightSlave = new WPI_TalonSRX(RobotMap.CAN_ID_RIGHT_DRIVE_SLAVE);
  public SpeedControllerGroup right = new SpeedControllerGroup(rightMaster, rightSlave);
  public DifferentialDrive drive = new DifferentialDrive(right, left);

  private PigeonIMU gyro = new PigeonIMU(RobotMap.CAN_ID_PIGEON_IMU);
  private double[] yawPitchRoll = new double[3];
  public GyroPID gyroPID = new GyroPID();

  private DriveTrainPID leftDriveTrainPID = new DriveTrainPID(
   RobotMap.PID_P_LEFT_DRIVE,
   RobotMap.PID_I_LEFT_DRIVE, 
   RobotMap.PID_D_LEFT_DRIVE, 
   leftMaster);
  private DriveTrainPID rightDriveTrainPID = new DriveTrainPID(
   RobotMap.PID_P_RIGHT_DRIVE, 
   RobotMap.PID_I_RIGHT_DRIVE, 
   RobotMap.PID_D_RIGHT_DRIVE, 
   rightMaster);

  public DriveSub(){
    //leftSlave.setInverted(true);
    //rightSlave.setInverted(true);
    leftMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
    rightMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new ProportionalDriveCommand());
  }

  public void PIDDrive(double speed, double turn){
    gyroPID.setSetpoint(turn);

    double left = speed - gyroPID.output;
    double right = speed + gyroPID.output;

    leftDriveTrainPID.setSetpoint(left);
    rightDriveTrainPID.setSetpoint(right);
    drive.tankDrive(leftDriveTrainPID.output, rightDriveTrainPID.output);
  }

  public double getYaw(){
    gyro.getYawPitchRoll(yawPitchRoll);
    if(yawPitchRoll[RobotMap.GYRO_AXIS_YAW] >= 180){
      yawPitchRoll[RobotMap.GYRO_AXIS_YAW] -= 360;
    }
    if(yawPitchRoll[RobotMap.GYRO_AXIS_YAW] <= -180){
      yawPitchRoll[RobotMap.GYRO_AXIS_YAW] += 360;
    }
    return yawPitchRoll[RobotMap.GYRO_AXIS_YAW];
  }

  public void setTurnRateSetpoint(double setpoint){
    gyroPID.setSetpoint(setpoint);
  }

}
