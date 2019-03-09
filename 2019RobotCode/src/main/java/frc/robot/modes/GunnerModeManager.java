package frc.robot.modes;

/**
 * Initializes the gunner mode list.
 */
public class GunnerModeManager extends ModeManager {
  private static ModeBase normal = new GunnerNormalMode();
  private static ModeBase failsafe = new GunnerFailsafeMode();

  public GunnerModeManager() {
    super(normal, failsafe);
  }

  public void activateNormalMode() {
    switchMode(normal);
  }
}
