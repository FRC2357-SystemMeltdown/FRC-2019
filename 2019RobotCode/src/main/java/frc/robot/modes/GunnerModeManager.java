package frc.robot.modes;

/**
 * Initializes the gunner mode list.
 */
public class GunnerModeManager extends DPadModeManager {
  private static ModeBase failsafeMode = new GunnerFailsafeMode();

  public GunnerModeManager() {
    super(
      failsafeMode,     // failsafe
      failsafeMode, // up
      failsafeMode, // right
      failsafeMode, // down
      failsafeMode, // left
      failsafeMode  // initial
    );
  }
}
