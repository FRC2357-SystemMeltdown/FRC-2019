package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class DriverSlowCommand extends Command {

  private boolean slow;

  public DriverSlowCommand(boolean slow) {
    this.slow = slow;
  }

  @Override
  protected void initialize() {
    Robot.OI.setDriverSlow(slow);;
  }

  @Override
  protected boolean isFinished() {
    return true;
  }
}
