package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ArmStopCommand extends Command {

  public ArmStopCommand() {
    requires(Robot.ARM_SUB);
  }

  @Override
  protected void execute() {
    Robot.ARM_SUB.stop();
  }

  @Override
  protected boolean isFinished() {
    // This never returns true, because it's the default command for ArmSub
    return false;
  }
}
