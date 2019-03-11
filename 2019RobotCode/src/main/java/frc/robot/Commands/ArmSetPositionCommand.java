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
import frc.robot.overlays.ArmControl;

/**
 * Travels in a single direction to a position.
 * Does not ensure accuracy, just travels to that position and stops.
 * This command can be called multiple times to dial in a position.
 */
public class ArmSetPositionCommand extends Command {
  private ArmControl controller;

  public ArmSetPositionCommand(ArmControl controller) {
    requires(Robot.ARM_SUB);
    this.controller = controller;
  }

  private ArmSub.Direction getDirectionNeeded() {
    int targetValue = controller.getArmTargetValue();
    int value = Robot.ARM_SUB.getPotentiometerValue();
    int tolerance = RobotMap.ARM_TARGET_TOLERANCE;

    if (value < targetValue + tolerance) {
      return ArmSub.Direction.DOWN;
    } else if (value > targetValue - tolerance) {
      return ArmSub.Direction.UP;
    } else {
      return ArmSub.Direction.STOP;
    }

  }

  private int getAdjustedTargetValue() {
    int targetValue = controller.getArmTargetValue();
    ArmSub.Direction direction = getDirectionNeeded();

    switch(direction) {
      case DOWN:
        return targetValue - RobotMap.ARM_DOWN_OVERSHOOT;
      case UP:
        return targetValue + RobotMap.ARM_UP_OVERSHOOT;
      default:
        return targetValue;
    }
  }

  private boolean isInRange() {
    int value = Robot.ARM_SUB.getPotentiometerValue();
    int targetValue = getAdjustedTargetValue();
    int min = targetValue - RobotMap.ARM_TARGET_TOLERANCE;
    int max = targetValue + RobotMap.ARM_TARGET_TOLERANCE;

    return value >= min && value <= max;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (isInRange()) {
      Robot.ARM_SUB.moveArmManual(ArmSub.Direction.STOP);
    } else {
      Robot.ARM_SUB.moveArmManual(getDirectionNeeded());
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
