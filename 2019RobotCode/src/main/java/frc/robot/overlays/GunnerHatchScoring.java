package frc.robot.overlays;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.Commands.ChangeArmPositionCommand;
import frc.robot.Other.XboxRaw;
import frc.robot.Subsystems.ArmSub.Position;

public class GunnerHatchScoring extends GunnerScoring {

  private ChangeArmPositionCommand low = new ChangeArmPositionCommand(Position.LOW);
  private JoystickButton lowButton;

  private ChangeArmPositionCommand mid = new ChangeArmPositionCommand(Position.MID);
  private JoystickButton midButton;

  private ChangeArmPositionCommand high = new ChangeArmPositionCommand(Position.HIGH);
  private JoystickButton highButton;
  public GunnerHatchScoring(XboxController controller) {
    super(controller);

    lowButton = new JoystickButton(controller, XboxRaw.A.value);
    lowButton.whenPressed(low);

    midButton = new JoystickButton(controller, XboxRaw.X.value);
    midButton.whenPressed(mid);

    highButton = new JoystickButton(controller, XboxRaw.Y.value);
    highButton.whenPressed(high);
  }
}
