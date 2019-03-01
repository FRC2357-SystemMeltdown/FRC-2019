/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.Subsystems.ArmSub;

// This command provides direct control over the arm motors
public class MoveArmDirectCommand extends Command {
  private ArmSub.Direction direction;

  public MoveArmDirectCommand(ArmSub.Direction direction) {
    requires(Robot.ARM_SUB);

    this.direction = direction;
  }

  public void setDirection(ArmSub.Direction direction) {
    this.direction = direction;
  }

  public ArmSub.Direction getDirection() {
    return direction;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.ARM_SUB.moveArmManual(direction);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.ARM_SUB.moveArmManual(ArmSub.Direction.STOP);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
