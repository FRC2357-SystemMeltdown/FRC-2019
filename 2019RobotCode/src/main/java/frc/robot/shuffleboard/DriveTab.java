package frc.robot.shuffleboard;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.Robot;
import frc.robot.modes.ModeManager;
import frc.robot.modes.GunnerModeManager;

public class DriveTab {
  private static final String TITLE = "Drive";

  private ShuffleboardTab tab = null;
  private NetworkTableEntry driverMode;
  private NetworkTableEntry gunnerMode;
  private ToggleButton driverFailsafeButton;
  private ToggleButton gunnerFailsafeButton;
  private NetworkTableEntry yaw;

  public void show() {
    if (tab == null) {
      tab = Shuffleboard.getTab(TITLE);

      driverMode = tab.add("Driver Mode", "").getEntry();
      gunnerMode = tab.add("Gunner Mode", "").getEntry();

      driverFailsafeButton = new ToggleButton(tab, "D-FAILSAFE");
      gunnerFailsafeButton = new ToggleButton(tab, "G-FAILSAFE");

      yaw = tab.add("Yaw", 0).getEntry();
    }

    Shuffleboard.selectTab(TITLE);
  }

  public void periodic() {
    if (tab == null) {
      return;
    }

    ModeManager driverModeMgr = Robot.getInstance().getDriverModeManager();
    ModeManager gunnerModeMgr = Robot.getInstance().getGunnerModeManager();

    driverMode.setString(driverModeMgr.getCurrentMode().getModeName());
    gunnerMode.setString(gunnerModeMgr.getCurrentMode().getModeName());

    updateFailsafeButton(driverFailsafeButton, driverModeMgr);
    updateGunnerFailsafe();

    yaw.setDouble(Robot.DRIVE_SUB.getYaw(false));
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

  private void updateGunnerFailsafe() {
    GunnerModeManager gunnerModeMgr = Robot.getInstance().getGunnerModeManager();

    if (gunnerFailsafeButton.didValueChange()) {
      if (gunnerFailsafeButton.getValue()) {
        gunnerModeMgr.activateFailsafeMode();
      } else {
        gunnerModeMgr.activateNormalMode();
      }
    }
  }
}
