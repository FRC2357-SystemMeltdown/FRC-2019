package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class AutoVelocityMoveCommand extends Command {
  private int speed;
  private int turn;
  private double seconds;
  private long endTime;

  public AutoVelocityMoveCommand(int speed, int turn, double seconds) {
    requires(Robot.DRIVE_SUB);
    this.speed = speed;
    this.turn = turn;
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
    Robot.DRIVE_SUB.PIDDrive(speed, turn);
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
