package frc.robot.overlays;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.RobotMap;
import frc.robot.Commands.HatchDirectMoveCommand;
import frc.robot.Commands.HatchDirectOpenCloseCommand;
import frc.robot.Commands.NullCommand;
import frc.robot.Other.XboxRaw;

/**
 * Defaults triggers to open/close,
 * move when X is held.
 */
public class GunnerHatchScoring extends GunnerScoring implements HatchControl {

  private HatchDirectOpenCloseCommand hatchOpenCloseCommand;
  private HatchDirectMoveCommand hatchMoveCommand;
  private JoystickButton hatchMoveButton;

  public GunnerHatchScoring(XboxController controller) {
    super(
      controller,
      RobotMap.ARM_HATCH_LOW_GOAL_ANGLE,
      RobotMap.ARM_HATCH_MID_GOAL_ANGLE,
      RobotMap.ARM_HATCH_HIGH_GOAL_ANGLE
    );

    hatchOpenCloseCommand = new HatchDirectOpenCloseCommand(this);
    hatchMoveCommand = new HatchDirectMoveCommand(this);
    hatchMoveButton = new JoystickButton(controller, XboxRaw.X.value);
  }

  @Override
  public void activate() {
    super.activate();
    hatchMoveButton.whenPressed(hatchMoveCommand);
    hatchMoveButton.whenReleased(hatchOpenCloseCommand);

    hatchOpenCloseCommand.start();
  }

  @Override
  public void deactivate() {
    super.deactivate();
    NullCommand nullCommand = new NullCommand();

    hatchMoveButton.whenPressed(nullCommand);
    hatchMoveButton.whenReleased(nullCommand);

    hatchOpenCloseCommand.cancel();
  }

  @Override
  public double getHatchMoveSpeed() {
    return controller.getTriggerAxis(Hand.kLeft) - controller.getTriggerAxis(Hand.kRight);
  }

  @Override
  public double getHatchOpenCloseSpeed() {
    return controller.getTriggerAxis(Hand.kLeft) - controller.getTriggerAxis(Hand.kRight);
  }
}
