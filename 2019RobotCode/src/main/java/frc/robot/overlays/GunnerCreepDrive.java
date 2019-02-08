package frc.robot.overlays;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class GunnerCreepDrive extends GunnerOverlay implements ProportionalDrive {
  public static final double DEFAULT_TURN_FACTOR = 0.25;
  public static final double DEFAULT_SPEED_FACTOR = 0.25;

  protected double turnFactor = DEFAULT_TURN_FACTOR;
  protected double speedFactor = DEFAULT_SPEED_FACTOR;

  public GunnerCreepDrive(XboxController controller) {
    super(controller);
  }

  public double getTurn() {
      return controller.getX(Hand.kRight) * turnFactor;
  }

  public double getSpeed() {
      return controller.getY(Hand.kLeft) * speedFactor;
  }
}
