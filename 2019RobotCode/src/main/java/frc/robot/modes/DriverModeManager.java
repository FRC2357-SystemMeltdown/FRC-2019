package frc.robot.modes;

/**
 * Initializes the driver mode list.
 */
public class DriverModeManager extends DPadModeManager {
  private static ModeBase failsafeMode = new DriverFailsafeMode();
  private static ModeBase fastMode = new DriverFastMode();
  private static ModeBase slowMode = new DriverSlowMode();

  public DriverModeManager() {
    // failsafe, up, right, down, left, initial
    super( failsafeMode, fastMode, null, slowMode, null, failsafeMode );
  }
}
