/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.Subsystems.ArmSub;

public class ArmPositionCommand extends Command {
  public ArmPositionCommand() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.ARM_SUB);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(Robot.ARM_SUB.getPotentiometerValue() > Robot.ARM_SUB.currentPosition.angle + RobotMap.ARM_ANGLE_TOLERANCE) {
      Robot.ARM_SUB.moveArmManual(ArmSub.Direction.UP);
      System.out.println("Arm up");
    } else if(Robot.ARM_SUB.getPotentiometerValue() < Robot.ARM_SUB.currentPosition.angle - RobotMap.ARM_ANGLE_TOLERANCE) {
      Robot.ARM_SUB.moveArmManual(ArmSub.Direction.DOWN);
      System.out.println("Arm down");
    } else {
      Robot.ARM_SUB.moveArmManual(ArmSub.Direction.STOP);
      System.out.println("Arm stop");
    }
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
