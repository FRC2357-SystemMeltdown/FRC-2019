/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.Commands.ArmStateCommand;

/**
 * Add your docs here.
 */
public class ArmSub extends Subsystem {

  private AnalogInput potentiometer = new AnalogInput(RobotMap.ANALOG_PORT_ARM_POTENTIOMETER);
  private DoubleSolenoid extendingSolenoid = new DoubleSolenoid(
    RobotMap.PCM_PORT_EXTENDING_SOLENOID_OPEN,
    RobotMap.PCM_PORT_EXTENDING_SOLENOID_CLOSE);
  private DoubleSolenoid loweringSolenoid = new DoubleSolenoid(
    RobotMap.PCM_PORT_LOWERING_SOLENOID_OPEN,
    RobotMap.PCM_PORT_LOWERING_SOLENOID_CLOSE);
  private Servo lock = new Servo(RobotMap.PWM_ID_ARM_LOCK_SERVO);
  public double targetAngle;

  public ArmSub() {
    targetAngle = 0.0;
  }

  @Override
  public void initDefaultCommand() {
    // Default command sets the arm speed to zero
    setDefaultCommand(new ArmStateCommand());
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
      extendingSolenoid.set(Value.kForward);
      loweringSolenoid.set(Value.kReverse);
    } else if (direction == -1) {
      extendingSolenoid.set(Value.kReverse);
      loweringSolenoid.set(Value.kForward);
    } else {
      extendingSolenoid.set(Value.kReverse);
      loweringSolenoid.set(Value.kReverse);
    }
  }

  /**
   * Engages the lock servo
   */
  public void lockArm(){
    if(lock.get() == RobotMap.SERVO_LOCK_POSITION) {
      lock.set(RobotMap.SERVO_RETRACTED_POSITION);
    } else {
      lock.set(RobotMap.SERVO_LOCK_POSITION);
    }
  }

}
