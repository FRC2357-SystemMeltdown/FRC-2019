package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.RobotMap;
import frc.robot.RobotMap.ArmPreset;
import frc.robot.Subsystems.VisionSub.TargetType;

public class AutoHatchLoadingStationCommand extends CommandGroup {
  public AutoHatchLoadingStationCommand() {
    setName("HATCH LOAD");

    addSequential(new AutoArmCommand(ArmPreset.Low, 0));
    addSequential(new AutoSquareUpCommand(TargetType.HATCH, RobotMap.VISION_DISTANCE_TO_HATCH_LOAD));

    // Push forward and lift.
    addSequential(new AutoArmCommand(ArmPreset.Low, -40, false));
    addSequential(new AutoMoveProportionalCommand(0.25, 0.25, 0.5));
    addSequential(new AutoMoveProportionalCommand(-0.25, -0.25, 0.5));
    addSequential(new AutoVelocityMoveCommand(-1500, 0, 0.25));
  }
}
