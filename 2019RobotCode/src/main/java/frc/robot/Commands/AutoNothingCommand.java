package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.RobotMap.ArmPreset;

public class AutoNothingCommand extends CommandGroup {
  public AutoNothingCommand() {
    setName("NOTHING");

    addSequential(new AutoArmCommand(ArmPreset.Low, 0));
  }
}
