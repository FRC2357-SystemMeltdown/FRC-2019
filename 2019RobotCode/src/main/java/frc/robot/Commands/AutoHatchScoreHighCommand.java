package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.RobotMap;
import frc.robot.RobotMap.ArmPreset;
import frc.robot.Subsystems.VisionSub.TargetType;

public class AutoHatchScoreHighCommand extends CommandGroup {
  public AutoHatchScoreHighCommand() {
    setName("HATCH HIGH");

    addSequential(new AutoArmCommand(ArmPreset.High, 0));
    addSequential(new AutoSquareUpCommand(TargetType.HATCH, RobotMap.VISION_DISTANCE_TO_HATCH_HIGH));

    // Push forward and drop.
    addSequential(new AutoVelocityMoveCommand(275, 0, 0.75));
    addSequential(new AutoArmCommand(ArmPreset.Low, +300, false));
    addSequential(new AutoVelocityMoveCommand(-300, 0, 1.00));
    addSequential(new DriveStopCommand());
  }
}
