package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class DefenseModeSetCommand extends Command {
  private boolean defenseMode;

  public DefenseModeSetCommand(boolean defenseMode) {
    this.defenseMode = defenseMode;
  }

  @Override
  protected void initialize() {
    if (defenseMode) {
      Robot.ARM_SUB.activateDefense();
    } else {
      Robot.ARM_SUB.deactivateDefense();
    }
  }

  @Override
  protected boolean isFinished() {
    return true;
  }
}
