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
  private NetworkTableEntry ty = table.getEntry("ty");
  private NetworkTableEntry ta = table.getEntry("ta");
  private NetworkTableEntry ts = table.getEntry("ts");
  private NetworkTableEntry thor = table.getEntry("thor");
  private NetworkTableEntry tvert = table.getEntry("tvert");

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public double getTV() {
    return tv.getDouble(RobotMap.VISION_DEFAULT_RETURN_VALUE);
  }

  public double getTX() {
    return tx.getDouble(RobotMap.VISION_DEFAULT_RETURN_VALUE);
  }

  public double getTY() {
    return ty.getDouble(RobotMap.VISION_DEFAULT_RETURN_VALUE);
  }

  public double getTA() {
    return ta.getDouble(RobotMap.VISION_DEFAULT_RETURN_VALUE);
  }

  public double getTS() {
    return ts.getDouble(RobotMap.VISION_DEFAULT_RETURN_VALUE);
  }

  public double getTHOR() {
    return thor.getDouble(RobotMap.VISION_DEFAULT_RETURN_VALUE);
  }

  public double getTVERT() {
    return tvert.getDouble(RobotMap.VISION_DEFAULT_RETURN_VALUE);
  }

  public double getInchesFromTarget(double targetHeight) {
    double angleDegrees = Math.abs(getTY()) + RobotMap.LIMELIGHT_MOUNTING_ANGLE;

    double heightDifference = RobotMap.LIMELIGHT_MOUNTING_HEIGHT_INCHES - targetHeight;
    double distance = heightDifference / Math.tan(Math.toRadians(angleDegrees));

    return distance;
  }

  public boolean isHeadOnTarget() {
    return getTS() == 0.0;
  }

  public boolean isRightOfTarget() {
    double ts = getTS();
    return ts <= RobotMap.LIMELIGHT_SKEW_CLOCKWISE_MAX &&
      ts >= RobotMap.LIMELIGHT_SKEW_CLOCKWISE_MIN;
  }

  public boolean isLeftOfTarget() {
    double ts = getTS();
    return ts >= RobotMap.LIMELIGHT_SKEW_COUNTERCLOCKWISE_MAX &&
      ts <= RobotMap.LIMELIGHT_SKEW_COUNTERCLOCKWISE_MIN;
  }

  public double getTargetRotationDegrees() {
    if (isHeadOnTarget()) {
      return 0.0;
    }
    if (isRightOfTarget()) {
      return - getRotationAngle();
    }
    if (isLeftOfTarget()) {
      return getRotationAngle();
    }

    return Double.NaN;
  }

  private double getRotationAngle() {
    double proportion = getTHOR() / getTVERT();
    double factor = proportion / RobotMap.FIELD_VISION_TARGET_PROPORTION;
    return 90.0 - (factor * 90.0);
  }
}
