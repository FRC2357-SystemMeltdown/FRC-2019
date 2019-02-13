/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

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
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  /**
   * 
   * @param speed The speed for the hatch gantries to move, where positive is left and negative is right.
   */
  public void failsafeMoveGantry(double speed) {
    leftGantry.set(speed);
    rightGantry.set(speed);
  }

  /**
   * 
   * @param speed The speed for the hatch gantries to open.
   */
  public void failsafeOpenCloseGantry(double speed) {
    leftGantry.set(speed);
    rightGantry.set(-speed);
  }
}
