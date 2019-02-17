/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.Subsystems.HatchSub;
import frc.robot.overlays.HatchControl;

public class HatchOpenCloseCommand extends Command {

  HatchControl controller;

  /**
   * 
   */
  public HatchOpenCloseCommand(HatchControl controller) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.HATCH_SUB);

    this.controller = controller;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double speed = controller.getHatchOpenCloseSpeed();
    Robot.HATCH_SUB.failsafeOpenCloseGantry(speed);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.HATCH_SUB.failsafeOpenCloseGantry(0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
