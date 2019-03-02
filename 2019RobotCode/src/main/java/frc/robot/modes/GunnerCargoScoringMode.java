package frc.robot.modes;

import frc.robot.Robot;
import frc.robot.overlays.GunnerCargoScoring;

public class GunnerCargoScoringMode extends ModeBase {
  public GunnerCargoScoringMode() {
    super("CARGO SCORE");
  }

  @Override
  public void activate() {
    Robot.OI.setGunnerOverlay(new GunnerCargoScoring(Robot.OI.getGunnerController()));
  }

  @Override
  public void deactivate() {

  }

}