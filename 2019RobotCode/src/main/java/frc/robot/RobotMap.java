/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package frc.robot;

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

  // PWM IDs
  public static final int PWM_ID_ARM_LOCK_SERVO = 0;

  // Analog In IDs
  public static final int ANALOG_PORT_ARM_POTENTIOMETER = 0;

  // DIO IDs
  public static final int DIO_PORT_CARGO_LIMIT_LEFT   = 8;
  public static final int DIO_PORT_CARGO_LIMIT_RIGHT  = 9;
  public static final int DIO_PORT_HATCH_LEFT         = 2;
  public static final int DIO_PORT_HATCH_RIGHT        = 3;

  //PCM IDs
  public static final int PCM_PORT_EXTEND = 1;
  public static final int PCM_PORT_CONTRACT = 0;
  public static final int PCM_PORT_RELEASE = 6;
  public static final int PCM_PORT_LATCH = 7;

  // Drive System
  public static final int GYRO_AXIS_YAW = 0;
  public static final int GYRO_AXIS_PITCH = 1;
  public static final int GYRO_AXIS_ROLL = 2;
  public static final int GYRO_AXIS_TOTAL = 3;
  public static final int ENCODER_TICKS_PER_ROTATION = 256;
  public static final double WHEEL_CIRCUMFERENCE_INCHES = 6 * Math.PI;
  public static final double MOTOR_MINIMUM_POWER = 0.05;

  // Drive Controls
  public static final double DRIVER_SPEED_PROPORTION = 0.8;
  public static final double DRIVER_TURN_PROPORTION = 0.8;
  public static final double GUNNER_SPEED_PROPORTION = 0.5;
  public static final double GUNNER_TURN_PROPORTION = 0.5;
  public static final double MAX_TURN_RATE_DEGREES_PER_SECOND = 180;
  public static final double MAX_VELOCITY_INCHES_PER_SECOND = 10;

  //Drive PID Values
  public static final double PID_P_LEFT_DRIVE = 0.0;
  public static final double PID_I_LEFT_DRIVE = 0.0;
  public static final double PID_D_LEFT_DRIVE = 0.0;
  public static final double PID_F_LEFT_DRIVE = 0.0;
  public static final double PID_P_RIGHT_DRIVE = 0.0;
  public static final double PID_I_RIGHT_DRIVE = 0.0;
  public static final double PID_D_RIGHT_DRIVE = 0.0;
  public static final double PID_F_RIGHT_DRIVE = 0.0;
  public static final double PID_P_GYRO = 0.0;
  public static final double PID_I_GYRO = 0.0;
  public static final double PID_D_GYRO = 0.0;
  public static final double PID_P_ARM = 0.0;
  public static final double PID_I_ARM = 0.0;
  public static final double PID_D_ARM = 0.0;
  public static final double DRIVE_TRAIN_SAMPLE_PERIOD = 1 / 5;

  // Arm
  public static final int ARM_MAX_ANGLE = 270;
  public static final int ARM_MIN_ANGLE = 0;
  public static final int ARM_RANGE = ARM_MAX_ANGLE - ARM_MIN_ANGLE;
  public static final int ARM_POTENTIOMETER_MAX = 4096;
  public static final int ARM_POTENTIOMETER_MIN = 0;
  public static final int ARM_POTENTIOMETER_RANGE = ARM_POTENTIOMETER_MAX - ARM_POTENTIOMETER_MIN;
  public static final double ARM_ANGLE_FACTOR = ARM_POTENTIOMETER_RANGE / ARM_RANGE;
  public static final int ARM_UP = 1;
  public static final int ARM_DOWN = -1;
  public static final double ARM_HIGH_GOAL_ANGLE = 70.0;
  public static final double ARM_MID_GOAL_ANGLE = 40.0;
  public static final double ARM_LOW_GOAL_ANGLE = 10.0;
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

  // Vision
  public static final double VISION_DEFAULT_RETURN_VALUE = 0.0;

  //General Purpose
  public static final int MILLISECONDS_PER_SECOND = 1000;
  public static final double STOP_SPEED = 0.0;
}
