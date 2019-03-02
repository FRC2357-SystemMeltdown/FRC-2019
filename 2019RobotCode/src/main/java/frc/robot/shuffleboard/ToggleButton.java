package frc.robot.shuffleboard;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

/**
 * A Shuffleboard toggle button.
 * 
 * This keeps track of when the button state changes for
 * events that should trigger on an edge.
 */
public class ToggleButton {
  private NetworkTableEntry netTab;
  private boolean lastValue;

  public ToggleButton(ShuffleboardTab tab, String title) {
    netTab = tab.add(title, false).withWidget(BuiltInWidgets.kToggleButton).getEntry();
    lastValue = false;
  }

  public boolean getValue() {
    return netTab.getBoolean(lastValue);
  }

  public void setValue(boolean value) {
    netTab.setBoolean(value);
    lastValue = value;
  }

  /**
   * Did the value of the button change since this was last called?
   * @return
   */
  public boolean didValueChange() {
    if(getValue() != lastValue) {
      lastValue = getValue();
      return true;
    }

    return false;
  }
}