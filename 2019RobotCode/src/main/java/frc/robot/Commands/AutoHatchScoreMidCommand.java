package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.RobotMap;
import frc.robot.RobotMap.ArmPreset;
import frc.robot.Subsystems.VisionSub.TargetType;

public class AutoHatchScoreMidCommand extends CommandGroup {
  public AutoHatchScoreMidCommand() {
    setName("HATCH MID");

    addSequential(new AutoArmCommand(ArmPreset.Low, 0));
    addSequential(new AutoSquareUpCommand(TargetType.HATCH, RobotMap.VISION_DISTANCE_TO_HATCH_LOW + 4));

    // Back up and get into scoring position.
    addSequential(new AutoVelocityMoveCommand(-300, 0, 0.5));
    addSequential(new AutoArmCommand(ArmPreset.Mid, -70));

    // Push forward and drop.
    addSequential(new AutoVelocityMoveCommand(550, 50, 2.50));
    addSequential(new AutoArmCommand(ArmPreset.Mid, +300, false));
    addSequential(new AutoVelocityMoveCommand(-450, 0, 0.75));
    addSequential(new DriveStopCommand());
  }
}
