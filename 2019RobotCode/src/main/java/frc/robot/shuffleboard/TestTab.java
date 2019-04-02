package frc.robot.shuffleboard;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.Subsystems.VisionSub.TargetType;
import frc.robot.Subsystems.VisionSub.VisionTarget;

public class TestTab {
  private static final String TITLE = "Test Mode";

  private ShuffleboardTab tab = null;
  private NetworkTableEntry compressorEnabled;
  private NetworkTableEntry pressureLow;
  private NetworkTableEntry encoderLeft;
  private NetworkTableEntry encoderRight;
  private NetworkTableEntry armPotentiometer;
  private NetworkTableEntry cargoLimitLeft;
  private NetworkTableEntry cargoLimitRight;
  private NetworkTableEntry visionTarget;
  private NetworkTableEntry visionTargetDistance;
  private NetworkTableEntry visionTargetRotation;
  private NetworkTableEntry yaw;

  public TestTab() {
    tab = Shuffleboard.getTab(TITLE);

    tab.add("Drive - Left", Robot.DRIVE_SUB.leftMaster);
    tab.add("Drive - Right", Robot.DRIVE_SUB.rightMaster);

    encoderLeft = tab.add("Drive Left", 0).getEntry();
    encoderRight = tab.add("Drive Right", 0).getEntry();

    compressorEnabled = tab.add("Compressor", false).getEntry();
    pressureLow = tab.add("Pressure", false).getEntry();

    armPotentiometer = tab.add("Arm Pot", 0).getEntry();

    tab.add("Arm Up", Robot.ARM_SUB.upSolenoid);
    tab.add("Arm Down", Robot.ARM_SUB.downSolenoid);

    tab.add("Cargo", Robot.CARGO_SUB.roller);

    cargoLimitLeft = tab.add("Cargo Left", false).getEntry();
    cargoLimitRight = tab.add("Cargo Right", false).getEntry();

    visionTarget = tab.add("Target", "none").getEntry();
    visionTargetDistance = tab.add("T Distance", 0.0).getEntry();
    visionTargetRotation = tab.add("T Rotation", 0.0).getEntry();

    yaw = tab.add("Yaw", 0.0).getEntry();
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

    armPotentiometer.setNumber(Robot.ARM_SUB.potentiometer.getValue());
    cargoLimitLeft.setBoolean(Robot.CARGO_SUB.limitLeft.get());
    cargoLimitRight.setBoolean(Robot.CARGO_SUB.limitRight.get());

    String visionString = "none";
    double visionDistance = 0.0;
    double visionRotation = 0.0;

    VisionTarget target = Robot.VISION_SUB.getCurrentTarget();
    if (target != null) {
      visionString = "x=" + target.getX();
      visionDistance = target.getInchesFromTarget();
      visionRotation = target.getTargetRotationDegrees();
    }

    visionTarget.setString(visionString);
    visionTargetDistance.setDouble(visionDistance);
    visionTargetRotation.setDouble(visionRotation);

    yaw.setDouble(Robot.DRIVE_SUB.getYaw(false));
  }
}
