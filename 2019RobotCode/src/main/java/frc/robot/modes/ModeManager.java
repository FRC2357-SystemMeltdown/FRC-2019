package frc.robot.modes;

/**
 * This class keeps track of a mode state.
 * @return
 */
public class ModeManager {
  private ModeBase mode;
  protected ModeBase failsafeMode;

  protected ModeManager() {
    mode = null;
    failsafeMode = null;
  }

  public ModeManager(ModeBase initialMode, ModeBase failsafeMode) {
    mode = initialMode;
    this.failsafeMode = failsafeMode;
    mode.activate();
  }

  public void switchMode(ModeBase newMode) {
    if(mode != newMode) {
      if(mode != null) {
        mode.deactivate();
      }
      mode = newMode;
      mode.activate();
    }
  }

  public void activateFailsafeMode() {
    switchMode(failsafeMode);
  }

  public ModeBase getCurrentMode() {
    return mode;
  }

  public boolean isFailsafeActive() {
    return mode == failsafeMode;
  }
}
