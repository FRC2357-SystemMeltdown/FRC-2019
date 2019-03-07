package frc.robot.modes;

/**
 * Initializes the gunner mode list.
 */
public class GunnerModeManager extends DPadModeManager {
  private static ModeBase failsafeMode = new GunnerFailsafeMode();
  private static ModeBase cargoLoadingMode = new GunnerCargoLoadingMode();
  private static ModeBase hatchLoadingMode = new GunnerHatchLoadingMode();
  private static ModeBase cargoScoringMode = new GunnerCargoScoringMode();
  private static ModeBase hatchScoringMode = new GunnerHatchScoringMode();

  public GunnerModeManager() {
    super(
      failsafeMode,     // failsafe
      cargoLoadingMode, // up
      hatchScoringMode, // right
      hatchLoadingMode, // down
      cargoScoringMode, // left
      hatchScoringMode  // initial
    );
  }
}
