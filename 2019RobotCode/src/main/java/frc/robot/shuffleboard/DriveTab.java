package frc.robot.shuffleboard;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.Robot;
import frc.robot.modes.ModeManager;

public class DriveTab {
  private static final String TITLE = "Drive";

  private ShuffleboardTab tab;
  private NetworkTableEntry driverMode;
  private NetworkTableEntry gunnerMode;
  private ToggleButton driverFailsafeButton;
  private ToggleButton gunnerFailsafeButton;

  public DriveTab() {
    tab = Shuffleboard.getTab(TITLE);

    driverMode = tab.add("Driver Mode", "").getEntry();
    gunnerMode = tab.add("Gunner Mode", "").getEntry();

    driverFailsafeButton = new ToggleButton(tab, "D-FAILSAFE");
    gunnerFailsafeButton = new ToggleButton(tab, "G-FAILSAFE");
  }

  public void show() {
    Shuffleboard.selectTab(TITLE);
  }

  public void periodic() {
    ModeManager driverModeMgr = Robot.getInstance().getDriverModeManager();
    ModeManager gunnerModeMgr = Robot.getInstance().getGunnerModeManager();

    driverMode.setString(driverModeMgr.getCurrentMode().getModeName());
    gunnerMode.setString(gunnerModeMgr.getCurrentMode().getModeName());

    updateFailsafeButton(driverFailsafeButton, driverModeMgr);
    updateFailsafeButton(gunnerFailsafeButton, gunnerModeMgr);
  }

  /**
   * Update the state of a failsafe button.
   *
   * If the button has changed to true the failsafe mode will
   * be activated.
   * @param button
   * @param mgr
   */
  private void updateFailsafeButton(ToggleButton button, ModeManager mgr) {
    if(button.didValueChange() && button.getValue()) {
      mgr.activateFailsafeMode();
    } else {
      button.setValue(mgr.isFailsafeActive());
    }
  }
}
