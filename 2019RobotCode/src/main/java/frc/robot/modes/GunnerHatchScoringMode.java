package frc.robot.modes;

import frc.robot.Robot;
import frc.robot.overlays.GunnerHatchScoring;

public class GunnerHatchScoringMode extends ModeBase {
  public GunnerHatchScoringMode() {
    super("HATCH SCORE");
  }

  @Override
  public void activate() {
    Robot.OI.setGunnerOverlay(new GunnerHatchScoring(Robot.OI.getGunnerController()));
  }

  @Override
  public void deactivate() {

  }

}
