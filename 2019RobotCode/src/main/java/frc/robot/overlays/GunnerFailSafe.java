package frc.robot.overlays;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.RobotMap;
import frc.robot.Commands.HatchDirectOpenCloseCommand;
import frc.robot.Commands.CargoRollerDirectCommand;
import frc.robot.Commands.CargoRollerStopCommand;
import frc.robot.Commands.HatchDirectMoveCommand;
import frc.robot.Commands.HatchStopCommand;
import frc.robot.Commands.MoveArmDirectCommand;
import frc.robot.Commands.NullCommand;
import frc.robot.Other.XboxRaw;
import frc.robot.Subsystems.ArmSub;

/**
 * The GunnerFailSafe overlay is the least complex control system
 * that relies on no sensors to function.
 */
public class GunnerFailSafe
  extends GunnerCreepDrive
  implements ProportionalDrive, HatchControl, CargoControl
{
  public static final double TURN_FACTOR = RobotMap.GUNNER_TURN_PROPORTION;
  public static final double SPEED_FACTOR = RobotMap.GUNNER_SPEED_PROPORTION;

  private CargoRollerDirectCommand cargoRollerCommand;
  private CargoRollerStopCommand cargoRollerStopCommand;
  private JoystickButton cargoRollerButton;

  private MoveArmDirectCommand armUpCommand;
  private MoveArmDirectCommand armDownCommand;
  private JoystickButton armUpButton;
  private JoystickButton armDownButton;

  private HatchDirectOpenCloseCommand hatchOpenCloseCommand;
  private HatchDirectMoveCommand hatchMoveCommand;
  private HatchStopCommand hatchStopCommand;
  private JoystickButton hatchMoveButton;
  private JoystickButton hatchOpenCloseButton;

  public GunnerFailSafe(XboxController controller) {
    super(controller);

    cargoRollerCommand = new CargoRollerDirectCommand(this);
    cargoRollerStopCommand = new CargoRollerStopCommand();
    armUpCommand = new MoveArmDirectCommand(ArmSub.Direction.UP);
    armDownCommand = new MoveArmDirectCommand(ArmSub.Direction.DOWN);
    hatchOpenCloseCommand = new HatchDirectOpenCloseCommand(this);
    hatchMoveCommand = new HatchDirectMoveCommand(this);
    hatchStopCommand = new HatchStopCommand();

    cargoRollerButton = new JoystickButton(controller, XboxRaw.A.value);
    armUpButton = new JoystickButton(controller, XboxRaw.BumperRight.value);
    armDownButton = new JoystickButton(controller, XboxRaw.BumperLeft.value);
    hatchMoveButton = new JoystickButton(controller, XboxRaw.X.value);
    hatchOpenCloseButton = new JoystickButton(controller, XboxRaw.Y.value);
  }

  @Override
  public void activate() {
    cargoRollerButton.whenPressed(cargoRollerCommand);
    cargoRollerButton.whenReleased(cargoRollerStopCommand);

    armUpButton.whileHeld(armUpCommand);

    armDownButton.whileHeld(armDownCommand);

    hatchMoveButton.whenPressed(hatchMoveCommand);
    hatchMoveButton.whenReleased(hatchStopCommand);

    hatchOpenCloseButton.whenPressed(hatchOpenCloseCommand);
    hatchOpenCloseButton.whenReleased(hatchStopCommand);
  }

  @Override
  public void deactivate() {
    NullCommand nullCommand = new NullCommand();

    cargoRollerButton.whenPressed(nullCommand);
    cargoRollerButton.whenReleased(nullCommand);

    armUpButton.whileHeld(nullCommand);

    armDownButton.whileHeld(nullCommand);

    hatchMoveButton.whenPressed(nullCommand);
    hatchMoveButton.whenReleased(nullCommand);

    hatchOpenCloseButton.whenPressed(nullCommand);
    hatchOpenCloseButton.whenReleased(nullCommand);
  }

  @Override
  public double getTurn() {
      return controller.getX(Hand.kRight) * TURN_FACTOR;
  }

  @Override
  public double getSpeed() {
      return - (controller.getY(Hand.kLeft) * SPEED_FACTOR);
  }

  @Override
  public double getHatchMoveSpeed() {
    return controller.getTriggerAxis(Hand.kLeft) - controller.getTriggerAxis(Hand.kRight);
  }

  @Override
  public double getHatchOpenCloseSpeed() {
    return controller.getTriggerAxis(Hand.kLeft) - controller.getTriggerAxis(Hand.kRight);
  }

  @Override
  public double getCargoRollerSpeed() {
    return controller.getTriggerAxis(Hand.kLeft) - controller.getTriggerAxis(Hand.kRight);
  }
}
