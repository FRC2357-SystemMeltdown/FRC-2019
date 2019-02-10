package frc.robot.overlays;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.RobotMap;

/**
 * The DriverVelocityDrive is the default overlay. It returns speed in inches/second.
 */
public class DriverVelocityDrive extends ControlOverlay implements VelocityDrive {

  public DriverVelocityDrive(XboxController controller) {
    super(controller);
  }

  public double getTurnDegreesPerSecond() {
      double input = controller.getX(Hand.kRight);
      double turnRate = input * RobotMap.MAX_TURN_RATE_DEGREES_PER_SECOND;
      return turnRate;
  }

  public double getSpeedInchesPerSecond() {
    double input = controller.getY(Hand.kLeft);
    double speed = input * RobotMap.MAX_VELOCITY_INCHES_PER_SECOND;
    return speed;
  }
}