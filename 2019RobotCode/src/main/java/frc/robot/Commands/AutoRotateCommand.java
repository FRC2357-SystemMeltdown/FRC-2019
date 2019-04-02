package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class AutoRotateCommand extends Command {
  private double degrees;

  public AutoRotateCommand(double degrees) {
    requires(Robot.DRIVE_SUB);
    this.degrees = degrees;
  }

  @Override
  protected void initialize() {
    super.initialize();
    Robot.DRIVE_SUB.rotateDegrees(degrees);
  }

  @Override
  protected boolean isFinished() {
    return isInRange();
  }

  public boolean isInRange() {
    double error = Robot.DRIVE_SUB.getRotationError();
    System.out.println("Rotate error:" + error);
    return error <= RobotMap.PID_ROTATION_POSITION_ACCURACY;
  }
}
