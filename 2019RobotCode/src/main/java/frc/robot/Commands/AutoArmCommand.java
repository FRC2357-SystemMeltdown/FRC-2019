package frc.robot.Commands;

import frc.robot.Robot;
import frc.robot.RobotMap.ArmPreset;
import frc.robot.Subsystems.ArmSub;

public class AutoArmCommand extends ArmPresetCommand {
  private int adjust;

  public AutoArmCommand(ArmPreset preset, int adjust) {
    requires(Robot.ARM_SUB);
    this.adjust = adjust;
    setPreset(preset);
  }

  @Override
  protected void initialize() {
    Robot.ARM_SUB.setTargetValue(preset.value + adjust);
    lastPreset = this.preset;
  }

  @Override
  protected boolean isFinished() {
    return Robot.ARM_SUB.calculateCurrentDirection() == ArmSub.Direction.STOP;
  }
}
