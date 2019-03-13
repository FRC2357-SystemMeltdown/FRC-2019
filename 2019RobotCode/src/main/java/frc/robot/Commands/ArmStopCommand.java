package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.Subsystems.ArmSub;

public class ArmStopCommand extends Command {

  public ArmStopCommand() {
    requires(Robot.ARM_SUB);
  }

  @Override
  protected void execute() {
    Robot.ARM_SUB.setTargetValue(ArmSub.TARGET_VALUE_STOP);
  }

  @Override
  protected boolean isFinished() {
    return true;
  }
}
