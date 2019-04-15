package frc.robot.Commands;

import frc.robot.RobotMap;
import frc.robot.RobotMap.ArmPreset;
import frc.robot.Subsystems.VisionSub.PipelineIndex;

public class AutoLaunchHatchCargoShipLeftCommand extends FullAutoCommandGroup {
  public AutoLaunchHatchCargoShipLeftCommand(AutoModes.StartPosition position) {
    setName("CSHIP_LEFT");

    // Sometimes the auto arm command doesn't quite work the first time. :/
    addSequential(new AutoArmCommand(ArmPreset.Low, 0, true));
    addSequential(new AutoArmCommand(ArmPreset.Low, 0, true));
    addSequential(new VisionSetPipelineCommand(PipelineIndex.VISION_TARGET_LEFT));

    switch (position) {
      case HAB_1_CENTER:
        moveFromHab1Center();
        break;
      case HAB_1_LEFT:
        moveFromHab1Left();
        break;
      case HAB_1_RIGHT:
        moveFromHab1Right();
        break;
    }

    // Auto scoring
    addSequential(new AutoHatchScoreLowCommand((int)(RobotMap.MAX_ENCODER_VELOCITY * 0.25)));

    // Stop
    addSequential(new AutoVelocityMoveCommand(0, 0, 0));
    addSequential(new VisionSetPipelineCommand(PipelineIndex.HUMAN_VIEW));
  }

  private void moveFromHab1Left() {
    // Initial launch
    addSequential(new AutoVelocityMoveCommand(
      (int)(RobotMap.MAX_ENCODER_VELOCITY * 0.30),
      0,
      0.20
    ));
    addSequential(new AutoVelocityMoveCommand(
      (int)(RobotMap.MAX_ENCODER_VELOCITY * 0.60),
      0,
      0.20
    ));
    addSequential(new AutoVelocityMoveCommand(
      (int)(RobotMap.MAX_ENCODER_VELOCITY * 0.90),
      0,
      0.50
    ));

    // Arc right
    addSequential(new AutoVelocityMoveCommand(
      (int)(RobotMap.MAX_ENCODER_VELOCITY * 0.50),
      (int)(RobotMap.MAX_ENCODER_VELOCITY * 0.45),
      0.6
    ));
    // Arc left
    addSequential(new AutoVelocityMoveCommand(
      (int)(RobotMap.MAX_ENCODER_VELOCITY * 0.50),
      (int)(RobotMap.MAX_ENCODER_VELOCITY * -0.45),
      0.6
    ));
  }

  private void moveFromHab1Center() {
  }

  private void moveFromHab1Right() {
  }

  @Override
  protected boolean isFinished() {
    boolean finished = super.isFinished();
    return finished;
  }
}
