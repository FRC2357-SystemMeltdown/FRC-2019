package frc.robot.overlays;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.RobotMap;
import frc.robot.Commands.HatchOpenCloseCommand;
import frc.robot.Commands.IntakeInCommand;
import frc.robot.Commands.IntakeOutCommand;
import frc.robot.Commands.MoveArmDirectCommand;
import frc.robot.Commands.MoveHatchCommand;
import frc.robot.Other.XboxRaw;

public class GunnerFailSafe extends GunnerCreepDrive implements ProportionalDrive {
  public static final double TURN_FACTOR = RobotMap.GUNNER_PROPORTION;
  public static final double SPEED_FACTOR = RobotMap.GUNNER_PROPORTION;

  private IntakeInCommand intakeInCommand;
  private IntakeOutCommand intakeOutCommand;
  private JoystickButton intakeInButton;
  private JoystickButton intakeOutButton;

  private MoveArmDirectCommand armUpCommand;
  private MoveArmDirectCommand armDownCommand;
  private JoystickButton armUpButton;
  private JoystickButton armDownButton;

  private JoystickButton hatchMoveButton;
  private JoystickButton hatchOpenCloseButton;

  public GunnerFailSafe(XboxController controller) {
    super(controller);

    intakeInCommand = new IntakeInCommand();
    intakeOutCommand = new IntakeOutCommand();
    armUpCommand = new MoveArmDirectCommand(RobotMap.ARM_UP_SPEED);
    armDownCommand = new MoveArmDirectCommand(RobotMap.ARM_DOWN_SPEED);

    intakeInButton = new JoystickButton(controller, XboxRaw.A.value);
    intakeInButton.whileHeld(intakeInCommand);

    intakeOutButton = new JoystickButton(controller, XboxRaw.B.value);
    intakeOutButton.whileHeld(intakeOutCommand);

    armUpButton = new JoystickButton(controller, XboxRaw.BumperRight.value);
    armUpButton.whileHeld(armUpCommand);

    armDownButton = new JoystickButton(controller, XboxRaw.BumperLeft.value);
    armDownButton.whileHeld(armDownCommand);

    hatchMoveButton = new JoystickButton(controller, XboxRaw.X.value);
    hatchMoveButton.whileHeld(new MoveHatchCommand(controller.getTriggerAxis(Hand.kLeft), controller.getTriggerAxis(Hand.kRight)));
    
    hatchOpenCloseButton = new JoystickButton(controller, XboxRaw.Y.value);
    hatchOpenCloseButton.whileHeld(new HatchOpenCloseCommand(controller.getTriggerAxis(Hand.kLeft), controller.getTriggerAxis(Hand.kRight)));
  }

  public double getTurn() {
      return controller.getX(Hand.kRight) * TURN_FACTOR;
  }

  public double getSpeed() {
      return controller.getY(Hand.kLeft) * SPEED_FACTOR;
  } 
}
