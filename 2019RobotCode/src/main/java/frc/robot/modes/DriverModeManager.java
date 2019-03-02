package frc.robot.modes;

/**
 * Initializes the driver mode list.
 */
public class DriverModeManager extends DPadModeManager {
  private static ModeBase failsafeMode = new DriverFailsafeMode();
  private static ModeBase pidMode = new DriverPIDMode();

  public DriverModeManager() {
    // failsafe, up, right, down, left, initial
    super( failsafeMode, pidMode, null, null, null, pidMode );
  }
}
