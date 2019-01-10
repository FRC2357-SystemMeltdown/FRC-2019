/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Susbsystems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.RobotMap;
import frc.robot.Commands.SplitArcadeDriveCommand;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class DriveSub extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.


  public Talon leftMaster = new Talon(RobotMap.LEFT_DRIVE);
  public Talon leftSlave = new Talon(RobotMap.LEFT_DRIVE_SLAVE);
  public SpeedControllerGroup left = new SpeedControllerGroup(leftMaster, leftSlave);
  public Talon rightMaster = new Talon(RobotMap.RIGHT_DRIVE);
  public Talon rightSlave = new Talon(RobotMap.RIGHT_DRIVE_SLAVE);
  public SpeedControllerGroup right = new SpeedControllerGroup(rightMaster, rightSlave);
  public DifferentialDrive drive = new DifferentialDrive(left, right);

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new SplitArcadeDriveCommand());
  }

  public void drive(double x, double y){
    drive.arcadeDrive(x, y);
  }

}
