package frc.robot.controls;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.RobotMap;
import frc.robot.Other.Utility;

public class DriverControls extends Controls implements ProportionalDrive, VelocityDrive {
  private int lastSpeed;

  public DriverControls(XboxController controller) {
    super(controller);
    lastSpeed = 0;
  }

  @Override
  public int getEncoderTurnDifferential() {
    double input = Utility.deadband(controller.getX(Hand.kRight), RobotMap.DRIVE_STICK_DEADBAND);
    int turnRate = (int)(input * RobotMap.DRIVER_ENCODER_TURN_RATE);
    return turnRate;
  }

  @Override
  public int getEncoderSpeed() {
    // TODO: Move this to OI
    double input = Utility.deadband(controller.getY(Hand.kLeft), RobotMap.DRIVE_STICK_DEADBAND);
    int speed = (int)(-input * RobotMap.DRIVER_ENCODER_SPEED);

    double limitFactor = RobotMap.DRIVER_ENCODER_MAX_FORWARD_LIMIT_FACTOR;
    int maxDiff = RobotMap.DRIVER_ENCODER_MAX_DIFF;

    if (speed - lastSpeed > maxDiff) {
      int max = maxDiff;

      if (lastSpeed > 0) {
        max = (int)(lastSpeed * limitFactor);
      } else if (lastSpeed < 0) {
        max = (int)(lastSpeed / limitFactor);
        max = max > -maxDiff ? 0 : max;
      }

      if (speed > max) {
        System.out.println("limit: " + speed + " to " + max + " (lastSpeed=" + lastSpeed + ")");
        speed = max;
      }
    }

    lastSpeed = speed;
    return speed;
  }

  @Override
  public double getProportionalTurn() {
    return controller.getX(Hand.kRight) * RobotMap.DRIVER_TURN_PROPORTION;
  }

  @Override
  public double getProportionalSpeed() {
    return - (controller.getY(Hand.kLeft) * RobotMap.DRIVER_SPEED_PROPORTION);
  }
}
