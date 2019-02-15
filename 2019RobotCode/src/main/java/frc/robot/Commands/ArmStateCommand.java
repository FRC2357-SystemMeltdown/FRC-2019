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
import frc.robot.Other.Utility;

public class ArmStateCommand extends Command {

  double lastTargetAngle;

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
    // double coastAngle = Robot.ARM_SUB.targetAngle + RobotMap.ARM_COASTING_DISTANCE;
    double currentAngle = Robot.ARM_SUB.getPotentiometerAngle();
    double deltaAngle = lastTargetAngle - Robot.ARM_SUB.targetAngle;
    double coastDistance = deltaAngle * RobotMap.ARM_COASTING_PERCENTAGE;
    double poweredDistance = deltaAngle - coastDistance;
    double maxLockAngle = Utility.getNearestArmPresetAbove(Robot.ARM_SUB.targetAngle) - RobotMap.ARM_ANGLE_TOLERANCE;
    double minLockAngle = Utility.getNearestArmPresetBelow(Robot.ARM_SUB.targetAngle) + RobotMap.ARM_ANGLE_TOLERANCE;
    if(currentAngle < maxLockAngle && currentAngle > minLockAngle) {
      Robot.ARM_SUB.lockArm();
    } else {
      Robot.ARM_SUB.unlockArm();
    }
    if(currentAngle <= lastTargetAngle - poweredDistance) {
      Robot.ARM_SUB.moveArmManual(0);
    } else if(currentAngle > minAngle && currentAngle < maxAngle) {
      Robot.ARM_SUB.moveArmManual(0);
      lastTargetAngle = Robot.ARM_SUB.targetAngle;
    } else if(Robot.ARM_SUB.targetAngle < lastTargetAngle) {
      Robot.ARM_SUB.moveArmManual(RobotMap.ARM_DOWN);
    } else if(Robot.ARM_SUB.targetAngle > lastTargetAngle) {
      Robot.ARM_SUB.moveArmManual(RobotMap.ARM_UP);
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
