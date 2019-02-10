package frc.robot.modes;

import frc.robot.Robot;
import frc.robot.overlays.GunnerFailSafe;

public class GunnerFailsafeMode extends ModeBase {
  @Override
  public void activate() {
    Robot.OI.setGunnerOverlay(new GunnerFailSafe(Robot.OI.getGunnerController()));
  }

  @Override
  public void deactivate() {

  }
}
