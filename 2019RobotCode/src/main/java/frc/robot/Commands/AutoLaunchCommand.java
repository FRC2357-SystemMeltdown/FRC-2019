package frc.robot.Commands;

import frc.robot.RobotMap;
import frc.robot.RobotMap.ArmPreset;

public class AutoLaunchCommand extends FullAutoCommandGroup {
  public AutoLaunchCommand() {
    setName("LAUNCH");

    launch();
  }
}
