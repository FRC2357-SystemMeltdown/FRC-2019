package frc.robot.overlays;

import edu.wpi.first.wpilibj.GenericHID.Hand;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.Trigger;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.Commands.HatchDirectOpenCloseCommand;
import frc.robot.Commands.ArmAdjustCommand;
import frc.robot.Commands.ArmPresetCycleCommand;
import frc.robot.Commands.CargoRollerLimitCommand;
import frc.robot.Commands.CargoRollerStopCommand;
import frc.robot.Commands.HatchDirectMoveCommand;
import frc.robot.Commands.HatchStopCommand;
import frc.robot.OI.DPadValue;
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
  private class DPadTrigger extends Trigger {
    DPadValue triggerValue;
    DPadValue lastValue;

    public DPadTrigger(DPadValue triggerValue) {
      this.triggerValue = triggerValue;
    }

    @Override
    public boolean get() {
      DPadValue dPadValue = Robot.OI.getGunnerDPadValue();

      if (dPadValue != lastValue) {
        lastValue = dPadValue;
        return (dPadValue == triggerValue);
      }
      return false;
    }
  }

  public static final double TURN_FACTOR = RobotMap.GUNNER_TURN_PROPORTION;
  public static final double SPEED_FACTOR = RobotMap.GUNNER_SPEED_PROPORTION;

  private CargoRollerLimitCommand cargoRollerCommand;
  private CargoRollerStopCommand cargoRollerStopCommand;
  private JoystickButton cargoRollerButton;

  private ArmPresetCycleCommand armUpCommand;
  private ArmPresetCycleCommand armDownCommand;
  private ArmAdjustCommand armAdjustUpCommand;
  private ArmAdjustCommand armAdjustDownCommand;
  private JoystickButton armAdjustUpButton;
  private JoystickButton armAdjustDownButton;

  private HatchDirectOpenCloseCommand hatchOpenCloseCommand;
  private HatchDirectMoveCommand hatchMoveCommand;
  private HatchStopCommand hatchStopCommand;

  private JoystickButton hatchMoveButton;
  private JoystickButton hatchOpenCloseButton;

  private DPadTrigger upTrigger;
  private DPadTrigger downTrigger;

  public GunnerFailSafe(XboxController controller) {
    super(controller);

    cargoRollerCommand = new CargoRollerLimitCommand(this);
    cargoRollerStopCommand = new CargoRollerStopCommand();
    hatchOpenCloseCommand = new HatchDirectOpenCloseCommand(this);
    hatchMoveCommand = new HatchDirectMoveCommand(this);
    hatchStopCommand = new HatchStopCommand();
    armUpCommand = new ArmPresetCycleCommand(ArmSub.Direction.UP);
    armDownCommand = new ArmPresetCycleCommand(ArmSub.Direction.DOWN);
    armAdjustUpCommand = new ArmAdjustCommand(RobotMap.ARM_ADJUST_UP);
    armAdjustDownCommand = new ArmAdjustCommand(RobotMap.ARM_ADJUST_DOWN);

    cargoRollerButton = new JoystickButton(controller, XboxRaw.A.value);
    hatchMoveButton = new JoystickButton(controller, XboxRaw.X.value);
    hatchOpenCloseButton = new JoystickButton(controller, XboxRaw.Y.value);
    armAdjustUpButton = new JoystickButton(controller, XboxRaw.BumperRight.value);
    armAdjustDownButton = new JoystickButton(controller, XboxRaw.BumperLeft.value);

    upTrigger = new DPadTrigger(DPadValue.Up);
    downTrigger = new DPadTrigger(DPadValue.Down);
  }

  @Override
  public void activate() {
    cargoRollerButton.whenPressed(cargoRollerCommand);
    cargoRollerButton.whenReleased(cargoRollerStopCommand);

    hatchMoveButton.whenPressed(hatchMoveCommand);
    hatchMoveButton.whenReleased(hatchStopCommand);

    hatchOpenCloseButton.whenPressed(hatchOpenCloseCommand);
    hatchOpenCloseButton.whenReleased(hatchStopCommand);

    armAdjustUpButton.whenPressed(armAdjustUpCommand);
    armAdjustDownButton.whenPressed(armAdjustDownCommand);

    upTrigger.whenActive(armUpCommand);
    downTrigger.whenActive(armDownCommand);
  }

  @Override
  public void deactivate() {
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
