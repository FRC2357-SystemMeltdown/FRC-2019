/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Subsystems;

import java.util.Timer;
import java.util.TimerTask;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.RobotMap;
import frc.robot.RobotMap.ArmPreset;

/**
 * Add your docs here.
 */
public class ArmSub extends SubsystemBase {
  private static long DEFENSE_SOLENOID_OFF_DELAY = 1000;
  private static int TARGET_VALUE_STOP = -1;

  public enum Direction {
    STOP,
    DOWN,
    UP,
  }

  private class DefenseSolenoidOffTask extends TimerTask {
    @Override
    public void run() {
      defenseSolenoid.set(Value.kOff);
    }
  }

  // TODO: Move Compressor to its own subsystem.
  public Compressor compressor = new Compressor(RobotMap.CAN_ID_PCM);
  public Solenoid downSolenoid = new Solenoid(
    RobotMap.CAN_ID_PCM,
    RobotMap.PCM_PORT_DOWN);
  public Solenoid upSolenoid = new Solenoid(
    RobotMap.CAN_ID_PCM,
    RobotMap.PCM_PORT_UP);
  public DoubleSolenoid defenseSolenoid = new DoubleSolenoid(
    RobotMap.CAN_ID_PCM,
    RobotMap.PCM_PORT_DEFENSE_MODE_ON,
    RobotMap.PCM_PORT_DEFENSE_MODE_OFF
  );

  public AnalogInput potentiometer = new AnalogInput(RobotMap.ANALOG_PORT_ARM_POTENTIOMETER);

  private Direction lastDirection = Direction.STOP;
  private Direction manualDirection = Direction.STOP;
  private int targetValue = TARGET_VALUE_STOP;
  private ArmPreset lastPreset = ArmPreset.Start;
  private Timer timer = new Timer();

  @Override
  protected void initDefaultCommand() {
    setFailsafeActive(isFailsafeActive());
    deactivateDefense();
  }

  @Override
  public void setFailsafeActive(boolean failsafeActive) {
    super.setFailsafeActive(failsafeActive);

    if (failsafeActive) {
      setLastPreset(ArmPreset.Failsafe);
    } else {
      setLastPreset(ArmPreset.Start);
      setTargetValue(ArmPreset.Start.value);
    }
  }

  /**
   * Gets the last preset set for the Arm
   * @return ArmPreset The last preset used
   */
  public ArmPreset getLastPreset() {
    return lastPreset;
  }

  /**
   * Set the last arm preset.
   * TODO: Move more preset code into the ArmSub so we set the
   * actual preset instead of setting the last preset.
   * @param armPreset The preset
   */
  public void setLastPreset(ArmPreset armPreset) {
    this.lastPreset = armPreset;
  }

  /**
   * Gets the current value of the arm position.
   * @return int The potentiometer reading currently
   */
  public int getValue() {
    return potentiometer.getValue();
  }

  /**
   * Checks if a target value is in range of the current value.
   * @param targetValue The target value to check against the current potentiometer value.
   */
  public boolean isInRange(int targetValue) {
    return Math.abs(getValue() - targetValue) < RobotMap.ARM_TARGET_TOLERANCE;
  }

  /**
   * Gets the current target value for the arm.
   * @return The target potentiometer value.
   */
  public int getTargetValue() {
    if (targetValue == TARGET_VALUE_STOP) {
      return getValue();
    }
    return targetValue;
  }

  /**
   * Sets the current target value for the arm.
   * @param targetValueAdjust the new potentiometer value for the arm.
   */
  public void setTargetValue(int targetValue) {
    if (isFailsafeActive() && targetValue != TARGET_VALUE_STOP) {
      System.err.println("ArmSub: Can't setTargetValue while in failsafe");
      return;
    }
    this.targetValue = targetValue;
  }

  /**
   * Failsafe only: move arm manually in given direction.
   * @param direction The direction to move the arm.
   */
  public void moveArmManual(Direction direction) {
    if (!isFailsafeActive()) {
      System.err.println("ArmSub: Can't moveArmManual when not in failsafe");
      return;
    }
    if (manualDirection != direction) {
      manualDirection = direction;
    }
  }

  public Direction calculateCurrentDirection() {
    if (isFailsafeActive()) {
      return manualDirection;
    }
    if (targetValue == TARGET_VALUE_STOP) {
      return Direction.STOP;
    }

    int value = getValue();
    ArmPreset nearestPreset = ArmPreset.getNearestPreset(targetValue);

    if (isInRange(targetValue)) {
      return Direction.STOP;
    }

    if (targetValue > value) {
      // DOWN
      if (targetValue - value < nearestPreset.downOvershoot) {
        return Direction.STOP;
      }
      return Direction.DOWN;
    } else if (targetValue < value) {
      // UP
      if (value - targetValue < nearestPreset.upOvershoot) {
        return Direction.STOP;
      }
      return Direction.UP;
    }
    return Direction.STOP;
  }

  @Override
  public void periodic() {
    Direction currentDirection = calculateCurrentDirection();

    if (currentDirection != lastDirection) {
      switch(currentDirection) {
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
  }

  public void activateDefense() {
    setLastPreset(ArmPreset.Defense);
    upSolenoid.set(true);
    downSolenoid.set(false);
    defenseSolenoid.set(Value.kForward);

    timer.schedule(new DefenseSolenoidOffTask(), DEFENSE_SOLENOID_OFF_DELAY );
  }

  public void deactivateDefense() {
    setLastPreset(ArmPreset.Start);
    defenseSolenoid.set(Value.kReverse);
    upSolenoid.set(false);
    downSolenoid.set(false);

    timer.schedule(new DefenseSolenoidOffTask(), DEFENSE_SOLENOID_OFF_DELAY );
  }

  /**
   * Stops the arm, regardless of mode.
   */
  public void stop() {
    if (lastDirection != Direction.STOP || downSolenoid.get() || upSolenoid.get()) {
      this.targetValue = TARGET_VALUE_STOP;
      this.manualDirection = Direction.STOP;
      this.lastDirection = Direction.STOP;
      downSolenoid.set(false);
      upSolenoid.set(false);
    }
  }

  private void down() {
    this.lastDirection = Direction.DOWN;
    downSolenoid.set(true);
    upSolenoid.set(false);
  }

  private void up() {
    this.lastDirection = Direction.UP;
    downSolenoid.set(false);
    upSolenoid.set(true);
  }
}
