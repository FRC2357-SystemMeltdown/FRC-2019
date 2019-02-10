/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

/**
 * Hatch Gantry Subsystem
 */
public class HatchSub extends Subsystem {
  public DigitalInput leftLimitSwitch;
  public DigitalInput rightLimitSwitch;

  public HatchSub() {
    leftLimitSwitch = new DigitalInput(RobotMap.DIO_PORT_HATCH_LEFT);
    rightLimitSwitch = new DigitalInput(RobotMap.DIO_PORT_HATCH_RIGHT);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
