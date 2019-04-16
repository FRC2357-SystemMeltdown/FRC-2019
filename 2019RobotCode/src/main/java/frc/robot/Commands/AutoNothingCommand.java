package frc.robot.Commands;

import frc.robot.RobotMap.ArmPreset;

public class AutoNothingCommand extends FullAutoCommandGroup {
  public AutoNothingCommand() {
    setName("NOTHING");

    addSequential(new AutoArmCommand(ArmPreset.Low, -300));
    addSequential(new AutoArmCommand(ArmPreset.Low, 0));
  }
}
