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
  public static final int CAN_ID_PDP               = 0;
  public static final int CAN_ID_RIGHT_DRIVE       = 1;
  public static final int CAN_ID_RIGHT_DRIVE_SLAVE = 2;
  public static final int CAN_ID_LEFT_DRIVE_SLAVE  = 3;
  public static final int CAN_ID_LEFT_DRIVE        = 4;
  public static final int CAN_ID_PCM               = 5;
  public static final int CAN_ID_PIGEON_IMU        = 6;
  public static final int CAN_ID_CARGO_INTAKE      = 7;

  // PWM IDs
  public static final int PWM_PORT_RIGHT_ARM = 0;
  public static final int PWM_PORT_LEFT_ARM  = 1;

  // Analog In IDs
  public static final int ANALOG_PORT_ARM_POTENTIOMETER = 0;

  // DIO IDs
  public static final int DIO_PORT_CARGO_LIMIT_SWITCH = 0;

  // Drive
  public static final int GYRO_AXIS_YAW = 0;
  public static final int GYRO_AXIS_PITCH = 1;
  public static final int GYRO_AXIS_ROLL = 2;

  // Arm
  public static final int ARM_MAX_ANGLE = 270;
  public static final int ARM_MIN_ANGLE = 0;
  public static final int ARM_RANGE = ARM_MAX_ANGLE - ARM_MIN_ANGLE;
  public static final int ARM_POTENTIOMETER_MAX = 4096;
  public static final int ARM_POTENTIOMETER_MIN = 0;
  public static final int ARM_POTENTIOMETER_RANGE = ARM_POTENTIOMETER_MAX - ARM_POTENTIOMETER_MIN;

  // Cargo
  public static final double INTAKE_IN_OUT_SPEED = 0.7;
  public static final double INTAKE_STOP_SPEED = 0.0;

  // Hatch

  // Vision
}
