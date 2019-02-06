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
    //CAN IDs
    public static final int PDP_CAN_ID = 0;
    public static final int RIGHT_DRIVE_CAN_ID = 1;
    public static final int RIGHT_DRIVE_SLAVE_CAN_ID = 2;
    public static final int LEFT_DRIVE_SLAVE_CAN_ID = 3;
    public static final int LEFT_DRIVE_CAN_ID = 4;
    public static final int PCM_CAN_ID = 5;
    public static final int PIGEON_IMU_CAN_ID = 6;
    public static final int CARGO_INTAKE_CAN_ID = 7;

    //PWM IDs
    public static final int RIGHT_ARM_PWM_PORT = 0;
    public static final int LEFT_ARM_PWM_PORT = 1;

    //Analog In IDs
    public static final int ARM_POTENTIOMETER_ANALOG_PORT = 0;

    //DIO IDs
    public static final int CARGO_LIMIT_SWITCH_DIO_PORT = 0;

    //Drive
    public static final int YAWPITCHROLL_YAW = 0;
    public static final int YAWPITCHROLL_PITCH = 1;
    public static final int YAWPITCHROLL_ROLL = 2;

    //Arm
    public static final int ARM_MAX_ANGLE = 270;
    public static final int ARM_MIN_ANGLE = 0;
    public static final int ARM_RANGE = ARM_MAX_ANGLE - ARM_MIN_ANGLE;
    public static final int ARM_POTENTIOMETER_MAX = 4096;
    public static final int ARM_POTENTIOMETER_MIN = 0;
    public static final int ARM_POTENTIOMETER_RANGE = ARM_POTENTIOMETER_MAX - ARM_POTENTIOMETER_MIN;

    //Cargo
    public static final double INTAKE_IN_OUT_SPEED = 0.7;
    public static final double INTAKE_STOP_SPEED = 0.0;

    //Hatch

    //Vision

}
