package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap.ArmPreset;

public class ArmPresetCommand extends Command {
  // This is tracked amongst all preset commands to keep them them same.
  protected static ArmPreset lastPreset = ArmPreset.Start;

  public static ArmPreset getLastPreset() {
    return lastPreset;
  }

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
      lastPreset = this.preset;
    }
  }

  @Override
  protected boolean isFinished() {
    return true;
  }
}
