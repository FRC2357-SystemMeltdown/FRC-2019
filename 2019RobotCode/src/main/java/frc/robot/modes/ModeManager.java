package frc.robot.modes;

/**
 * This class keeps track of a mode state.
 * @return
 */
public class ModeManager {
  private ModeBase currentMode;
  protected ModeBase failsafeMode;

  protected ModeManager() {
    currentMode = null;
    failsafeMode = null;
  }

  public ModeManager(ModeBase initialMode, ModeBase failsafeMode) {
    this.failsafeMode = failsafeMode;
    this.switchMode(initialMode);
  }

  public void switchMode(ModeBase newMode) {
    if (currentMode != newMode) {
      if (currentMode != null) {
        currentMode.deactivate();
      }
      currentMode = newMode;
      currentMode.activate();
    }
  }

  public void activateFailsafeMode() {
    switchMode(failsafeMode);
  }

  public ModeBase getCurrentMode() {
    return currentMode;
  }

  public boolean isFailsafeActive() {
    return currentMode == failsafeMode;
  }
}
