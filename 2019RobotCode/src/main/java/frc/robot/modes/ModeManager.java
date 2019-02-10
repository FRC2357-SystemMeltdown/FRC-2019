package frc.robot.modes;

// This class keeps track of a mode state.
public class ModeManager {
  private ModeBase mode;

  protected ModeManager() {
    mode = null;
  }

  public ModeManager(ModeBase initialMode) {
    mode = initialMode;
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
}
