package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class AutoMoveForwardCommand extends Command {
  private double inches;

  public AutoMoveForwardCommand(double inches) {
    requires(Robot.DRIVE_SUB);
    this.inches = inches;
  }

  @Override
  protected void initialize() {
    super.initialize();
    Robot.DRIVE_SUB.moveForwardDistance(inches);
  }

  @Override
  protected boolean isFinished() {
    return isInRange();
  }

  public boolean isInRange() {
    int error = Robot.DRIVE_SUB.getPositionError();
    return error <= RobotMap.PID_DRIVE_POSITION_ACCURACY;
  }
}
