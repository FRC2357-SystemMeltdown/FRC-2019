package frc.robot.modes;

import frc.robot.Robot;
import frc.robot.Commands.ProportionalDriveCommand;
import frc.robot.overlays.DriverFailSafe;

public class DriverFailsafeMode extends ModeBase {
  ProportionalDriveCommand driveCommand;

  public DriverFailsafeMode() {
    super("FAILSAFE");
    driveCommand = new ProportionalDriveCommand();
  }

  @Override
  public void activate() {
    driveCommand.start();
    Robot.OI.setDriverOverlay(new DriverFailSafe(Robot.OI.getDriverController()));
  }

  @Override
  public void deactivate() {
    driveCommand.cancel();
  }
}
