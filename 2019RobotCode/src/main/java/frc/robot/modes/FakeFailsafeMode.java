package frc.robot.modes;

import frc.robot.Robot;
import frc.robot.overlays.DriverFailSafe;

/**
 * This class is for testing mode switching
 */
public class FakeFailsafeMode extends ModeBase {
  @Override
  public void activate() {
    Robot.OI.setDriverOverlay(new DriverFailSafe(Robot.OI.getDriverController()));
  }

  @Override
  public void deactivate() {

  }
}
