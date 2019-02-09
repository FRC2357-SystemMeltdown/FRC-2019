package frc.robot.overlays;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

/**
 * The DriverFailSafe overlay is the least complex drive control system
 * that relies on minimal to no sensors to function.
 */
public class DriverFailSafe extends ControlOverlay implements ProportionalDrive {
  public static final double TURN_FACTOR = 1.0;
  public static final double SPEED_FACTOR = 1.0;

  public DriverFailSafe(XboxController controller) {
    super(controller);
  }

  public double getTurn() {
      return controller.getX(Hand.kRight) * TURN_FACTOR;
  }

  public double getSpeed() {
      return controller.getY(Hand.kLeft) * SPEED_FACTOR;
  }
}
