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
    cargoRollerButton.whenPressed(cargoRollerCommand);
    cargoRollerButton.whenReleased(cargoRollerStopCommand);

    armUpButton = new JoystickButton(controller, XboxRaw.BumperRight.value);
    armUpButton.whileHeld(armUpCommand);

    armDownButton = new JoystickButton(controller, XboxRaw.BumperLeft.value);
    armDownButton.whileHeld(armDownCommand);

    hatchMoveButton = new JoystickButton(controller, XboxRaw.X.value);
    hatchMoveButton.whenPressed(hatchMoveCommand);
    hatchMoveButton.whenReleased(hatchStopCommand);

    hatchOpenCloseButton = new JoystickButton(controller, XboxRaw.Y.value);
    hatchOpenCloseButton.whenPressed(hatchOpenCloseCommand);
    hatchOpenCloseButton.whenReleased(hatchStopCommand);
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
