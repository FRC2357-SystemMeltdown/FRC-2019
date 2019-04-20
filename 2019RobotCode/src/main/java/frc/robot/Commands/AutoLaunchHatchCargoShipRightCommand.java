package frc.robot.Commands;

import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.Subsystems.VisionSub.PipelineIndex;

public class AutoLaunchHatchCargoShipRightCommand extends FullAutoCommandGroup {
  public AutoLaunchHatchCargoShipRightCommand(AutoModes.StartPosition position) {
    setName("CSHIP_RIGHT");

    launch();

    switch (position) {
      case HAB_1_CENTER:
        moveFromHab1Center();
        break;
      case HAB_1_LEFT:
        // Do nothing, this isn't used.
        break;
      case HAB_1_RIGHT:
        moveFromHab1Right();
        return;
    }

    // Score hatch on right cargo ship
    addSequential(new VisionSetPipelineCommand(PipelineIndex.VISION_TARGET_RIGHT));
    addSequential(new AutoHatchScoreLowCommand((int)(RobotMap.MAX_ENCODER_VELOCITY * 0.25)));
    addSequential(new VisionSetPipelineCommand(PipelineIndex.HUMAN_VIEW));

    backUpToRight();
    stop(0);

    addSequential(new VisionSetPipelineCommand(PipelineIndex.HUMAN_VIEW));
  }

  private void stop(double seconds) {
    addSequential(new AutoVelocityMoveCommand(0, 0, seconds));
  }

  private void moveFromHab1Right() {
    // Arc left
    addSequential(new AutoVelocityMoveCommand(
      (int)(RobotMap.MAX_ENCODER_VELOCITY * 0.40),
      (int)(RobotMap.MAX_ENCODER_VELOCITY * -0.60),
      0.7
    ));
    // Arc right
    addSequential(new AutoVelocityMoveCommand(
      (int)(RobotMap.MAX_ENCODER_VELOCITY * 0.40),
      (int)(RobotMap.MAX_ENCODER_VELOCITY * 0.60),
      0.7
    ));
  }

  private void moveFromHab1Center() {
    // Arc right
    addSequential(new AutoVelocityMoveCommand(
      (int)(RobotMap.MAX_ENCODER_VELOCITY * 0.40),
      (int)(RobotMap.MAX_ENCODER_VELOCITY * 0.30),
      0.7
    ));
    // Arc left
    addSequential(new AutoVelocityMoveCommand(
      (int)(RobotMap.MAX_ENCODER_VELOCITY * 0.40),
      (int)(RobotMap.MAX_ENCODER_VELOCITY * -0.30),
      0.7
    ));
  }

  private void backUpToRight() {
    addSequential(new AutoVelocityMoveCommand(
      (int)(RobotMap.MAX_ENCODER_VELOCITY * -0.60),
      (int)(RobotMap.MAX_ENCODER_VELOCITY * -0.50),
      1.75
    ));
  }

  @Override
  protected boolean isFinished() {
    boolean finished = super.isFinished();
    return finished;
  }

  @Override
  protected void interrupted() {
    super.interrupted();
    Robot.VISION_SUB.setPipeline(PipelineIndex.HUMAN_VIEW);
  }
}
