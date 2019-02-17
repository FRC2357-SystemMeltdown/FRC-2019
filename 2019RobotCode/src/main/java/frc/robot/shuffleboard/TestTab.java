package frc.robot.shuffleboard;

import com.ctre.phoenix.motorcontrol.SensorCollection;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.Robot;

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

    driverMode.setString(Robot.getInstance().getDriverModeName());
    gunnerMode.setString(Robot.getInstance().getGunnerModeName());
  }
}
