package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap.ArmPreset;

public class ArmPresetCommand extends Command {
  private ArmPreset preset;

  public ArmPresetCommand(ArmPreset preset) {
    requires(Robot.ARM_SUB);

    if (preset == ArmPreset.Failsafe) {
      System.err.println("ArmPresetCommand doesn't support Failsafe preset.");
    }
    this.preset = preset;
  }

  @Override
  protected void execute() {
    if (preset == ArmPreset.Failsafe) {
      return;
    }
    Robot.ARM_SUB.setTargetValue(preset.value);
  }

  @Override
  protected boolean isFinished() {
    return Robot.ARM_SUB.isInRange(preset.value);
  }
}
