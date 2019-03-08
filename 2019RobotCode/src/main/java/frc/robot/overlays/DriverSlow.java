package frc.robot.overlays;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.RobotMap;

/**
 * The DriverSlow overlay is the same as failsafe at this point, just slower.
 */
public class DriverSlow extends ControlOverlay implements ProportionalDrive {
  public static final double TURN_FACTOR = RobotMap.DRIVER_TURN_PROPORTION_SLOW;
  public static final double SPEED_FACTOR = RobotMap.DRIVER_SPEED_PROPORTION_SLOW;

  public DriverSlow(XboxController controller) {
    super(controller);
  }

  @Override
  public double getTurn() {
      return controller.getX(Hand.kRight) * TURN_FACTOR;
  }

  @Override
  public double getSpeed() {
      return - (controller.getY(Hand.kLeft) * SPEED_FACTOR);
  }

  @Override
  public void activate() {
  }

  @Override
  public void deactivate() {
  }
}
