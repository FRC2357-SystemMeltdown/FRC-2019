package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class AutoMoveProportionalCommand extends Command {
  private double leftSpeed;
  private double rightSpeed;
  private double seconds;
  private long endTime;

  public AutoMoveProportionalCommand(double leftSpeed, double rightSpeed, double seconds) {
    requires(Robot.DRIVE_SUB);
    this.leftSpeed = leftSpeed;
    this.rightSpeed = rightSpeed;
    this.seconds = seconds;
  }

  @Override
  protected void initialize() {
    super.initialize();
    endTime = (long)(System.currentTimeMillis() + (seconds * 1000));
  }

  @Override
  protected void execute() {
    super.initialize();
    Robot.DRIVE_SUB.tankDrive(leftSpeed, rightSpeed);
  }

  @Override
  protected void end() {
    super.end();
  }

  @Override
  protected boolean isFinished() {
    return System.currentTimeMillis() >= endTime;
  }
}
