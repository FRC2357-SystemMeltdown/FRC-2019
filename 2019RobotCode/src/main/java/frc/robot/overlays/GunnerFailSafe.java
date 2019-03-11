package frc.robot.overlays;

import edu.wpi.first.wpilibj.GenericHID.Hand;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.Commands.HatchDirectOpenCloseCommand;
import frc.robot.Commands.ArmSetPositionCommand;
import frc.robot.Commands.CargoRollerLimitCommand;
import frc.robot.Commands.CargoRollerStopCommand;
import frc.robot.Commands.HatchDirectMoveCommand;
import frc.robot.Commands.HatchStopCommand;
import frc.robot.Commands.NullCommand;
import frc.robot.OI.DPadValue;
import frc.robot.Other.XboxRaw;

/**
 * The GunnerFailSafe overlay is the least complex control system
 * that relies on no sensors to function.
 */
public class GunnerFailSafe
  extends GunnerCreepDrive
  implements ProportionalDrive, HatchControl, CargoControl, ArmControl
{
  public static final double TURN_FACTOR = RobotMap.GUNNER_TURN_PROPORTION;
  public static final double SPEED_FACTOR = RobotMap.GUNNER_SPEED_PROPORTION;

  private CargoRollerLimitCommand cargoRollerCommand;
  private CargoRollerStopCommand cargoRollerStopCommand;
  private JoystickButton cargoRollerButton;

  private ArmSetPositionCommand armSetPositionCommand;

  private HatchDirectOpenCloseCommand hatchOpenCloseCommand;
  private HatchDirectMoveCommand hatchMoveCommand;
  private HatchStopCommand hatchStopCommand;
  private JoystickButton hatchMoveButton;
  private JoystickButton hatchOpenCloseButton;

  private DPadValue lastDPadValue;
  private boolean lastBumperLeft;
  private boolean lastBumperRight;
  private int armAngleIndex;
  private int armAdjust;

  public GunnerFailSafe(XboxController controller) {
    super(controller);

    lastDPadValue = DPadValue.Unpressed;
    lastBumperLeft = false;
    lastBumperRight = false;
    armAngleIndex = 0;
    armAdjust = 0;

    cargoRollerCommand = new CargoRollerLimitCommand(this);
    cargoRollerStopCommand = new CargoRollerStopCommand();
    armSetPositionCommand = new ArmSetPositionCommand(this);
    hatchOpenCloseCommand = new HatchDirectOpenCloseCommand(this);
    hatchMoveCommand = new HatchDirectMoveCommand(this);
    hatchStopCommand = new HatchStopCommand();

    cargoRollerButton = new JoystickButton(controller, XboxRaw.A.value);
    hatchMoveButton = new JoystickButton(controller, XboxRaw.X.value);
    hatchOpenCloseButton = new JoystickButton(controller, XboxRaw.Y.value);
  }

  public int getArmAngleIndex() {
    return armAngleIndex;
  }

  public int getArmAdjust() {
    return armAdjust;
  }

  @Override
  public void activate() {
    armAngleIndex = 0;

    cargoRollerButton.whenPressed(cargoRollerCommand);
    cargoRollerButton.whenReleased(cargoRollerStopCommand);

    hatchMoveButton.whenPressed(hatchMoveCommand);
    hatchMoveButton.whenReleased(hatchStopCommand);

    hatchOpenCloseButton.whenPressed(hatchOpenCloseCommand);
    hatchOpenCloseButton.whenReleased(hatchStopCommand);

    armSetPositionCommand.start();
  }

  @Override
  public void deactivate() {
    NullCommand nullCommand = new NullCommand();

    cargoRollerButton.whenPressed(nullCommand);
    cargoRollerButton.whenReleased(nullCommand);

    hatchMoveButton.whenPressed(nullCommand);
    hatchMoveButton.whenReleased(nullCommand);

    hatchOpenCloseButton.whenPressed(nullCommand);
    hatchOpenCloseButton.whenReleased(nullCommand);

    armSetPositionCommand.cancel();
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

  @Override
  public int getArmTargetValue() {
    updateAngleIndex();
    return RobotMap.ARM_ANGLES[armAngleIndex] + armAdjust;
  }

  private void updateAngleIndex() {
    DPadValue dPadValue = Robot.OI.getGunnerDPadValue();

    if (dPadValue != lastDPadValue) {
      switch(dPadValue) {
        case Up:
          if (armAngleIndex < RobotMap.ARM_ANGLES.length - 1) {
            armAngleIndex++;
            armAdjust = 0;
          }
          break;
        case Down:
          if (armAngleIndex > 0) {
            armAngleIndex--;
            armAdjust = 0;
          }
          break;
        default:
          // Do nothing.
          break;
      }
      lastDPadValue = dPadValue;
    }

    boolean bumperLeft = Robot.OI.getGunnerController().getBumperPressed(Hand.kLeft);
    boolean bumperRight = Robot.OI.getGunnerController().getBumperPressed(Hand.kRight);

    if (bumperLeft != lastBumperLeft) {
      if (bumperLeft) {
        armAdjust += RobotMap.ARM_ADJUST_DOWN;
      }
      lastBumperLeft = bumperLeft;
    }

    if (bumperRight != lastBumperRight) {
      if (bumperRight) {
        armAdjust -= RobotMap.ARM_ADJUST_UP;
      }
      lastBumperRight = bumperRight;
    }
  }
}
