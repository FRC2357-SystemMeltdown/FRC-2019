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
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.Commands.ArmPositionCommand;
import frc.robot.Commands.MoveArmDirectCommand;

/**
 * Add your docs here.
 */
public class ArmSub extends Subsystem {
  public enum Direction {
    STOP,
    DOWN,
    UP,
  }

  public enum Position {
    HIGH(RobotMap.ARM_HIGH_GOAL_ANGLE),
    MID(RobotMap.ARM_MID_GOAL_ANGLE),
    LOW(RobotMap.ARM_LOW_GOAL_ANGLE),
    CARGO(RobotMap.ARM_CARGO_PICKUP_ANGLE),
    MIN(RobotMap.ARM_STARTING_ANGLE);

    public int angle;

    private Position(int angle) {
    this.angle = angle;
    }
  }

  public Compressor compressor = new Compressor(RobotMap.CAN_ID_PCM);
  public Solenoid downSolenoid = new Solenoid(
    RobotMap.CAN_ID_PCM,
    RobotMap.PCM_PORT_DOWN);
  public Solenoid upSolenoid = new Solenoid(
    RobotMap.CAN_ID_PCM,
    RobotMap.PCM_PORT_UP);

  private AnalogInput potentiometer = new AnalogInput(RobotMap.ANALOG_PORT_ARM_POTENTIOMETER);
  private Direction currentDirection = null;
  public Position currentPosition = Position.MIN;


  @Override
  public void initDefaultCommand() {
    // Default command sets the arm speed to zero
    setDefaultCommand(new ArmPositionCommand());
  }

  /**
   * Gets the raw potentiometer value
   * @return A value between 0 and 4096 (12-bit adc)
   */
  public int getPotentiometerValue() {
    return potentiometer.getValue();
  }

  /**
   *
   * @return The angle of the arm in degrees relative to its lowest possible state.
   */
  public double getPotentiometerAngle() {
    double angle = potentiometer.getValue() * RobotMap.ARM_ANGLE_FACTOR;
    double angleFromBase = angle - RobotMap.ARM_MIN_ANGLE;
    return angleFromBase;
  }

  /**
   * Moves the arm in the direction desired, or stops it.
   *
   * @param direction The direction to move the arm.
   */
  public void moveArmManual(Direction direction) {
    if (currentDirection != direction) {
      currentDirection = direction;

      switch(direction) {
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

  public void setPosition(Position targetPosition) {
    if(targetPosition != currentPosition) {
      currentPosition = targetPosition;
    }
  }
}
