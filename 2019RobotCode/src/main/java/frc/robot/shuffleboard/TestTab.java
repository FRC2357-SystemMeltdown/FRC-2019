package frc.robot.shuffleboard;

import com.ctre.phoenix.motorcontrol.SensorCollection;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.Robot;

public class TestTab {
  private static final String TITLE = "Test Mode";

  private ShuffleboardTab tab;
  private NetworkTableEntry encoderLeft;
  private NetworkTableEntry encoderRight;
  private NetworkTableEntry hatchLimitLeft;
  private NetworkTableEntry hatchLimitRight;

  public TestTab() {
    tab = Shuffleboard.getTab(TITLE);
    tab.add("Drive - Left", Robot.DRIVE_SUB.leftMaster);
    tab.add("Drive - Right", Robot.DRIVE_SUB.rightMaster);

    encoderLeft = tab.add("Drive Encoder - Left", 0).getEntry();
    encoderRight = tab.add("Drive Encoder - Right", 0).getEntry();

    hatchLimitLeft = tab.add("Hatch Limit - Left", false).getEntry();
    hatchLimitRight = tab.add("Hatch Limit - Right", false).getEntry();
  }

  public void show() {
    Shuffleboard.selectTab(TITLE);
  }

  public void periodic() {
    int leftPosition = Robot.DRIVE_SUB.leftMaster.getSelectedSensorPosition();
    int rightPosition= Robot.DRIVE_SUB.rightMaster.getSelectedSensorPosition();

    encoderLeft.setNumber(leftPosition);
    encoderRight.setNumber(rightPosition);

    hatchLimitLeft.setBoolean(Robot.HATCH_SUB.leftLimitSwitch.get());
    hatchLimitRight.setBoolean(Robot.HATCH_SUB.rightLimitSwitch.get());
  }
}
