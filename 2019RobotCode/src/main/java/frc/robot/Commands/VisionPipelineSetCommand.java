package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.Subsystems.VisionSub.PipelineIndex;

public class VisionPipelineSetCommand extends Command {
  private PipelineIndex pipeline;

  public VisionPipelineSetCommand(PipelineIndex pipeline) {
    requires(Robot.VISION_SUB);
    this.pipeline = pipeline;
    setRunWhenDisabled(true);
  }

  @Override
  protected void initialize() {
    Robot.VISION_SUB.setPipeline(pipeline);
  }

  @Override
  protected boolean isFinished() {
    return true;
  }
}
