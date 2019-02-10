/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.Commands.MoveArmDirectCommand;
import edu.wpi.first.wpilibj.PWM;

/**
 * Add your docs here.
 */
public class ArmSub extends Subsystem {

  private AnalogInput potentiometer = new AnalogInput(RobotMap.ANALOG_PORT_ARM_POTENTIOMETER);
  private PWM leftSpark = new PWM(RobotMap.PWM_PORT_LEFT_ARM);
  private PWM rightSpark = new PWM(RobotMap.PWM_PORT_RIGHT_ARM);

  @Override
  public void initDefaultCommand() {
    // Default command sets the arm speed to zero
    setDefaultCommand(new MoveArmDirectCommand(0));
  }

  public double getPotentiometerAngle() {
    double factor = RobotMap.ARM_MAX_ANGLE / RobotMap.ARM_POTENTIOMETER_MAX;
    double angle = potentiometer.getValue() * factor;
    return angle;
  }

  // Set a speed for the arm. Positive moves up
  // @param input in [-1, 1]
  public void moveArmManual(double input) {
    int leftRawPWMValue = (int)Math.ceil((input + 1) * 255 / 2);
    int rightRawPWMValue = -leftRawPWMValue;
    leftSpark.setRaw(leftRawPWMValue);
    rightSpark.setRaw(rightRawPWMValue);
  }

}
