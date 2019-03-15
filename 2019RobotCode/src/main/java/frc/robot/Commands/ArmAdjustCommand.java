package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.Subsystems.ArmSub.Direction;

public class ArmAdjustCommand extends Command {
  private Direction direction;
  private int targetValue;

  public ArmAdjustCommand(Direction direction) {
    requires(Robot.ARM_SUB);
    this.direction = direction;
  }

  public boolean isFailsafe() {
    return Robot.getInstance().isFailsafeActive();
  }

  public int getAdjustValue() {
    switch(direction) {
      case UP:
        return RobotMap.ARM_ADJUST_UP;
      case DOWN:
        return RobotMap.ARM_ADJUST_DOWN;
      default:
        return 0;
    }
  }

  @Override
  protected void initialize() {
    super.initialize();

    if (isFailsafe()) {
      Robot.ARM_SUB.moveArmManual(direction);
    } else {
      this.targetValue = Robot.ARM_SUB.getTargetValue() + getAdjustValue();
      Robot.ARM_SUB.setTargetValue(this.targetValue);
    }
  }

  @Override
  protected boolean isFinished() {
    return ! isFailsafe();
  }

  @Override
  protected void end() {
    if (isFailsafe()) {
      Robot.ARM_SUB.stop();
    }
  }
}
