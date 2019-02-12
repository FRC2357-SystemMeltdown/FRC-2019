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

public class MoveHatchCommand extends Command {

  double left, right;

  /**
   * 
   * @param left If the button to move the gantries left is held
   * @param right If the button to move the gantries right is held
   */
  public MoveHatchCommand(double left, double right) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.HATCH_SUB);

    this.left = left;
    this.right = right;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    double speed = left - right;
    Robot.HATCH_SUB.failsafeMoveGantry(speed);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return true;
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
