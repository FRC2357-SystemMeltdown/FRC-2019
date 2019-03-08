package frc.robot.modes;

import frc.robot.Robot;
import frc.robot.Commands.ProportionalDriveCommand;
import frc.robot.overlays.DriverSlow;

public class DriverSlowMode extends ModeBase {
  ProportionalDriveCommand driveCommand;

  public DriverSlowMode() {
    super("SLOW");
    driveCommand = new ProportionalDriveCommand();
  }

  @Override
  public void activate() {
    driveCommand.start();
    Robot.OI.setDriverOverlay(new DriverSlow(Robot.OI.getDriverController()));
  }

  @Override
  public void deactivate() {
    driveCommand.cancel();
  }
}
