package frc.robot.overlays;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

/**
 * The DriverFailSafe overlay is the least complex drive control system
 * that relies on minimal to no sensors to function.
 */
public class DriverFailSafe extends ControlOverlay implements ProportionalDrive {
  public static final double DEFAULT_TURN_FACTOR = 1.0;
  public static final double DEFAULT_SPEED_FACTOR = 1.0;

  protected double turnFactor = DEFAULT_TURN_FACTOR;
  protected double speedFactor = DEFAULT_SPEED_FACTOR;

  public DriverFailSafe(XboxController controller) {
    super(controller);
  }

  public double getTurn() {
      return controller.getX(Hand.kRight) * turnFactor;
  }

  public double getSpeed() {
      return controller.getY(Hand.kLeft) * speedFactor;
  }
}
