package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.RobotMap;
import frc.robot.RobotMap.ArmPreset;

public abstract class FullAutoCommandGroup extends CommandGroup {
  protected void launch() {
    addSequential(new AutoArmCommand(ArmPreset.Low, -300, true));
    addSequential(new AutoArmCommand(ArmPreset.Low, 0, true));

    addSequential(new AutoVelocityMoveCommand(
      (int)(RobotMap.MAX_ENCODER_VELOCITY * 0.30),
      0,
      0.20
    ));
    addSequential(new AutoVelocityMoveCommand(
      (int)(RobotMap.MAX_ENCODER_VELOCITY * 0.60),
      0,
      0.20
    ));
    addSequential(new AutoVelocityMoveCommand(
      (int)(RobotMap.MAX_ENCODER_VELOCITY * 0.80),
      0,
      0.30
    ));
    addSequential(new AutoVelocityMoveCommand(
      (int)(RobotMap.MAX_ENCODER_VELOCITY * 0.40),
      0,
      0.50
    ));
    addSequential(new AutoVelocityMoveCommand(0, 0, 0.1));
  }
}
