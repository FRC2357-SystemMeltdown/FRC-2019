package frc.robot.controls;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.RobotMap;
import frc.robot.Other.Utility;

public class DriverControls extends Controls implements ProportionalDrive, VelocityDrive {
  public DriverControls(XboxController controller) {
    super(controller);
  }

  @Override
  public int getEncoderTurnDifferential() {
    double input = Utility.deadband(controller.getX(Hand.kRight), RobotMap.DRIVE_STICK_DEADBAND);
    int turnRate = (int)(input * RobotMap.DRIVER_ENCODER_TURN_RATE);
    return turnRate;
  }

  @Override
  public int getEncoderSpeed() {
    double input = Utility.deadband(controller.getY(Hand.kLeft), RobotMap.DRIVE_STICK_DEADBAND);
    int speed = (int)(-input * RobotMap.DRIVER_ENCODER_SPEED);
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
