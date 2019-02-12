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
import frc.robot.Commands.IntakeStopCommand;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

/**
 * This subsystem manages the cargo intake motor.
 */
public class CargoSub extends Subsystem {

  private WPI_VictorSPX intake = new WPI_VictorSPX(RobotMap.CAN_ID_CARGO_INTAKE);
  private DigitalInput limitSwitch = new DigitalInput(RobotMap.CAN_ID_CARGO_INTAKE);

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new IntakeStopCommand());
  }

  public void intakeIn() {
    intake.set(RobotMap.INTAKE_IN_SPEED);
  }

  public void intakeOut() {
    intake.set(RobotMap.INTAKE_OUT_SPEED);
  }

  public void intakeStop() {
    intake.set(RobotMap.STOP_SPEED);
  }
}
