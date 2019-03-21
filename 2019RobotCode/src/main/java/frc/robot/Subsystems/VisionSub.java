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
  public enum TargetType {
    HATCH, CARGO
  }

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
  }

  public static class VisionTarget {
    public final TargetType type;
    private final double ts;
    private final double thor;
    private final double tvert;
    private final double tx;
    private final double ty;

    protected VisionTarget(TargetType type, double ts, double thor, double tvert, double tx, double ty) {
      this.type = type;
      this.ts = ts;
      this.thor = thor;
      this.tvert = tvert;
      this.tx = tx;
      this.ty = ty;
    }

    public double getX() {
      return tx;
    }

    public boolean isHeadOn() {
      return ts == 0.0;
    }

    public boolean isToLeft() {
      return ts <= RobotMap.LIMELIGHT_SKEW_CLOCKWISE_MAX &&
        ts >= RobotMap.LIMELIGHT_SKEW_CLOCKWISE_MIN;
    }

    public boolean isToRight() {
      return ts >= RobotMap.LIMELIGHT_SKEW_COUNTERCLOCKWISE_MAX &&
        ts <= RobotMap.LIMELIGHT_SKEW_COUNTERCLOCKWISE_MIN;
    }

    public double getTargetRotationDegrees() {
      if (isHeadOn()) {
        return 0.0;
      }
      if (isToLeft()) {
        return - getRotationAngle();
      }
      if (isToRight()) {
        return getRotationAngle();
      }

      return Double.NaN;
    }

    private double getRotationAngle() {
      double proportion = thor / tvert;
      double factor = proportion / RobotMap.FIELD_VISION_TARGET_PROPORTION;
      return 90.0 - (factor * 90.0);
    }

    public double getTargetHeight() {
      switch (type) {
        case HATCH:
          return RobotMap.FIELD_HATCH_TARGET_TOP_FROM_FLOOR;
        case CARGO:
          return RobotMap.FIELD_CARGO_TARGET_TOP_FROM_FLOOR;
        default:
          return 0;
      }
    }

    public double getInchesFromTarget() {
      double angleDegrees = Math.abs(ty) + RobotMap.LIMELIGHT_MOUNTING_ANGLE;

      double heightDifference = RobotMap.LIMELIGHT_MOUNTING_HEIGHT_INCHES - getTargetHeight();
      double distance = heightDifference / Math.tan(Math.toRadians(angleDegrees));

      return distance;
    }
  }

  private NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
  private NetworkTableEntry pipeline = table.getEntry("pipeline");
  private NetworkTableEntry tv = table.getEntry("tv");
  private NetworkTableEntry tx = table.getEntry("tx");
  private NetworkTableEntry ty = table.getEntry("ty");
  private NetworkTableEntry ta = table.getEntry("ta");
  private NetworkTableEntry ts = table.getEntry("ts");
  private NetworkTableEntry thor = table.getEntry("thor");
  private NetworkTableEntry tvert = table.getEntry("tvert");

  private VisionTarget currentTarget = null;
  private VisionTarget lastVisibleTarget = null;

  @Override
  protected void initDefaultCommand() {
  }

  public VisionTarget getCurrentTarget() {
    return currentTarget;
  }

  public VisionTarget getLastVisibleTarget() {
    return lastVisibleTarget;
  }

  public VisionTarget acquireTarget(TargetType type) {
    if (0 < getTV()) {
      currentTarget = new VisionTarget(type, getTS(), getTHOR(), getTVERT(), getTX(), getTY());
      lastVisibleTarget = currentTarget;
    } else {
      currentTarget = null;
    }
    return currentTarget;
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
}
