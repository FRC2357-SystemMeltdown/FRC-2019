package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;

public class AutoHatchLoadingStationCommand extends Command {
  public AutoHatchLoadingStationCommand() {
    setName("HATCH LOAD");
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
