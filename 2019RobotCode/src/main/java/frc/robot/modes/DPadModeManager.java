package frc.robot.modes;

import frc.robot.OI;
import frc.robot.OI.DPadValue;

// This manager switches the current mode based on a d-pad input
public class DPadModeManager extends ModeManager {
  private ModeBase[] modes;
  private OI.DPadValue currentDPadValue;

  /**
   * Use this constructor if the failsafe mode is not mapped to the dpad.
   * @param failsafeMode
   * @param upMode
   * @param rightMode
   * @param downMode
   * @param leftMode
   * @param initialDirection
   */
  public DPadModeManager(
    ModeBase failsafeMode,
    ModeBase upMode,
    ModeBase rightMode,
    ModeBase downMode,
    ModeBase leftMode,
    DPadValue initialDirection
  ) {
    super();

    this.failsafeMode = failsafeMode;
    createModeArray(upMode, rightMode, downMode, leftMode);
    currentDPadValue = initialDirection;
    switchMode(modes[currentDPadValue.value]);
  }

  /**
   * Use this constructor if the failsafe mode is mapped to the dpad.
   * @param upMode
   * @param rightMode
   * @param downMode
   * @param leftMode
   * @param initialDirection
   * @param failsafeModeDirection
   */
  public DPadModeManager(
    ModeBase upMode,
    ModeBase rightMode,
    ModeBase downMode,
    ModeBase leftMode,
    DPadValue initialDirection,
    DPadValue failsafeModeDirection
  ) {
    super();

    createModeArray(upMode, rightMode, downMode, leftMode);
    this.failsafeMode = modes[failsafeModeDirection.value];
    currentDPadValue = initialDirection;
    switchMode(modes[currentDPadValue.value]);
  }

  public void updateDPadValue(DPadValue newValue) {
    if(newValue != DPadValue.Unpressed &&
       newValue != currentDPadValue &&
       modes[newValue.value] != null) {
      currentDPadValue = newValue;
      switchMode(modes[currentDPadValue.value]);
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
