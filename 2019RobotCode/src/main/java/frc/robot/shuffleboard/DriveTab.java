package frc.robot.shuffleboard;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.Commands.ArmPresetCommand;
import frc.robot.Commands.DefenseModeSetCommand;
import frc.robot.Commands.FailsafeSetCommand;
import frc.robot.RobotMap.ArmPreset;

public class DriveTab {
  private static final String TITLE = "Drive";

  private ShuffleboardTab tab = null;
  private NetworkTableEntry failsafe;
  private NetworkTableEntry defenseMode;
  private NetworkTableEntry armHeight;
  private ToggleTrigger failsafeTrigger;
  private ToggleTrigger defenseModeTrigger;

  public DriveTab() {
    tab = Shuffleboard.getTab(TITLE);
  }

  public void show() {
    if (failsafe == null) {
      failsafe = tab.add("FAILSAFE", false)
        .withWidget(BuiltInWidgets.kToggleButton)
        .getEntry();

      defenseMode = tab.add("Defense", false)
        .withWidget(BuiltInWidgets.kToggleButton)
        .getEntry();

      failsafeTrigger = new ToggleTrigger(failsafe);
      failsafeTrigger.whenActive(new FailsafeSetCommand(true));
      failsafeTrigger.whenInactive(new FailsafeSetCommand(false));

      defenseModeTrigger = new ToggleTrigger(defenseMode);
      defenseModeTrigger.whenActive(new DefenseModeSetCommand(true));
      defenseModeTrigger.whenInactive(new DefenseModeSetCommand(false));
    }

    if (armHeight == null) {
      armHeight = tab.add("Arm", "").getEntry();
    }

    failsafe.setBoolean(Robot.getInstance().isFailsafeActive());

    Shuffleboard.selectTab(TITLE);
  }

  public void periodic() {
    if (armHeight != null) {
      ArmPreset preset = Robot.ARM_SUB.getLastPreset();
      int armValue = Robot.ARM_SUB.getValue();
      armHeight.setString(RobotMap.ArmPreset.getPresetString(preset, armValue));
    }
  }
}
