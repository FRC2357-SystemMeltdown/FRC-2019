package frc.robot.overlays;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.RobotMap;

/**
 * The DriverFailSafe overlay is the least complex drive control system
 * that relies on no sensors to function.
 */
public class DriverFailSafe extends ControlOverlay implements ProportionalDrive {
  public static final double TURN_FACTOR = RobotMap.DRIVER_TURN_PROPORTION;
  public static final double SPEED_FACTOR = RobotMap.DRIVER_SPEED_PROPORTION;

  public DriverFailSafe(XboxController controller) {
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
