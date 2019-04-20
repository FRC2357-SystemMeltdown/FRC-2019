package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.RobotMap;
import frc.robot.RobotMap.ArmPreset;
import frc.robot.Subsystems.VisionSub.TargetType;

public class AutoHatchLoadingStationCommand extends CommandGroup {
  public AutoHatchLoadingStationCommand() {
    // Let the driver set the approach speed.
    this(0);

    // Hold the stop until this command is cancelled
    addSequential(new DriveStopCommand());
  }

  public AutoHatchLoadingStationCommand(int approachSpeed) {
    setName("HATCH LOAD");

    addSequential(new AutoArmCommand(ArmPreset.Low, 0));
    addSequential(new AutoSquareUpCommand(TargetType.HATCH, RobotMap.VISION_DISTANCE_TO_HATCH_LOAD, approachSpeed));

    // Push forward and lift.
    addSequential(new AutoMoveProportionalCommand(0.20, 0.20, 0.60));
    addSequential(new AutoArmCommand(ArmPreset.Low, -300, false));
    addSequential(new AutoMoveProportionalCommand(-0.30, -0.30, 0.75));
  }
}
