package frc.robot.modes;

import frc.robot.OI;
import frc.robot.OI.DPadValue;

// This manager switches the current mode based on a d-pad input
public class DPadModeManager extends ModeManager {
  private ModeBase[] modes;
  private OI.DPadValue currentDPadValue;

  public DPadModeManager(
    ModeBase upMode,
    ModeBase rightMode,
    ModeBase downMode,
    ModeBase leftMode,
    DPadValue initialDirection
  )
  {
    modes = new ModeBase[4];
    modes[OI.DPadValue.Up.value] = upMode;
    modes[OI.DPadValue.Right.value] = rightMode;
    modes[OI.DPadValue.Down.value] = downMode;
    modes[OI.DPadValue.Left.value] = leftMode;
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
}
