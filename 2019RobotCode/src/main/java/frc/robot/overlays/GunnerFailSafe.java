package frc.robot.overlays;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.RobotMap;

public class GunnerFailSafe extends GunnerCreepDrive implements ProportionalDrive {
  public static final double TURN_FACTOR = RobotMap.GUNNER_PROPORTION;
  public static final double SPEED_FACTOR = RobotMap.GUNNER_PROPORTION;

  public GunnerFailSafe(XboxController controller) {
    super(controller);
  }

  public double getTurn() {
      return controller.getX(Hand.kRight) * TURN_FACTOR;
  }

  public double getSpeed() {
      return controller.getY(Hand.kLeft) * SPEED_FACTOR;
  }
}
