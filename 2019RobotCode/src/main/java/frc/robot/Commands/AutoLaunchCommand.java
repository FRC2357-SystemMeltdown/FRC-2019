package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.RobotMap;
import frc.robot.RobotMap.ArmPreset;

public class AutoLaunchCommand extends CommandGroup {
  public AutoLaunchCommand() {
    setName("LAUNCH");

    addSequential(new AutoArmCommand(ArmPreset.Low, 0));
    addSequential(new AutoVelocityMoveCommand((int)(RobotMap.MAX_ENCODER_VELOCITY * 0.80), 0, 1.25));
    addSequential(new AutoVelocityMoveCommand((int)(RobotMap.MAX_ENCODER_VELOCITY * 0.25), 0, 0.50));
    addSequential(new AutoVelocityMoveCommand(0, 0, 0));
  }
}
