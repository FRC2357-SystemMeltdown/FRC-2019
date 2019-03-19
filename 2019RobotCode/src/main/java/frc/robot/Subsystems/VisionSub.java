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
import frc.robot.RobotMap;

/**
 * Controls the limelight camera options.
 */
public class VisionSub extends SubsystemBase {
  public enum PipelineIndex {
    UNKNOWN(-1),
    VISION_TARGET(0),
    HUMAN_VIEW(1);

    public final int index;

    private PipelineIndex(int index) {
      this.index = index;
    }

    public static PipelineIndex getPipelineByIndex(int index) {
      for (PipelineIndex i : PipelineIndex.values()) {
        if (i.index == index) {
          return i;
        }
      }
      return UNKNOWN;
    }
  };

  private NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
  private NetworkTableEntry pipeline = table.getEntry("pipeline");
  private NetworkTableEntry tv = table.getEntry("tv");
  private NetworkTableEntry tx = table.getEntry("tx");
  private NetworkTableEntry ty = table.getEntry("ty");
  private NetworkTableEntry ta = table.getEntry("ta");
  private NetworkTableEntry ts = table.getEntry("ts");
  private NetworkTableEntry thor = table.getEntry("thor");
  private NetworkTableEntry tvert = table.getEntry("tvert");

  @Override
  protected void initDefaultCommand() {
  }

  public PipelineIndex getPipeline() {
    double value = pipeline.getDouble(Double.NaN);
    if (value >= 0.0 && value <= 9.0) {
      return PipelineIndex.getPipelineByIndex((int) value);
    }
    return PipelineIndex.UNKNOWN;
  }

  public void setPipeline(PipelineIndex p) {
    pipeline.setDouble(p.index);
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
