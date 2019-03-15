package frc.robot.Commands;

import frc.robot.RobotMap.ArmPreset;
import frc.robot.Subsystems.ArmSub;

public class ArmPresetCycleCommand extends ArmPresetCommand {

  private ArmSub.Direction direction;

  public ArmPresetCycleCommand(ArmSub.Direction direction) {
    this.direction = direction;
  }

  protected ArmPreset getNextPreset(ArmSub.Direction direction) {
    switch (direction) {
      case UP:
        return ArmPreset.getNext(getLastPreset());
      case DOWN:
        return ArmPreset.getPrevious(getLastPreset());
      default:
        return ArmPreset.Start;
    }
  }

  @Override
  protected void initialize() {
    // Update the preset before setting its value.
    // Someone else may have changed the last preset
    ArmPreset nextPreset = getNextPreset(this.direction);
    setPreset(nextPreset);
    super.initialize();
  }
}
