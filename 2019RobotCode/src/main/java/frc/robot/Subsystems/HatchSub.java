/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.Commands.HatchStopCommand;
import frc.robot.Other.Utility;

/**
 * Hatch Gantry Subsystem
 */
public class HatchSub extends Subsystem {
  public DigitalInput leftLimitSwitch;
  public DigitalInput rightLimitSwitch;

  private WPI_TalonSRX leftGantry = new WPI_TalonSRX(RobotMap.CAN_ID_LEFT_HATCH_GANTRY);
  private WPI_TalonSRX rightGantry = new WPI_TalonSRX(RobotMap.CAN_ID_RIGHT_HATCH_GANTRY);

  public HatchSub() {
    leftLimitSwitch = new DigitalInput(RobotMap.DIO_PORT_HATCH_LEFT);
    rightLimitSwitch = new DigitalInput(RobotMap.DIO_PORT_HATCH_RIGHT);

    leftGantry.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
    Utility.configTalonPID(leftGantry, RobotMap.HATCH_LEFT_PID);

    rightGantry.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
    Utility.configTalonPID(rightGantry, RobotMap.HATCH_RIGHT_PID);

    // let the gantries go past the center limit switches
    leftGantry.configForwardLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.Disabled);
    rightGantry.configForwardLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.Disabled);

    // configure motion magic values
    double ticksPerInch = RobotMap.HATCH_ENCODER_TICKS_PER_ROTATION / RobotMap.HATCH_INCHES_PER_ROTATION;
    double accelerationTicksPer100msPerSec = RobotMap.HATCH_ACCELERATION * ticksPerInch / (RobotMap.MILLISECONDS_PER_SECOND / 100.0);
    double velocityTicksPer100ms = RobotMap.HATCH_MAX_VELOCITY * ticksPerInch / (RobotMap.MILLISECONDS_PER_SECOND / 100.0);
    leftGantry.configMotionCruiseVelocity((int)velocityTicksPer100ms);
    leftGantry.configMotionAcceleration((int)accelerationTicksPer100msPerSec);
    rightGantry.configMotionCruiseVelocity((int)velocityTicksPer100ms);
    rightGantry.configMotionAcceleration((int)accelerationTicksPer100msPerSec);
  }

  public boolean isLeftLimitClosed() {
    return leftGantry.getSensorCollection().isFwdLimitSwitchClosed();
  }

  public boolean isRightLimitClosed() {
    return rightGantry.getSensorCollection().isFwdLimitSwitchClosed();
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new HatchStopCommand());
  }

  /**
   * Sets the motor speeds directly to move left or right.
   * @param speed The speed for the hatch gantries to move, where positive is left and negative is right.
   */
  public void directMoveGantry(double speed) {
    leftGantry.set(-speed);
    rightGantry.set(-speed);
  }

  /**
   * Sets the motor speeds directly to open or close.
   * @param speed The speed for the hatch gantries to open.
   */
  public void directOpenCloseGantry(double speed) {
    leftGantry.set(speed);
    rightGantry.set(-speed);
  }

  /**
   * Move the left gantry to a specific position
   * @param inchesLeftOfCenter
   */
  public void setLeftGantryPosition(double inchesLeftOfCenter) {
    setGantryPosition(leftGantry, -inchesLeftOfCenter);
  }

  /**
   * Move the right gantry to a specific position
   * @param inchesRightOfCenter
   */
  public void setRightGantryPosition(double inchesRightOfCenter) {
    setGantryPosition(rightGantry, inchesRightOfCenter);
  }

  private void setGantryPosition(WPI_TalonSRX talon, double inchesFromCenter) {
    double ticksPerInch = RobotMap.HATCH_ENCODER_TICKS_PER_ROTATION / RobotMap.HATCH_INCHES_PER_ROTATION;
    talon.set(ControlMode.MotionMagic, inchesFromCenter * ticksPerInch);
  }

  public void resetLeftEncoder() {
    leftGantry.setSelectedSensorPosition(0);
  }

  public void resetRightEncoder() {
    rightGantry.setSelectedSensorPosition(0);
  }
}
