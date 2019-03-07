package frc.robot.overlays;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.RobotMap;
import frc.robot.Commands.HatchDirectMoveCommand;
import frc.robot.Commands.HatchDirectOpenCloseCommand;
import frc.robot.Other.XboxRaw;

public class GunnerHatchLoading extends GunnerLoading implements HatchControl {

  private HatchDirectOpenCloseCommand hatchOpenCloseCommand;
  private HatchDirectMoveCommand hatchMoveCommand;
  private JoystickButton hatchMoveButton;

  public GunnerHatchLoading(XboxController controller) {
    super(controller, RobotMap.ARM_HATCH_LOW_GOAL_ANGLE);

    hatchOpenCloseCommand = new HatchDirectOpenCloseCommand(this);
    hatchMoveCommand = new HatchDirectMoveCommand(this);

    hatchMoveButton = new JoystickButton(controller, XboxRaw.X.value);
    hatchMoveButton.whenPressed(hatchMoveCommand);
    hatchMoveButton.whenReleased(hatchOpenCloseCommand);

    hatchOpenCloseCommand.start();
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
