package frc.robot.overlays;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.Commands.ChangeArmPositionCommand;
import frc.robot.Other.XboxRaw;
import frc.robot.Subsystems.ArmSub.Position;

public class GunnerScoring extends GunnerCreepDrive {

  private ChangeArmPositionCommand changePositionHigh;
  private ChangeArmPositionCommand changePositionMid;
  private ChangeArmPositionCommand changePositionLow;
  private ChangeArmPositionCommand changePositionMin;

  private JoystickButton changePositionHighButton;
  private JoystickButton changePositionMidButton;
  private JoystickButton changePositionLowButton;
  private JoystickButton changePositionMinButton;

  public GunnerScoring(XboxController controller) {
    super(controller);

    changePositionHigh = new ChangeArmPositionCommand(Position.HIGH);
    changePositionMid = new ChangeArmPositionCommand(Position.MID);
    changePositionLow = new ChangeArmPositionCommand(Position.LOW);
    changePositionMin = new ChangeArmPositionCommand(Position.MIN);

    changePositionHighButton = new JoystickButton(controller, XboxRaw.Y.value);
    changePositionHighButton.whenPressed(changePositionHigh);

    changePositionMidButton = new JoystickButton(controller, XboxRaw.X.value);
    changePositionMidButton.whenPressed(changePositionMid);

    changePositionLowButton = new JoystickButton(controller, XboxRaw.A.value);
    changePositionLowButton.whenPressed(changePositionLow);

    changePositionMinButton = new JoystickButton(controller, XboxRaw.Start.value);
    changePositionMinButton.whenPressed(changePositionMin);
  }
}