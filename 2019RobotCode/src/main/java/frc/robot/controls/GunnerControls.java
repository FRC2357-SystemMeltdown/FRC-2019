package frc.robot.controls;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.RobotMap;

public class GunnerControls extends Controls
    implements ProportionalDrive, CargoControl {
  public static final double TURN_FACTOR = RobotMap.GUNNER_TURN_PROPORTION;
  public static final double SPEED_FACTOR = RobotMap.GUNNER_SPEED_PROPORTION;

  public JoystickButton cargoRollerButton;
  public JoystickButton armAdjustUpButton;
  public JoystickButton armAdjustDownButton;
  public DPadTrigger armCycleUpTrigger;
  public DPadTrigger armCycleDownTrigger;

  public GunnerControls(XboxController controller) {
    super(controller);

    cargoRollerButton = new JoystickButton(controller, XboxRaw.A.value);
    armAdjustUpButton = new JoystickButton(controller, XboxRaw.BumperRight.value);
    armAdjustDownButton = new JoystickButton(controller, XboxRaw.BumperLeft.value);
    armCycleUpTrigger = new DPadTrigger(controller, DPadValue.Up);
    armCycleDownTrigger = new DPadTrigger(controller, DPadValue.Down);
  }

  @Override
  public double getProportionalTurn() {
    return controller.getX(Hand.kRight) * TURN_FACTOR;
  }

  @Override
  public double getProportionalSpeed() {
    return -(controller.getY(Hand.kLeft) * SPEED_FACTOR);
  }

  @Override
  public double getCargoRollerSpeed() {
    return controller.getTriggerAxis(Hand.kLeft) - controller.getTriggerAxis(Hand.kRight);
  }
}
