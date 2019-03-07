package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class TipReflexSetCommand extends Command {
  private double pitchThreshold;
  private double speed;

  /**
   * Creates a command to set the reflex mode.
   * @param pitchThreshold The angle at which to kick in, or NaN to cancel.
   * @param speed The speed to apply to the motors (should be negative)
   */
  public TipReflexSetCommand(double pitchThreshold, double speed) {
    this.pitchThreshold = pitchThreshold;
    this.speed = speed;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.DRIVE_SUB.setTipReflex(pitchThreshold, speed);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return true;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
