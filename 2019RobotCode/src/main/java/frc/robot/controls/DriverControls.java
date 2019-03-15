package frc.robot.controls;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.RobotMap;

public class DriverControls extends Controls implements ProportionalDrive, VelocityDrive {
  public DriverControls(XboxController controller) {
    super(controller);
  }

  @Override
  public double getTurnDegreesPerSecond() {
    double input = controller.getX(Hand.kRight);
    double turnRate = input * RobotMap.MAX_TURN_RATE_DEGREES_PER_SECOND;
    return turnRate;
  }

  @Override
  public double getSpeedInchesPerSecond() {
    double input = controller.getY(Hand.kLeft);
    double speed = -input * RobotMap.MAX_VELOCITY_INCHES_PER_SECOND;
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
