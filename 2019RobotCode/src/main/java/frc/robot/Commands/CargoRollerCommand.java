/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.controls.CargoControl;

// TODO: Move switch logic into subsystem so this command can be simpler and not loop execute.
public class CargoRollerCommand extends Command {
  private CargoControl controller;
  private boolean triggered;
  private boolean reset;

  public CargoRollerCommand(CargoControl controller) {
    requires(Robot.CARGO_SUB);
    this.controller = controller;
  }

  public boolean isFailsafe() {
    return Robot.getInstance().isFailsafeActive();
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    this.reset = false;
    triggered = Robot.CARGO_SUB.isLimit();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (isFailsafe()) {
      executeFailsafe();
    } else {
      executeWithLimits();
    }
  }

  private void executeFailsafe() {
    double speed = controller.getCargoRollerSpeed();
    Robot.CARGO_SUB.cargoRollerDirect(speed);
  }

  private void executeWithLimits() {
    double speed = controller.getCargoRollerSpeed();

    if (Robot.CARGO_SUB.isLimit() && !triggered) {
      reset = false;
      triggered = true;
    }
    
    if (speed == 0.0) {
      reset = true;
    }

    if(reset && !Robot.CARGO_SUB.isLimit() && triggered) {
      triggered = false;
    }

    if (!reset) {
      speed = 0.0;
    }

    Robot.CARGO_SUB.cargoRollerDirect(speed);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
