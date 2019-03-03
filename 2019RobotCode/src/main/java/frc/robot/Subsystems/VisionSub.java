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
  private NetworkTableEntry tv = table.getEntry("tv");
  private NetworkTableEntry tx = table.getEntry("tx");
  private NetworkTableEntry ta = table.getEntry("ta");
  private NetworkTableEntry ts = table.getEntry("ts");

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public double getTV(){
    return tv.getDouble(RobotMap.VISION_DEFAULT_RETURN_VALUE);
  }

  public double getTX(){
    return tx.getDouble(RobotMap.VISION_DEFAULT_RETURN_VALUE);
  }

  public double getTA(){
    return ta.getDouble(RobotMap.VISION_DEFAULT_RETURN_VALUE);
  }

  public double getTS(){
    return ts.getDouble(RobotMap.VISION_DEFAULT_RETURN_VALUE);
  }
}
