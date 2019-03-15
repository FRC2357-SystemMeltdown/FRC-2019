package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class FailsafeSetCommand extends Command {
  private boolean failsafeActive;

  public FailsafeSetCommand(boolean failsafeActive) {
    this.failsafeActive = failsafeActive;
    setRunWhenDisabled(true);
  }

  @Override
  protected void initialize() {
    super.initialize();
    Robot.getInstance().setFailsafeActive(failsafeActive);
  }

  @Override
  protected boolean isFinished() {
    return true;
  }
}
