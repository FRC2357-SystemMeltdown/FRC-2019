/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Subsystems;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.Other.LimelightLEDMode;
import frc.robot.Other.LimelightPiPMode;

/**
 * Add your docs here.
 */
public class VisionSub extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private NetworkTable table;
  private NetworkTableEntry tx;
  private NetworkTableEntry ta;
  private UsbCamera webCam;

  public VisionSub(){
    table = NetworkTableInstance.getDefault().getTable("limelight");
    tx = table.getEntry("tx");
    ta = table.getEntry("ta");

    webCam = CameraServer.getInstance().startAutomaticCapture(0);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public double getTargetXAngle(){
    return tx.getDouble(RobotMap.VISION_DEFAULT_RETURN_VALUE);
  }

  public double getTargetArea(){
    return ta.getDouble(RobotMap.VISION_DEFAULT_RETURN_VALUE);
  }

  /**
   * Enable or disable vision processing on the limelight. If vision processing
   * is disabled the exposure is increased for easier human viewing.
   * @param enable
   */
  public void setVisionProcessingEnabled(boolean enable) {
    table.getEntry("camMode").setNumber(enable ? 0 : 1);
  }

  /**
   * Set the LED mode on the Limelight.
   * @param ledMode
   */
  public void setLimelightLEDMode(LimelightLEDMode ledMode) {
    table.getEntry("ledMode").setNumber(ledMode.value);
  }

  /**
   * Set the picture in picture mode on the Limelight.
   * @param pipMode
   */
  public void setLimelightPiPMode(LimelightPiPMode pipMode) {
    table.getEntry("stream").setNumber(pipMode.value);
  }
}
