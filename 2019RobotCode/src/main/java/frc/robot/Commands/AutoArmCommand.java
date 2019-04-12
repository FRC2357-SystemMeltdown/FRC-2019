package frc.robot.Commands;

import frc.robot.Robot;
import frc.robot.RobotMap.ArmPreset;
import frc.robot.Subsystems.ArmSub;

public class AutoArmCommand extends ArmPresetCommand {
  private int adjust;
  private boolean awaitStop;

  public AutoArmCommand(ArmPreset preset, int adjust) {
    this(preset, adjust, true);
  }

  public AutoArmCommand(ArmPreset preset, int adjust, boolean awaitStop) {
    requires(Robot.ARM_SUB);
    this.adjust = adjust;
    this.awaitStop = awaitStop;
    setPreset(preset);
  }

  @Override
  protected void initialize() {
    Robot.ARM_SUB.setTargetValue(preset.value + adjust);
    Robot.ARM_SUB.setLastPreset(preset);
  }

  @Override
  protected boolean isFinished() {
    if (!awaitStop) {
      return true;
    }
    return Robot.ARM_SUB.calculateCurrentDirection() == ArmSub.Direction.STOP;
  }
}
