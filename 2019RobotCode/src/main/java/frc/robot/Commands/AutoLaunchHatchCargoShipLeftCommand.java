package frc.robot.Commands;

import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.Subsystems.VisionSub.PipelineIndex;

public class AutoLaunchHatchCargoShipLeftCommand extends FullAutoCommandGroup {
  public AutoLaunchHatchCargoShipLeftCommand(AutoModes.StartPosition position) {
    setName("CSHIP_LEFT");

    launch();

    switch (position) {
      case HAB_1_CENTER:
        moveFromHab1Center();
        break;
      case HAB_1_LEFT:
        moveFromHab1Left();
        break;
      case HAB_1_RIGHT:
        // Do nothing, this isn't used.
        return;
    }

    // Score hatch on left cargo ship
    addSequential(new VisionSetPipelineCommand(PipelineIndex.VISION_TARGET_LEFT));
    addSequential(new AutoHatchScoreLowCommand((int)(RobotMap.MAX_ENCODER_VELOCITY * 0.25)));
    addSequential(new VisionSetPipelineCommand(PipelineIndex.HUMAN_VIEW));

    backUpToLeft();
    stop(0);
    //addSequential(new AutoArmCommand(ArmPreset.Low, 0, true));
    //driveToLoadingStation();

    //addSequential(new VisionSetPipelineCommand(PipelineIndex.VISION_TARGET));
    //addSequential(new AutoHatchLoadingStationCommand((int)(RobotMap.MAX_ENCODER_VELOCITY * 0.30)));

    addSequential(new VisionSetPipelineCommand(PipelineIndex.HUMAN_VIEW));
    stop(0);
  }

  private void stop(double seconds) {
    addSequential(new AutoVelocityMoveCommand(0, 0, seconds));
  }

  private void moveFromHab1Left() {
    // Arc right
    addSequential(new AutoVelocityMoveCommand(
      (int)(RobotMap.MAX_ENCODER_VELOCITY * 0.40),
      (int)(RobotMap.MAX_ENCODER_VELOCITY * 0.60),
      0.7
    ));
    // Arc left
    addSequential(new AutoVelocityMoveCommand(
      (int)(RobotMap.MAX_ENCODER_VELOCITY * 0.40),
      (int)(RobotMap.MAX_ENCODER_VELOCITY * -0.60),
      0.7
    ));
  }

  private void moveFromHab1Center() {
  }

  private void backUpToLeft() {
    addSequential(new AutoVelocityMoveCommand(
      (int)(RobotMap.MAX_ENCODER_VELOCITY * -0.60),
      (int)(RobotMap.MAX_ENCODER_VELOCITY * 0.50),
      1.75
    ));
  }

  private void driveToLoadingStation() {
    // Accelerate forward
    addSequential(new AutoVelocityMoveCommand(
      (int)(RobotMap.MAX_ENCODER_VELOCITY * 0.20),
      0,
      0.3
    ));
    // Keep accelerating
    addSequential(new AutoVelocityMoveCommand(
      (int)(RobotMap.MAX_ENCODER_VELOCITY * 0.40),
      0,
      0.3
    ));
    // Drive straight
    addSequential(new AutoVelocityMoveCommand(
      (int)(RobotMap.MAX_ENCODER_VELOCITY * 0.80),
      0,
      0.75
    ));
    // Arc left
    addSequential(new AutoVelocityMoveCommand(
      (int)(RobotMap.MAX_ENCODER_VELOCITY * 0.40),
      (int)(RobotMap.MAX_ENCODER_VELOCITY * -0.20),
      1.25
    ));
    // Drive straight
    addSequential(new AutoVelocityMoveCommand(
      (int)(RobotMap.MAX_ENCODER_VELOCITY * 0.80),
      0,
      0.5
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
