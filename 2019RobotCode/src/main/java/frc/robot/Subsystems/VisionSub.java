/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class VisionSub extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
  private NetworkTableEntry tx = table.getEntry("tx");
  private NetworkTableEntry ta = table.getEntry("ta");

  public enum LEDMode {
    PipelineDefault(0),
    Off(1),
    Blink(2),
    On(3);

    public final int value;

    private LEDMode(int value) {
      this.value = value;
    }
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
   * Set the LED mode on the limelight.
   * @param ledMode
   */
  public void setLEDMode(LEDMode ledMode) {
    table.getEntry("ledMode").setNumber(ledMode.value);
  }
}
