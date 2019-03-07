package frc.robot.overlays;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.Commands.ChangeArmStateCommand;
import frc.robot.Commands.MoveArmDirectCommand;
import frc.robot.Other.XboxRaw;
import frc.robot.Subsystems.ArmSub;

public class GunnerLoading extends GunnerCreepDrive {

  private MoveArmDirectCommand armUpCommand;
  private MoveArmDirectCommand armDownCommand;
  private ChangeArmStateCommand armSetCommand;

  private JoystickButton armUpButton;
  private JoystickButton armDownButton;

  public GunnerLoading(XboxController controller, double loadAngle) {
    super(controller);

    armUpCommand = new MoveArmDirectCommand(ArmSub.Direction.UP);
    armDownCommand = new MoveArmDirectCommand(ArmSub.Direction.DOWN);
    armSetCommand = new ChangeArmStateCommand(loadAngle);

    armUpButton = new JoystickButton(controller, XboxRaw.BumperRight.value);
    armUpButton.whileHeld(armUpCommand);

    armDownButton = new JoystickButton(controller, XboxRaw.BumperLeft.value);
    armDownButton.whileHeld(armDownCommand);

    armSetCommand.start();
  }
}