package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap.ArmPreset;

public class ArmPresetCommand extends Command {
  protected ArmPreset preset = null;

  public ArmPresetCommand() {
    requires(Robot.ARM_SUB);
  }

  public boolean isFailsafe() {
    return Robot.getInstance().isFailsafeActive();
  }

  public ArmPresetCommand(ArmPreset preset) {
    requires(Robot.ARM_SUB);
    setPreset(preset);
  }

  protected void setPreset(ArmPreset preset) {
    if (preset == ArmPreset.Failsafe) {
      System.err.println("ArmPresetCommand doesn't support Failsafe preset.");
      this.preset = null;
    } else {
      this.preset = preset;
    }
  }

  @Override
  protected void initialize() {
    if (! isFailsafe()) {
      Robot.ARM_SUB.setTargetValue(preset.value);
      Robot.ARM_SUB.setLastPreset(preset);
    }
  }

  @Override
  protected boolean isFinished() {
    return true;
  }
}
