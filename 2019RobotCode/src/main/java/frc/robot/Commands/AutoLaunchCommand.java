package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.RobotMap;
import frc.robot.RobotMap.ArmPreset;

public class AutoLaunchCommand extends CommandGroup {
  public AutoLaunchCommand() {
    setName("LAUNCH");

    addSequential(new AutoArmCommand(ArmPreset.CargoPickup, 0));
    addSequential(new AutoVelocityMoveCommand((int)(RobotMap.MAX_ENCODER_VELOCITY * 1.00), 0, 1.00));
    addParallel(new AutoArmCommand(ArmPreset.Low, 0));
    addSequential(new AutoVelocityMoveCommand((int)(RobotMap.MAX_ENCODER_VELOCITY * 0.50), 0, 0.25));
    addSequential(new AutoVelocityMoveCommand(0, 0, 0));
  }

  @Override
  protected boolean isFinished() {
    boolean finished = super.isFinished();
    return finished;
  }
}
