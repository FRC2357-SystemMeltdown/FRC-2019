package frc.robot.modes;

import frc.robot.Robot;
import frc.robot.Commands.ProportionalDriveCommand;
import frc.robot.overlays.DriverFast;

public class DriverFastMode extends ModeBase {
  ProportionalDriveCommand driveCommand;

  public DriverFastMode() {
    super("FAST");
    driveCommand = new ProportionalDriveCommand();
  }

  @Override
  public void activate() {
    driveCommand.start();
    Robot.OI.setDriverOverlay(new DriverFast(Robot.OI.getDriverController()));
  }

  @Override
  public void deactivate() {
    driveCommand.cancel();
  }
}
