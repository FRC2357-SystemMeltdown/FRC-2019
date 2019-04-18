/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package frc.robot;

import frc.robot.Other.PIDValues;

/**
 * Add your docs here.
 */
public class RobotMap {
  // General Purpose
  public static final int MILLISECONDS_PER_SECOND = 1000;
  public static final int MILLIS_PER_MINUTE = 60 * MILLISECONDS_PER_SECOND;
  public static final double STOP_SPEED = 0.0;

  // CAN IDs
  public static final int CAN_ID_RIGHT_DRIVE        = 1;
  public static final int CAN_ID_RIGHT_DRIVE_SLAVE  = 2;
  public static final int CAN_ID_LEFT_DRIVE_SLAVE   = 3;
  public static final int CAN_ID_LEFT_DRIVE         = 4;
  public static final int CAN_ID_PIGEON_IMU         = 6;
  public static final int CAN_ID_CARGO_INTAKE       = 7;
  public static final int CAN_ID_LEFT_HATCH_GANTRY  = 8;
  public static final int CAN_ID_RIGHT_HATCH_GANTRY = 9;
  public static final int CAN_ID_PDP                = 20;
  public static final int CAN_ID_PCM                = 25;

  // Analog In IDs
  public static final int ANALOG_PORT_ARM_POTENTIOMETER = 0;

  // DIO IDs
  public static final int DIO_PORT_CARGO_LIMIT_LEFT   = 8;
  public static final int DIO_PORT_CARGO_LIMIT_RIGHT  = 9;
  public static final int DIO_PORT_HATCH_LEFT         = 2;
  public static final int DIO_PORT_HATCH_RIGHT        = 3;

  //PCM IDs
  public static final int PCM_PORT_DOWN = 0;
  public static final int PCM_PORT_UP = 1;
  public static final int PCM_PORT_DEFENSE_MODE_ON = 2;
  public static final int PCM_PORT_DEFENSE_MODE_OFF = 3;

  // Drive System
  public static final int GYRO_UNITS_PER_ROTATION = 8192; // Pigeon IMU
  public static final int GYRO_AXIS_YAW = 0;
  public static final int GYRO_AXIS_PITCH = 1;
  public static final int GYRO_AXIS_ROLL = 2;
  public static final int GYRO_AXIS_TOTAL = 3;
  public static final int ENCODER_TICKS_PER_ROTATION = 1024;
  public static final double WHEEL_CIRCUMFERENCE_INCHES = 6.375 * Math.PI;
  public static final double MOTOR_MINIMUM_POWER = 0.05;

  // Drive Controls
  public static final double DRIVE_STICK_DEADBAND = 0.1;
  public static final double DRIVE_RAMP_SECONDS = 1.0;
  public static final double DRIVE_MOTOR_DEADBAND = 0.04;
  public static final double DRIVER_SPEED_PROPORTION = 1.0;
  public static final double DRIVER_TURN_PROPORTION = 1.0;
  public static final double DRIVER_SPEED_PROPORTION_SLOW = 0.6;
  public static final double DRIVER_TURN_PROPORTION_SLOW = 0.6;
  public static final double GUNNER_SPEED_PROPORTION = 0.5;
  public static final double GUNNER_TURN_PROPORTION = 0.5;
  public static final double FAILSAFE_TRIM_FORWARD_DEFAULT = 0.0;
  public static final double FAILSAFE_TRIM_REVERSE_DEFAULT = 0.0;
  public static final double DRIVER_SLOW_TRIGGER_THRESHOLD = 0.25;

  //Drive PID Values
  /*
  Feed forward value is derived from motor speed at maximum controller output. Run the
  Phoenix Tuner self-test for each motor while driving it at maximum speed. Look at the encoder
  velocity under PID0:

  PID0 (primary)
  Feedback: Quad/MagEnc(rel)
  Pos: 347u   |   Vel: 0u/100ms
                       ^-- This value

  The feed forward is 1023 / <maximum speed>
  */

  /*
  209/170

  Left 279, Right 322
  */

  public static final int DRIVE_MAX_RPMS = 1100;
  public static final double VEL_FEED_FWD = 1023.0 / DRIVE_MAX_RPMS;

