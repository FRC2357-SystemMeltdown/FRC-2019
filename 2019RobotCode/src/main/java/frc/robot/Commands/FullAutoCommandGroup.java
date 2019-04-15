package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;

public abstract class FullAutoCommandGroup extends CommandGroup {

  @Override
  protected boolean isFinished() {
    return Robot.OI.isAnyStickActive() || super.isFinished();
  }
}
