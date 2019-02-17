/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.Commands.ArmStateCommand;
import frc.robot.Commands.MoveArmDirectCommand;

/**
 * Add your docs here.
 */
public class ArmSub extends Subsystem {

  public Compressor compressor = new Compressor(RobotMap.CAN_ID_PCM);
  public DoubleSolenoid movementSolenoid = new DoubleSolenoid(
    RobotMap.CAN_ID_PCM,
    RobotMap.PCM_PORT_EXTEND,
    RobotMap.PCM_PORT_CONTRACT);
  public DoubleSolenoid latchingSolenoid = new DoubleSolenoid(
    RobotMap.CAN_ID_PCM,
    RobotMap.PCM_PORT_RELEASE,
    RobotMap.PCM_PORT_LATCH);
  private AnalogInput potentiometer = new AnalogInput(RobotMap.ANALOG_PORT_ARM_POTENTIOMETER);
  private Servo lock = new Servo(RobotMap.PWM_ID_ARM_LOCK_SERVO);
  public double targetAngle;

  public ArmSub() {
    targetAngle = 0.0;
  }

  @Override
  public void initDefaultCommand() {
    // Default command sets the arm speed to zero
    setDefaultCommand(new MoveArmDirectCommand(0));
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
   *
   * @param direction If 1, arm will raise. If -1, arm will lower. If 0, arm will hold.
   */
  public void moveArmManual(int direction) {
    if(direction == 1) {
      latchingSolenoid.set(DoubleSolenoid.Value.kForward);
      movementSolenoid.set(DoubleSolenoid.Value.kForward);
    } else if (direction == -1) {
      latchingSolenoid.set(DoubleSolenoid.Value.kForward);
      movementSolenoid.set(DoubleSolenoid.Value.kReverse);
    } else {
      movementSolenoid.set(DoubleSolenoid.Value.kForward);
      latchingSolenoid.set(DoubleSolenoid.Value.kReverse);
    }
  }

  /**
   * Engages the lock servo
   */
  public void lockArm(){
    lock.set(RobotMap.SERVO_LOCK_POSITION);
  }

  /**
   * Disengages the lock servo
   */
  public void unlockArm(){
    lock.set(RobotMap.SERVO_RETRACTED_POSITION);
  }
}
