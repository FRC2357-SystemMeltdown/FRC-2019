package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.RobotMap.ArmPreset;

public class AutoHatchLoadingStationCommand extends CommandGroup {
  public AutoHatchLoadingStationCommand() {
    setName("HATCH LOAD");

    // TODO: Add code for a real sequence here instead of just test commands.
    addSequential(new AutoMoveForwardCommand(24));
    addSequential(new AutoArmCommand(ArmPreset.Low, 0));
    addSequential(new AutoRotateCommand(45));
    addSequential(new DriveStopCommand());
  }

  @Override
  protected void initialize() {
    super.initialize();
    System.out.println("AUTO: Hatch Loading Station - init");
  }

  @Override
  protected void interrupted() {
    super.interrupted();
    System.out.println("AUTO: Hatch Loading Station - interrupted");
  }

  @Override
  protected boolean isFinished() {
    return false;
  }
}
