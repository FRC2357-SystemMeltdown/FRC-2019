/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class EncoderBasedDrive extends Command {

  public EncoderBasedDrive() {
    requires(Robot.DRIVE_SUB);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double turn = Robot.OI.getGyroBasedTurn();
    double speed = Robot.OI.getEncoderBasedSpeed();
    Robot.DRIVE_SUB.PIDDrive(speed, turn);
    // if(Robot.OI.getDriverController().getAButton()) {
    //   //Robot.DRIVE_SUB.resetSensors();
    // }
    // Robot.DRIVE_SUB.rotateToAngle(90 * (Robot.OI.getDriverController().getBButton() ? 1 : 0));
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
