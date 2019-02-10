package frc.robot.overlays;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Commands.IntakeInCommand;
import frc.robot.Commands.IntakeOutCommand;
import frc.robot.Other.XboxRaw;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.RobotMap;

public class GunnerFailSafe extends GunnerCreepDrive implements ProportionalDrive {
  public static final double TURN_FACTOR = RobotMap.GUNNER_PROPORTION;
  public static final double SPEED_FACTOR = RobotMap.GUNNER_PROPORTION;

  private IntakeInCommand intakeInCommand;
  private IntakeOutCommand intakeOutCommand;
  private JoystickButton intakeInButton;
  private JoystickButton intakeOutButton;

  public GunnerFailSafe(XboxController controller) {
    super(controller);

    intakeInCommand = new IntakeInCommand();
    intakeOutCommand = new IntakeOutCommand();

    intakeInButton = new JoystickButton(controller, XboxRaw.A.value);
    intakeInButton.whileHeld(intakeInCommand);

    intakeOutButton = new JoystickButton(controller, XboxRaw.B.value);
    intakeOutButton.whileHeld(intakeOutCommand);
  }

  public double getTurn() {
      return controller.getX(Hand.kRight) * TURN_FACTOR;
  }

  public double getSpeed() {
      return controller.getY(Hand.kLeft) * SPEED_FACTOR;
  } 
}
