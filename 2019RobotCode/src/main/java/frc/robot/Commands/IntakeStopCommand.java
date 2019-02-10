package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

// This command stops the cargo intake
public class IntakeStopCommand extends Command {
  public IntakeStopCommand()
  {
      requires(Robot.CARGO_SUB);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.CARGO_SUB.intakeStop();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
      return true;
  }  
}
