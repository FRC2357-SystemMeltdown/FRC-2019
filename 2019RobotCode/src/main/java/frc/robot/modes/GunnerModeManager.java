package frc.robot.modes;

import frc.robot.OI.DPadValue;

/**
 * Initializes the gunner mode list.
 */
public class GunnerModeManager extends DPadModeManager {
  public GunnerModeManager() {
    super(
      new GunnerFailsafeMode(),
      new GunnerCargoLoadingMode(),
      new GunnerHatchLoadingMode(),
      new GunnerCargoScoringMode(),
      new GunnerHatchScoringMode(),
      DPadValue.Up
    );
  }
}
