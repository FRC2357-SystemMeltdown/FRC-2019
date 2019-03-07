package frc.robot.overlays;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.Commands.ChangeArmStateCommand;
import frc.robot.Commands.MoveArmDirectCommand;
import frc.robot.Commands.NullCommand;
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
    armLowButton = new JoystickButton(controller, XboxRaw.A.value);
    armMidButton = new JoystickButton(controller, XboxRaw.B.value);
    armHighButton = new JoystickButton(controller, XboxRaw.Y.value);
    armUpButton = new JoystickButton(controller, XboxRaw.BumperRight.value);
    armDownButton = new JoystickButton(controller, XboxRaw.BumperLeft.value);
  }

  @Override
  public void activate() {
    armLowButton.whenPressed(armLowCommand);
    armMidButton.whenPressed(armMidCommand);
    armHighButton.whenPressed(armHighCommand);
    armUpButton.whileHeld(armUpCommand);
    armDownButton.whileHeld(armDownCommand);
  }

  @Override
  public void deactivate() {
    NullCommand nullCommand = new NullCommand();

    armLowButton.whenPressed(nullCommand);
    armMidButton.whenPressed(nullCommand);
    armHighButton.whenPressed(nullCommand);
    armUpButton.whileHeld(nullCommand);
    armDownButton.whileHeld(nullCommand);
  }
}
