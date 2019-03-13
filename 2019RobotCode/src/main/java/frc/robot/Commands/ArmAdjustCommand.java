package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class ArmAdjustCommand extends Command {
  private int value;

  public ArmAdjustCommand(int adjustValue) {
    requires(Robot.ARM_SUB);

    if (Math.abs(adjustValue) > RobotMap.ARM_ADJUST_MAX) {
      System.err.println("ArmAdjustCommand: value exceeds max: " + adjustValue);
      adjustValue = 0;
    }
    this.value = Robot.ARM_SUB.getValue() + adjustValue;
  }

  @Override
  protected void execute() {
    Robot.ARM_SUB.setTargetValue(value);
  }

  @Override
  protected boolean isFinished() {
    return Robot.ARM_SUB.isInRange(value);
  }
}