  public static final int VELOCITY_UNITS_PER_MIN = MILLIS_PER_MINUTE / 100;
  public static final int MAX_ENCODER_VELOCITY = DRIVE_MAX_RPMS * ENCODER_TICKS_PER_ROTATION / VELOCITY_UNITS_PER_MIN;

  public static final int DRIVER_ENCODER_TURN_RATE = (int)(MAX_ENCODER_VELOCITY * 0.60);
  public static final int DRIVER_ENCODER_SPEED = MAX_ENCODER_VELOCITY;
  public static final int DRIVER_ENCODER_SLOW_TURN_RATE = (int)(MAX_ENCODER_VELOCITY * 0.5);
  public static final int DRIVER_ENCODER_SLOW_SPEED = (int)(MAX_ENCODER_VELOCITY * 0.5);
  public static final int GUNNER_ENCODER_TURN_RATE = (int)(MAX_ENCODER_VELOCITY * 0.5);
  public static final int GUNNER_ENCODER_SPEED = (int)(MAX_ENCODER_VELOCITY * 0.5);
  public static final double DRIVER_ENCODER_MAX_FORWARD_LIMIT_FACTOR = 1.07;
  public static final int DRIVER_ENCODER_MAX_DIFF = 75;

  // PID Values
  //  Output units: motor percent (-1.0 to +1.0)                    P       I       D    feed forward  izone   peak
  public static final PIDValues PID_DRIVE_SPEED = new PIDValues( 0.50,    0.0,   40.0,            0.0,     0,  1.00);
  public static final PIDValues PID_DRIVE_POS   = new PIDValues( 0.10, 0.0002,   10.0,            0.0,     0,  0.23);

  // Output units: drive encoder clicks (-1700 to 1700)             P       I       D    feed forward  izone   peak
  public static final PIDValues PID_DRIVE_YAW   = new PIDValues( 14.0,  0.620,  400.0,            0.0,     0,   950);
  public static final PIDValues PID_VISION_YAW  = new PIDValues(  3.0,  2.000,   80.0,            0.0,     0,   950);
  public static final PIDValues PID_ADJUST_YAW  = new PIDValues( 45.0,    0.0,    0.0,            0.0,     0,   950);

  public static final double VISION_DISTANCE_TO_HATCH_LOAD = 28.0;
  public static final double VISION_DISTANCE_TO_HATCH_LOW = 23.0;
  public static final double VISION_DISTANCE_TO_HATCH_MID = 25.0;
  public static final double VISION_DISTANCE_TO_HATCH_HIGH = 23.0;

  public static final int PID_DRIVE_POSITION_ACCURACY = 50;
  public static final double PID_ROTATION_POSITION_ACCURACY = 1.0;
  public static final double PID_VISION_CENTER_ACCURACY = 0.25;
  public static final double PID_VISION_TARGET_ROTATION_ACCURACY = 5.0;
  public static final double PID_VISION_TURN_ERR_ACCURACY = 1.0;
  public static final double PID_VISION_TARGET_ROTATION_TO_X_OFFSET = 1.5;
  public static final double PID_VISION_TARGET_DISTANCE_CENTERING_THRESHOLD_INCHES = 12;
  public static final int PID_VISION_TARGET_ROTATION_SAMPLES = 20;

  public static final int TALON_TIMEOUT_MS = 30;
  public static final int TALON_PID_PRIMARY = 0;
  public static final int TALON_PID_SECONDARY = 1;
  public final static int TALON_SLOT_DISTANCE = 0;
	public final static int TALON_SLOT_TURNING = 1;
	public final static int TALON_SLOT_VELOCITY = 2;
	public final static int TALON_SLOT_MOTION_PROFILE = 3;

  //=====
  // Arm
  //=====
  // Constant for failsafe, not a real angle.
  public static final int ARM_FAILSAFE_ANGLE = -1;

  // The starting angle is absolute from the potentiometer at resting position.
  // Adjust this whenever we remove and reinstall the potentiometer.

  // Practice Robot
  //public static final int ARM_STARTING_ANGLE = 3015;

  // Competition Robot
  public static final int ARM_STARTING_ANGLE = 3461;

