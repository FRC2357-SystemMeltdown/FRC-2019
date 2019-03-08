package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.overlays.CargoControl;

public class CargoRollerScoreCommand extends Command {
  private CargoControl controller;

  public CargoRollerScoreCommand(CargoControl controller) {
    requires(Robot.CARGO_SUB);
    this.controller = controller;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double speed = controller.getCargoRollerSpeed();

    if (Robot.CARGO_SUB.isLimit() && speed < 0) {
      speed = 0;
    }

    Robot.CARGO_SUB.cargoRollerDirect(speed);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.CARGO_SUB.cargoRollerStop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
