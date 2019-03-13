package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap.ArmPreset;
import frc.robot.Subsystems.ArmSub;

public class ArmPresetCycleCommand extends Command {
  private static ArmPreset preset = ArmPreset.Start;

  private ArmSub.Direction direction;

  public ArmPresetCycleCommand(ArmSub.Direction direction) {
    requires(Robot.ARM_SUB);

    this.direction = direction;
  }

  @Override
  protected void initialize() {
    super.initialize();
    switch (direction) {
      case UP:
        preset = ArmPreset.getNext(preset);
        break;
      case DOWN:
        preset = ArmPreset.getPrevious(preset);
        break;
      default:
    }
  }

  @Override
  protected void execute() {
    Robot.ARM_SUB.setTargetValue(preset.value);
  }

  @Override
  protected boolean isFinished() {
    return Robot.ARM_SUB.isInRange(preset.value);
  }
}
