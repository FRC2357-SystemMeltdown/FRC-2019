package frc.robot.modes;

import frc.robot.OI.DPadValue;

// This manager switches the current mode based on a d-pad input
public class DPadModeManager extends ModeManager {
  private ModeBase[] modes;

  /**
   * Use this constructor if the failsafe mode is not mapped to the dpad.
   * @param failsafeMode
   * @param upMode
   * @param rightMode
   * @param downMode
   * @param leftMode
   * @param initialMode
   */
  public DPadModeManager(
    ModeBase failsafeMode,
    ModeBase upMode,
    ModeBase rightMode,
    ModeBase downMode,
    ModeBase leftMode,
    ModeBase initialMode
  ) {
    super(initialMode, failsafeMode);
    createModeArray(upMode, rightMode, downMode, leftMode);
  }

  public void updateDPadValue(DPadValue newDirection) {
    if (newDirection != DPadValue.Unpressed && modes[newDirection.value] != null) {
      switchMode(modes[newDirection.value]);
    }
  }

  private void createModeArray(
    ModeBase upMode,
    ModeBase rightMode,
    ModeBase downMode,
    ModeBase leftMode
   ) {
    modes = new ModeBase[4];
    modes[DPadValue.Up.value] = upMode;
    modes[DPadValue.Right.value] = rightMode;
    modes[DPadValue.Down.value] = downMode;
    modes[DPadValue.Left.value] = leftMode;
  }
}
