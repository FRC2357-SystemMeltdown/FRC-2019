package frc.robot.controls;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.Other.Utility;
import frc.robot.RobotMap;

public class GunnerControls extends Controls
    implements ProportionalDrive, VelocityDrive, CargoControl {
  public static final double TURN_FACTOR = RobotMap.GUNNER_TURN_PROPORTION;
  public static final double SPEED_FACTOR = RobotMap.GUNNER_SPEED_PROPORTION;

  private final JoystickButton armAdjustUpButton;
  private final JoystickButton armAdjustDownButton;

  public final JoystickButton cargoRollerButton;
  public final JoystickButton autoModeButton;
  public final JoystickButton autoTargetLeftButton;
  public final JoystickButton autoTargetRightButton;
  public final JoystickButton autoScoreLowButton;
  public final JoystickButton autoScoreMidButton;
  public final JoystickButton autoScoreHighButton;
  public final JoystickButton autoLoadingStationButton;
  public final DPadTrigger armCycleUpTrigger;
  public final DPadTrigger armCycleDownTrigger;
  public final NonAutoModeButtonTrigger armAdjustUpTrigger;
  public final NonAutoModeButtonTrigger armAdjustDownTrigger;
  public final ButtonChordTrigger autoHatchLoadingStationTrigger;
  public final ButtonChordTrigger autoHatchScoreLowTrigger;
  public final ButtonChordTrigger autoHatchScoreMidTrigger;
  public final ButtonChordTrigger autoHatchScoreHighTrigger;

  public GunnerControls(XboxController controller) {
    super(controller);

    cargoRollerButton = new JoystickButton(controller, XboxRaw.A.value);
    armAdjustUpButton = new JoystickButton(controller, XboxRaw.BumperRight.value);
    armAdjustDownButton = new JoystickButton(controller, XboxRaw.BumperLeft.value);
    autoModeButton = new JoystickButton(controller, XboxRaw.Back.value);
    autoTargetLeftButton = new JoystickButton(controller, XboxRaw.BumperLeft.value);
    autoTargetRightButton = new JoystickButton(controller, XboxRaw.BumperRight.value);
    autoScoreLowButton = new JoystickButton(controller, XboxRaw.A.value);
    autoScoreMidButton = new JoystickButton(controller, XboxRaw.X.value);
    autoScoreHighButton = new JoystickButton(controller, XboxRaw.Y.value);
    autoLoadingStationButton = new JoystickButton(controller, XboxRaw.B.value);

    armAdjustUpTrigger = new NonAutoModeButtonTrigger(armAdjustUpButton);
    armAdjustDownTrigger = new NonAutoModeButtonTrigger(armAdjustDownButton);

    armCycleUpTrigger = new DPadTrigger(controller, DPadValue.Up);
    armCycleDownTrigger = new DPadTrigger(controller, DPadValue.Down);

    autoHatchLoadingStationTrigger = new ButtonChordTrigger(
      new JoystickButton[] {autoModeButton, autoLoadingStationButton}
    );
    autoHatchScoreLowTrigger = new ButtonChordTrigger(
      new JoystickButton[] {autoModeButton, autoScoreLowButton}
    );
    autoHatchScoreMidTrigger = new ButtonChordTrigger(
      new JoystickButton[] {autoModeButton, autoScoreMidButton}
    );
    autoHatchScoreHighTrigger = new ButtonChordTrigger(
      new JoystickButton[] {autoModeButton, autoScoreHighButton}
    );
  }

  @Override
  public boolean isActive() {
    return super.isActive() ||
      cargoRollerButton.get() ||
      armAdjustUpButton.get() ||
      armAdjustDownButton.get() ||
      autoModeButton.get() ||
      autoTargetLeftButton.get() ||
      autoTargetRightButton.get() ||
      autoScoreLowButton.get() ||
      autoScoreMidButton.get() ||
      autoScoreHighButton.get() ||
      autoLoadingStationButton.get() ||
      armAdjustUpTrigger.get() ||
      armAdjustDownTrigger.get() ||
      armCycleUpTrigger.get() ||
      armCycleDownTrigger.get();
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
  public int getEncoderTurnDifferential() {
    double input = Utility.deadband(controller.getX(Hand.kRight), RobotMap.DRIVE_STICK_DEADBAND);
    int turnRate = (int)(input * RobotMap.GUNNER_ENCODER_TURN_RATE);
    return turnRate;
  }

  @Override
  public int getEncoderSpeed() {
    double input = Utility.deadband(controller.getY(Hand.kLeft), RobotMap.DRIVE_STICK_DEADBAND);
    int speed = (int)(-input * RobotMap.GUNNER_ENCODER_SPEED);
    return speed;
  }

  @Override
  public double getCargoRollerSpeed() {
    return controller.getTriggerAxis(Hand.kLeft) - controller.getTriggerAxis(Hand.kRight);
  }
}
