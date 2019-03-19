package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class AutoModePreviewCommand extends Command {
  private boolean active;

  public AutoModePreviewCommand(boolean active) {
    requires(Robot.VISION_SUB);
    this.active = active;
  }

  @Override
  protected void initialize() {
    Robot.OI.setAutoModePreview(active);
  }

  @Override
  protected boolean isFinished() {
    return true;
  }
}
