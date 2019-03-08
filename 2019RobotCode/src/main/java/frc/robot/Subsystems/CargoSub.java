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
import frc.robot.Commands.CargoRollerStopCommand;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

/**
 * This subsystem manages the cargo intake motor.
 */
public class CargoSub extends Subsystem {

  public WPI_VictorSPX roller = new WPI_VictorSPX(RobotMap.CAN_ID_CARGO_INTAKE);
  public DigitalInput limitLeft = new DigitalInput(RobotMap.DIO_PORT_CARGO_LIMIT_LEFT);
  public DigitalInput limitRight = new DigitalInput(RobotMap.DIO_PORT_CARGO_LIMIT_RIGHT);

  public CargoSub() {
    roller.setNeutralMode(NeutralMode.Brake);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new CargoRollerStopCommand());
  }

  public void cargoRollerDirect(double speed) {
    roller.set(-speed);
  }

  public void cargoRollerStop() {
    roller.set(RobotMap.STOP_SPEED);
  }
}
