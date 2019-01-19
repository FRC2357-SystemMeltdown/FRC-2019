/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Susbsystems;

import frc.robot.RobotMap;
import frc.robot.Commands.SplitArcadeDriveCommand;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class DriveSub extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.


  public WPI_TalonSRX leftMaster = new WPI_TalonSRX(RobotMap.LEFT_DRIVE);
  public WPI_TalonSRX leftSlave = new WPI_TalonSRX(RobotMap.LEFT_DRIVE_SLAVE);
  public SpeedControllerGroup left = new SpeedControllerGroup(leftMaster, leftSlave);
  public WPI_TalonSRX rightMaster = new WPI_TalonSRX(RobotMap.RIGHT_DRIVE);
  public WPI_TalonSRX rightSlave = new WPI_TalonSRX(RobotMap.RIGHT_DRIVE_SLAVE);
  public SpeedControllerGroup right = new SpeedControllerGroup(rightMaster, rightSlave);
  public DifferentialDrive drive = new DifferentialDrive(right, left);

  public DriveSub(){
    //leftSlave.setInverted(true);
    //rightSlave.setInverted(true);
    System.out.println("DriveSub instantialized");
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new SplitArcadeDriveCommand());
  }

  public void drive(double spd, double trn){
    drive.arcadeDrive(spd, trn);
  }

}
