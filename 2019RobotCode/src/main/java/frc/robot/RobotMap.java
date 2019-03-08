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

  // Drive System
  public static final int GYRO_UNITS_PER_ROTATION = 8192; // Pigeon IMU
  public static final int GYRO_AXIS_YAW = 0;
  public static final int GYRO_AXIS_PITCH = 1;
  public static final int GYRO_AXIS_ROLL = 2;
  public static final int GYRO_AXIS_TOTAL = 3;
  public static final int ENCODER_TICKS_PER_ROTATION = 1024;
  public static final double WHEEL_CIRCUMFERENCE_INCHES = 6 * Math.PI;
  public static final double MOTOR_MINIMUM_POWER = 0.05;

  // Drive Controls
  public static final int DRIVE_RAMP_SECONDS = 1;
  public static final double DRIVE_MOTOR_DEADBAND = 0.04;
  public static final double DRIVER_SPEED_PROPORTION = 0.8;
  public static final double DRIVER_TURN_PROPORTION = 0.8;
  public static final double DRIVER_SPEED_PROPORTION_SLOW = 0.6;
  public static final double DRIVER_TURN_PROPORTION_SLOW = 0.6;
  public static final double GUNNER_SPEED_PROPORTION = 0.5;
  public static final double GUNNER_TURN_PROPORTION = 0.5;
  public static final double MAX_TURN_RATE_DEGREES_PER_SECOND = 180;
  public static final double MAX_VELOCITY_INCHES_PER_SECOND = 40;
  public static final double FAILSAFE_TRIM_FORWARD_DEFAULT = 0.0;
  public static final double FAILSAFE_TRIM_REVERSE_DEFAULT = 0.0;

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
  public static final PIDValues PID_SPEED_LEFT_DRIVE = new PIDValues(2, 0, 0, 1023.0 / 900.0, 0);
  public static final PIDValues PID_SPEED_RIGHT_DRIVE = new PIDValues(2, 0, 0, 1023.0 / 900.0, 0);

  // PID values for position based movement
  public static final PIDValues PID_POS_LEFT_DRIVE = new PIDValues(0.6 * 1023 / ENCODER_TICKS_PER_ROTATION, 0, 0, 0, 0);
  public static final PIDValues PID_POS_RIGHT_DRIVE = new PIDValues(0.6 * 1023 / ENCODER_TICKS_PER_ROTATION, 0, 0, 0, 0);

  public static final PIDValues PID_GYRO = new PIDValues(0.001 / 90.0, 0, 0.03 / 90.0, 0, 0);
  public static final PIDValues PID_ARM = new PIDValues(0, 0, 0, 0, 0);
  public static final double DRIVE_TRAIN_SAMPLE_PERIOD = 1 / 5;

  // Arm
  public static final int ARM_MAX_ANGLE = 270;
  public static final int ARM_MIN_ANGLE = 0;
  public static final int ARM_RANGE = ARM_MAX_ANGLE - ARM_MIN_ANGLE;
  public static final int ARM_POTENTIOMETER_MAX = 4096;
  public static final int ARM_POTENTIOMETER_MIN = 0;
  public static final int ARM_POTENTIOMETER_RANGE = ARM_POTENTIOMETER_MAX - ARM_POTENTIOMETER_MIN;
  public static final double ARM_ANGLE_FACTOR = ARM_POTENTIOMETER_RANGE / ARM_RANGE;
  public static final double ARM_CARGO_HIGH_GOAL_ANGLE = 70.0;
  public static final double ARM_CARGO_MID_GOAL_ANGLE = 40.0;
  public static final double ARM_CARGO_LOW_GOAL_ANGLE = 10.0;
  public static final double ARM_HATCH_HIGH_GOAL_ANGLE = 70.0;
  public static final double ARM_HATCH_MID_GOAL_ANGLE = 40.0;
  public static final double ARM_HATCH_LOW_GOAL_ANGLE = 10.0;
  public static final double ARM_CARGO_PICKUP_ANGLE = 5.0;
  public static final double ARM_STARTING_ANGLE = 0.0;
  public static final double ARM_ANGLE_TOLERANCE = 1.0;
  public static final double ARM_COASTING_PERCENTAGE = 0.1;
  public static final double SERVO_LOCK_POSITION = 1.0;
  public static final double SERVO_RETRACTED_POSITION = 0.0;

  // Cargo
  public static final double INTAKE_IN_SPEED = -1.0;
  public static final double INTAKE_OUT_SPEED = 0.7;

  // Hatch
  public static final double HATCH_FAILSAFE_MOVEMENT_SPEED = 0.7;
  public static final double HATCH_ENCODER_TICKS_PER_ROTATION = 256;
  public static final double HATCH_INCHES_PER_ROTATION = 3; // ?? wild guess
  public static final PIDValues HATCH_LEFT_PID = new PIDValues(0, 0, 0, 0, 0);
  public static final PIDValues HATCH_RIGHT_PID = new PIDValues(0, 0, 0, 0, 0);
  public static final double HATCH_ACCELERATION = 4; // inches/s/s
  public static final double HATCH_MAX_VELOCITY = 8; // inches/s

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

  // General Purpose
  public static final int MILLISECONDS_PER_SECOND = 1000;
  public static final double STOP_SPEED = 0.0;
}
