package frc.robot.modes;

import frc.robot.Robot;
import frc.robot.overlays.GunnerHatchLoading;

public class GunnerHatchLoadingMode extends ModeBase {
  public GunnerHatchLoadingMode() {
    super("HATCH LOAD");
  }

  @Override
  public void activate() {
    Robot.OI.setGunnerOverlay(new GunnerHatchLoading(Robot.OI.getGunnerController()));
  }

  @Override
  public void deactivate() {

  }

}
