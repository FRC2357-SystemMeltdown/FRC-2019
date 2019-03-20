package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class DriveStopCommand extends Command {
  public DriveStopCommand() {
    requires(Robot.DRIVE_SUB);
  }

  @Override
  protected void initialize() {
    super.initialize();
    Robot.DRIVE_SUB.resetSensors();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.DRIVE_SUB.tankDrive(0, 0);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }
}
