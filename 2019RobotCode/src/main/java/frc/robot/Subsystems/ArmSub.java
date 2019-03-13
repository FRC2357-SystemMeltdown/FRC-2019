/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.RobotMap;
import frc.robot.Commands.ArmStopCommand;

/**
 * Add your docs here.
 */
public class ArmSub extends SubsystemBase {
  public static int TARGET_VALUE_STOP = -1;

  public enum Direction {
    STOP,
    DOWN,
    UP,
  }

  // TODO: Move Compressor to its own subsystem.
  public Compressor compressor = new Compressor(RobotMap.CAN_ID_PCM);
  public Solenoid downSolenoid = new Solenoid(
    RobotMap.CAN_ID_PCM,
    RobotMap.PCM_PORT_DOWN);
  public Solenoid upSolenoid = new Solenoid(
    RobotMap.CAN_ID_PCM,
    RobotMap.PCM_PORT_UP);

  public AnalogInput potentiometer = new AnalogInput(RobotMap.ANALOG_PORT_ARM_POTENTIOMETER);

  private Direction manualDirection = Direction.STOP;
  private int targetValue = TARGET_VALUE_STOP;

  @Override
  public void setFailsafeActive(boolean failsafeActive) {
    super.setFailsafeActive(failsafeActive);
    manualDirection = Direction.STOP;
    targetValue = TARGET_VALUE_STOP;
  }

  /**
   * Gets the current value of the arm position.
   * @return int The potentiometer reading currently
   */
  public int getValue() {
    return potentiometer.getValue();
  }

  /**
   * Gets the current target value for the arm.
   * @return The target potentiometer value.
   */
  public int getTargetValue() {
    return targetValue;
  }

  /**
   * Sets the current target value for the arm.
   * @param targetValueAdjust the new potentiometer value for the arm.
   */
  public void setTargetValue(int targetValue) {
    if (isFailsafeActive() && targetValue != TARGET_VALUE_STOP) {
      System.err.println("ArmSub: Can't set target value while in failsafe");
    }
    this.targetValue = targetValue;
  }

  public boolean isInRange(int value) {
    return Math.abs(getTargetValueError(value)) < RobotMap.ARM_TARGET_TOLERANCE;
  }

  private int getTargetValueError(int value) {
    return targetValue - value;
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ArmStopCommand());
  }

  /**
   * Failsafe only: move arm manually in given direction.
   * @param direction The direction to move the arm.
   */
  public void moveArmManual(Direction direction) {
    if (!isFailsafeActive()) {
      System.err.println("moveArmManual called when not in failsafe");
      return;
    }
    if (manualDirection != direction) {
      manualDirection = direction;
    }
  }

  private Direction getCurrentDirection() {
    if (isFailsafeActive()) {
      return manualDirection;
    }
    if (targetValue == TARGET_VALUE_STOP) {
      return Direction.STOP;
    }

    int value = getValue();
    int error = getTargetValueError(value);

    if (isInRange(value)) {
      return Direction.STOP;
    }

    if (error > 0) {
      // DOWN
      if (error < RobotMap.ARM_DOWN_OVERSHOOT) {
        return Direction.STOP;
      }
      return Direction.DOWN;
    } else if (error < 0) {
      // UP
      if (-error < RobotMap.ARM_UP_OVERSHOOT) {
        return Direction.STOP;
      }
      return Direction.UP;
    }
    return Direction.STOP;
  }

  @Override
  public void periodic() {
    switch(getCurrentDirection()) {
      case DOWN:
        down();
        break;
      case UP:
        up();
        break;
      default:
        stop();
        break;
    }
  }

  private void down() {
    downSolenoid.set(true);
    upSolenoid.set(false);
  }

  private void up() {
    downSolenoid.set(false);
    upSolenoid.set(true);
  }

  private void stop() {
    downSolenoid.set(false);
    upSolenoid.set(false);
  }
}
