package frc.robot.overlays;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class GunnerCreepDrive extends ControlOverlay implements ProportionalDrive {
  public static final double TURN_FACTOR = 0.25;
  public static final double SPEED_FACTOR = 0.25;

  public GunnerCreepDrive(XboxController controller) {
    super(controller);
  }

  public double getTurn() {
      return controller.getX(Hand.kRight) * TURN_FACTOR;
  }

  public double getSpeed() {
      return controller.getY(Hand.kLeft) * SPEED_FACTOR;
  }
}
