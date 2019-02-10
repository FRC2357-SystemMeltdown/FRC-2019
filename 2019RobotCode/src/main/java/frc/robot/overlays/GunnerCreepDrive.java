package frc.robot.overlays;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.RobotMap;

public class GunnerCreepDrive extends ControlOverlay implements VelocityDrive {
  public static final double TURN_FACTOR = RobotMap.GUNNER_PROPORTION;
  public static final double SPEED_FACTOR = RobotMap.GUNNER_PROPORTION;

  public GunnerCreepDrive(XboxController controller) {
    super(controller);
  }

  public double getTurnDegreesPerSecond() {
      return controller.getX(Hand.kRight) * TURN_FACTOR * RobotMap.MAX_TURN_RATE_DEGREES_PER_SECOND;
  }

  public double getSpeedInchesPerSecond() {
      return controller.getY(Hand.kLeft) * SPEED_FACTOR * RobotMap.MAX_VELOCITY_INCHES_PER_SECOND;
  }
}
