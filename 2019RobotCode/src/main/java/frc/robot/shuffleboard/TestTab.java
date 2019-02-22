package frc.robot.shuffleboard;

import com.ctre.phoenix.motorcontrol.SensorCollection;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.WidgetType;
import frc.robot.Robot;
import frc.robot.modes.ModeManager;

public class TestTab {
  private static final String TITLE = "Test Mode";

  private ShuffleboardTab tab;
  private NetworkTableEntry compressorEnabled;
  private NetworkTableEntry pressureLow;
  private NetworkTableEntry encoderLeft;
  private NetworkTableEntry encoderRight;
  private NetworkTableEntry hatchLimitLeft;
  private NetworkTableEntry hatchLimitRight;
  private NetworkTableEntry armPotentiometer;
  private NetworkTableEntry cargoLimitLeft;
  private NetworkTableEntry cargoLimitRight;
  private NetworkTableEntry driverMode;
  private NetworkTableEntry gunnerMode;
  private ToggleButton driverFailsafeButton;
  private ToggleButton gunnerFailsafeButton;

  public TestTab() {
    tab = Shuffleboard.getTab(TITLE);

    tab.add("Drive - Left", Robot.DRIVE_SUB.leftMaster);
    tab.add("Drive - Right", Robot.DRIVE_SUB.rightMaster);

    encoderLeft = tab.add("Drive Left", 0).getEntry();
    encoderRight = tab.add("Drive Right", 0).getEntry();

    compressorEnabled = tab.add("Compressor", false).getEntry();
    pressureLow = tab.add("Pressure", false).getEntry();

    armPotentiometer = tab.add("Arm Pot", 0).getEntry();

    tab.add("Arm Latch", Robot.ARM_SUB.latchingSolenoid);
    tab.add("Arm Move", Robot.ARM_SUB.movementSolenoid);

    tab.add("Cargo", Robot.CARGO_SUB.roller);

    cargoLimitLeft = tab.add("Cargo Left", false).getEntry();
    cargoLimitRight = tab.add("Cargo Right", false).getEntry();

    hatchLimitLeft = tab.add("Hatch Left", false).getEntry();
    hatchLimitRight = tab.add("Hatch Right", false).getEntry();

    driverMode = tab.add("Driver Mode", "").getEntry();
    gunnerMode = tab.add("Gunner Mode", "").getEntry();

    driverFailsafeButton = new ToggleButton(tab, "Driver Failsafe");
    gunnerFailsafeButton = new ToggleButton(tab, "Gunner Failsafe");
  }

  public void show() {
    Shuffleboard.selectTab(TITLE);
  }

  public void periodic() {
    int leftPosition = Robot.DRIVE_SUB.leftMaster.getSelectedSensorPosition();
    int rightPosition= Robot.DRIVE_SUB.rightMaster.getSelectedSensorPosition();

    encoderLeft.setNumber(leftPosition);
    encoderRight.setNumber(rightPosition);

    compressorEnabled.setBoolean(Robot.ARM_SUB.compressor.enabled());
    pressureLow.setBoolean(Robot.ARM_SUB.compressor.getPressureSwitchValue());

    armPotentiometer.setNumber(Robot.ARM_SUB.getPotentiometerValue());
    cargoLimitLeft.setBoolean(Robot.CARGO_SUB.limitLeft.get());
    cargoLimitRight.setBoolean(Robot.CARGO_SUB.limitRight.get());

    hatchLimitLeft.setBoolean(Robot.HATCH_SUB.leftLimitSwitch.get());
    hatchLimitRight.setBoolean(Robot.HATCH_SUB.rightLimitSwitch.get());

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
