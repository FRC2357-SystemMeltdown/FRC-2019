package frc.robot.shuffleboard;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.Commands.ArmPresetCommand;
import frc.robot.Commands.FailsafeSetCommand;
import frc.robot.RobotMap.ArmPreset;
import frc.robot.Commands.TipReflexSetCommand;

public class DriveTab {
  private static final String TITLE = "Drive";

  private ShuffleboardTab tab = null;
  private NetworkTableEntry failsafe;
  private NetworkTableEntry armHeight;
  private NetworkTableEntry tipReflex;
  private NetworkTableEntry tipWarning;
  private ToggleTrigger failsafeTrigger;
  private ToggleTrigger tipReflexTrigger;


  public DriveTab() {
    tab = Shuffleboard.getTab(TITLE);

    tipReflex = tab.add("Reflex Enable", true).getEntry();
    tipWarning = tab.add("Tip Reflex", true).getEntry();
  }

  public void show() {
    if (failsafe == null) {
      failsafe = tab.add("FAILSAFE", false)
        .withWidget(BuiltInWidgets.kToggleButton)
        .getEntry();

      failsafeTrigger = new ToggleTrigger(failsafe);
      failsafeTrigger.whenActive(new FailsafeSetCommand(true));
      failsafeTrigger.whenInactive(new FailsafeSetCommand(false));
    }

    if (armHeight == null) {
      armHeight = tab.add("Arm", "").getEntry();
    }

    if (tipReflex == null) {
      tipReflex = tab.add("Tip Reflex", true)
        .withWidget(BuiltInWidgets.kToggleButton)
        .getEntry();

        tipReflexTrigger = new ToggleTrigger(tipReflex);
        tipReflexTrigger.whenActive(new TipReflexSetCommand(true));
        tipReflexTrigger.whenInactive(new TipReflexSetCommand(false));
    }

    failsafe.setBoolean(Robot.getInstance().isFailsafeActive());
    tipWarning.setBoolean(Robot.DRIVE_SUB.isTipping());

    Shuffleboard.selectTab(TITLE);
  }

  public void periodic() {
    if (armHeight != null) {
      ArmPreset preset = ArmPresetCommand.getLastPreset();
      int armValue = Robot.ARM_SUB.getValue();
      armHeight.setString(RobotMap.ArmPreset.getPresetString(preset, armValue));
    }
  }
}
