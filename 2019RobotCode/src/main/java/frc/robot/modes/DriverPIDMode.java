package frc.robot.modes;

import frc.robot.Robot;
import frc.robot.Commands.EncoderBasedDrive;
import frc.robot.overlays.DriverVelocityDrive;

public class DriverPIDMode extends ModeBase {
  EncoderBasedDrive driveCommand;

  public DriverPIDMode() {
    driveCommand = new EncoderBasedDrive();
  }

  @Override
  public void activate() {
    driveCommand.start();
    Robot.OI.setDriverOverlay(new DriverVelocityDrive(Robot.OI.getDriverController()));
  }

  @Override
  public void deactivate() {
    driveCommand.cancel();
  }
}