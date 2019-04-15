package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.Subsystems.VisionSub.PipelineIndex;

public class VisionSetPipelineCommand extends Command {
  private PipelineIndex index;

  public VisionSetPipelineCommand(PipelineIndex index) {
    this.index = index;
  }

  @Override
  protected void initialize() {
    super.initialize();
    Robot.VISION_SUB.setPipeline(index);
  }

  @Override
  protected boolean isFinished() {
    return true;
  }
}
