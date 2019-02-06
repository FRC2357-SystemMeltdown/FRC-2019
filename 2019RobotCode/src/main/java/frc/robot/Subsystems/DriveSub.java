/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Subsystems;

import frc.robot.RobotMap;
import frc.robot.Commands.SplitArcadeDriveCommand;
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


  public WPI_TalonSRX leftMaster = new WPI_TalonSRX(RobotMap.LEFT_DRIVE_CAN_ID);
  public WPI_TalonSRX leftSlave = new WPI_TalonSRX(RobotMap.LEFT_DRIVE_SLAVE_CAN_ID);
  public SpeedControllerGroup left = new SpeedControllerGroup(leftMaster, leftSlave);
  public WPI_TalonSRX rightMaster = new WPI_TalonSRX(RobotMap.RIGHT_DRIVE_CAN_ID);
  public WPI_TalonSRX rightSlave = new WPI_TalonSRX(RobotMap.RIGHT_DRIVE_SLAVE_CAN_ID);
  public SpeedControllerGroup right = new SpeedControllerGroup(rightMaster, rightSlave);
  public DifferentialDrive drive = new DifferentialDrive(right, left);

  private PigeonIMU gyro = new PigeonIMU(RobotMap.PIGEON_IMU_CAN_ID);
  private double[] yawPitchRoll = new double[3];

  public DriveSub(){
    //leftSlave.setInverted(true);
    //rightSlave.setInverted(true);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new SplitArcadeDriveCommand());
  }

  public void drive(double speed, double turn){
    double left = speed + turn;
    double right = speed - turn;
    drive.tankDrive(left, right);
  }

  public double getYaw(){
    gyro.getYawPitchRoll(yawPitchRoll);
    if(yawPitchRoll[RobotMap.YAWPITCHROLL_YAW] >= 180){
      yawPitchRoll[RobotMap.YAWPITCHROLL_YAW] -= 360;
    }
    if(yawPitchRoll[RobotMap.YAWPITCHROLL_YAW] <= -180){
      yawPitchRoll[RobotMap.YAWPITCHROLL_YAW] += 360;
    }
    return yawPitchRoll[RobotMap.YAWPITCHROLL_YAW];
  }

}
