package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class CancelAutoCommand extends Command {
  @Override
  protected void initialize() {
    super.initialize();
    Robot.getInstance().cancelAutoCommand();
  }

  @Override
  protected boolean isFinished() {
    return true;
  }
}
