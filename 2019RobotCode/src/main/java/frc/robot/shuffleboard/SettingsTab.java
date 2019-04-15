package frc.robot.shuffleboard;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class SettingsTab {
  private static final String TITLE = "Settings";

  private static SettingsTab instance;

  private ShuffleboardTab tab;
  private NetworkTableEntry failsafeTrimForward;
  private NetworkTableEntry failsafeTrimReverse;

  public static double getFailsafeTrimForward() {
    return instance.failsafeTrimForward.getDouble(RobotMap.FAILSAFE_TRIM_FORWARD_DEFAULT);
  }

  public static double getFailsafeTrimReverse() {
    return instance.failsafeTrimReverse.getDouble(RobotMap.FAILSAFE_TRIM_REVERSE_DEFAULT);
  }

  public SettingsTab() {
    instance = this;
    tab = Shuffleboard.getTab(TITLE);

    failsafeTrimForward = tab.addPersistent(
      "Failsafe Trim Forward",
      RobotMap.FAILSAFE_TRIM_FORWARD_DEFAULT
    ).getEntry();

    failsafeTrimReverse = tab.addPersistent(
      "Failsafe Trim Reverse",
      RobotMap.FAILSAFE_TRIM_REVERSE_DEFAULT
    ).getEntry();

    tab.add(Robot.autoModes.positionChooser);
    tab.add(Robot.autoModes.taskChooser);
  }

  public void show() {
    Shuffleboard.selectTab(TITLE);
  }
}