  // The rest of these values are relative to the starting angle
  // They should only need to be tweaked, not when the potentiometer is changed.
  public enum ArmPreset {
    //               Name            Potentiometer value          up  down (overshoots)
    Failsafe(        "FAILSAFE",     -1,                           0,   0),
    Defense(         "DEFENSE",      ARM_STARTING_ANGLE,           0,  20),
    Start(           "START",        ARM_STARTING_ANGLE,           0, 110),
    CargoPickup(     "C FLOOR",      ARM_STARTING_ANGLE - 100,    99,  45),
    Low(             "LOW",          ARM_STARTING_ANGLE - 375,   190,  75),
    CargoShip(       "C SHIP",       ARM_STARTING_ANGLE - 760,   130,  85),
    Mid(             "MID",          ARM_STARTING_ANGLE - 1105,  180, 120),
    High(            "HIGH",         ARM_STARTING_ANGLE - 1980,  360,   0);

    public final int value;
    public final String name;
    public final int upOvershoot;
    public final int downOvershoot;

    private ArmPreset(String name, int value, int upOvershoot, int downOvershoot) {
      this.value = value;
      this.name = name;
      this.upOvershoot = upOvershoot;
      this.downOvershoot = downOvershoot;
    }

    public static ArmPreset getNearestPreset(int value) {
      ArmPreset nearest = ArmPreset.Start;
      int nearestDiff = Integer.MAX_VALUE;

      for (ArmPreset preset : ArmPreset.values()) {
        if (preset == ArmPreset.Failsafe ||
            preset == ArmPreset.Defense) {
          continue;
        }

        int diff = Math.abs(value - preset.value);
        if (diff < nearestDiff) {
          nearest = preset;
          nearestDiff = diff;
        }
      }
      return nearest;
    }

    /**
     * Gets a string representation of the current arm preset value.
     * @param preset The preset the value should be relative to.
     * @param value The potentiometer value.
     * @return A string with the preset and adjustment.
     */
    public static String getPresetString(ArmPreset preset, int value) {
      if (value < preset.value) {
        return preset.name + " " + (value - preset.value);
      } else if (value > preset.value) {
        return preset.name + " +" + (value - preset.value);
      } else {
        return preset.name;
      }
    }

    public static ArmPreset getNext(ArmPreset preset) {
      int index = preset.ordinal();
      if (index < values().length - 1) {
        return values()[index + 1];
      }
      return preset;
    }

    public static ArmPreset getPrevious(ArmPreset preset) {
      int index = preset.ordinal();
      if (index > Defense.ordinal() + 1) {
        return values()[index - 1];
      }
      return preset;
    }
  }

  public static final int ARM_TARGET_TOLERANCE = 5;

  public static final int ARM_ADJUST_UP = -10;
  public static final int ARM_ADJUST_DOWN = 10;

  // Cargo
  public static final double INTAKE_IN_SPEED = -1.0;
  public static final double INTAKE_OUT_SPEED = 0.7;

  //--------
  // Vision
  //--------
  public static final double VISION_DEFAULT_RETURN_VALUE = 0.0;

  // (shorter=closer, taller=farther)
  public static final double LIMELIGHT_MOUNTING_HEIGHT_INCHES = 39.5;

  // (lower=farther, higher=closer)
  public static final double LIMELIGHT_MOUNTING_ANGLE = -2.4;

  // Calibrate centered at 5 feet.
  public static final double LIMELIGHT_SKEW_CLOCKWISE_MIN = -89.99;
  public static final double LIMELIGHT_SKEW_CLOCKWISE_MAX = -60.00;
  public static final double LIMELIGHT_SKEW_COUNTERCLOCKWISE_MIN = -0.01;
  public static final double LIMELIGHT_SKEW_COUNTERCLOCKWISE_MAX = -30.00;

  //-------
  // Field
  //-------
  // Vision target overall dimensions: 14 3/4" x 5 7/8"
  public static final double FIELD_VISION_TARGET_WIDTH = 14.75;
  public static final double FIELD_VISION_TARGET_HEIGHT = 5.875;
  public static final double FIELD_VISION_TARGET_PROPORTION =
    FIELD_VISION_TARGET_WIDTH / FIELD_VISION_TARGET_HEIGHT;

  // 2' 7 1/2"
  public static final double FIELD_HATCH_TARGET_TOP_FROM_FLOOR = 31.5;

  // 3' 3 1/8"
  public static final double FIELD_CARGO_TARGET_TOP_FROM_FLOOR = 39.125;
}
