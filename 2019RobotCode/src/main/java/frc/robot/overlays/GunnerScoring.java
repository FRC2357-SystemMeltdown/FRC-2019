package frc.robot.overlays;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.Commands.ChangeArmStateCommand;
import frc.robot.Commands.MoveArmDirectCommand;
import frc.robot.Other.XboxRaw;
import frc.robot.Subsystems.ArmSub;

public class GunnerScoring extends GunnerCreepDrive {

  private ChangeArmStateCommand armHighCommand;
  private ChangeArmStateCommand armMidCommand;
  private ChangeArmStateCommand armLowCommand;

  private MoveArmDirectCommand armUpCommand;
  private MoveArmDirectCommand armDownCommand;

  private JoystickButton armLowButton;
  private JoystickButton armMidButton;
  private JoystickButton armHighButton;
  private JoystickButton armUpButton;
  private JoystickButton armDownButton;

  public GunnerScoring(XboxController controller, double armLow, double armMid, double armHigh) {
    super(controller);

    armLowCommand = new ChangeArmStateCommand(armLow);
    armMidCommand = new ChangeArmStateCommand(armMid);
    armHighCommand = new ChangeArmStateCommand(armHigh);

    armUpCommand = new MoveArmDirectCommand(ArmSub.Direction.UP);
    armDownCommand = new MoveArmDirectCommand(ArmSub.Direction.DOWN);

    armLowButton = new JoystickButton(controller, XboxRaw.A.value);
    armLowButton.whenPressed(armLowCommand);

    armMidButton = new JoystickButton(controller, XboxRaw.X.value);
    armMidButton.whenPressed(armMidCommand);

    armHighButton = new JoystickButton(controller, XboxRaw.Y.value);
    armHighButton.whenPressed(armHighCommand);

    armUpButton = new JoystickButton(controller, XboxRaw.BumperRight.value);
    armUpButton.whileHeld(armUpCommand);

    armDownButton = new JoystickButton(controller, XboxRaw.BumperLeft.value);
    armDownButton.whileHeld(armDownCommand);
  }
}
