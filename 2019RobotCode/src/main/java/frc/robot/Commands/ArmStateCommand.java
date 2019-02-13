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

public class ArmStateCommand extends Command {

  public ArmStateCommand() {
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
    double minAngle = Robot.ARM_SUB.targetAngle - RobotMap.ARM_ANGLE_TOLERANCE;
    double maxAngle = Robot.ARM_SUB.targetAngle + RobotMap.ARM_ANGLE_TOLERANCE;
    double coastAngle = Robot.ARM_SUB.targetAngle + RobotMap.ARM_COASTING_DISTANCE;
    double currentAngle = Robot.ARM_SUB.getPotentiometerAngle();
    if(Robot.ARM_SUB.targetAngle == RobotMap.ARM_HIGH_GOAL_ANGLE) {
      if(currentAngle > maxAngle) {
        Robot.ARM_SUB.moveArmManual(-1);
        Robot.ARM_SUB.lockArm();
      } else if(currentAngle < minAngle) {
        Robot.ARM_SUB.moveArmManual(1);
        if(currentAngle > RobotMap.ARM_MID_GOAL_ANGLE) {
          Robot.ARM_SUB.lockArm();
        }
      } else {
        Robot.ARM_SUB.moveArmManual(0);
      }
    } else if(Robot.ARM_SUB.targetAngle == RobotMap.ARM_MID_GOAL_ANGLE) {
      if(currentAngle > coastAngle) {
        Robot.ARM_SUB.moveArmManual(-1);
        if(currentAngle < RobotMap.ARM_HIGH_GOAL_ANGLE) {
          Robot.ARM_SUB.lockArm();
        }
      } else if(currentAngle < minAngle) {
        Robot.ARM_SUB.moveArmManual(1);
        if(currentAngle > RobotMap.ARM_LOW_GOAL_ANGLE) {
          Robot.ARM_SUB.lockArm();
        }
      } else {
        Robot.ARM_SUB.moveArmManual(0);
      }
    } else if(Robot.ARM_SUB.targetAngle == RobotMap.ARM_LOW_GOAL_ANGLE) {
      if(currentAngle > coastAngle) {
        Robot.ARM_SUB.moveArmManual(-1);
        if(currentAngle < RobotMap.ARM_MID_GOAL_ANGLE) {
          Robot.ARM_SUB.lockArm();
        }
      } else if(currentAngle < minAngle) {
        Robot.ARM_SUB.moveArmManual(1);
        Robot.ARM_SUB.lockArm();
      } else {
        Robot.ARM_SUB.moveArmManual(0);
      }
    } else {
      Robot.ARM_SUB.targetAngle = RobotMap.ARM_STARTING_ANGLE;
      minAngle = RobotMap.ARM_STARTING_ANGLE;
      coastAngle = Robot.ARM_SUB.targetAngle + RobotMap.ARM_COASTING_DISTANCE;
      if(currentAngle > coastAngle) {
        Robot.ARM_SUB.moveArmManual(-1);
      } else if(currentAngle < minAngle) {
        Robot.ARM_SUB.moveArmManual(1);
      } else {
        Robot.ARM_SUB.moveArmManual(0);
      }
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
