package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoHatchLoadingStationCommand extends CommandGroup {
  public AutoHatchLoadingStationCommand() {
    setName("HATCH LOAD");

    addSequential(new AutoMoveForwardCommand(100));
    addSequential(new DriveStopCommand());
  }

  @Override
  protected void initialize() {
    super.initialize();
    System.out.println("AUTO: Hatch Loading Station - init");
  }

  @Override
  protected void end() {
    super.end();
    System.out.println("AUTO: Hatch Loading Station - end");
  }

  @Override
  protected boolean isFinished() {
    return false;
  }
}
