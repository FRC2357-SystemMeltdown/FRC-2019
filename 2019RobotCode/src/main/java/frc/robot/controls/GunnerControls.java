package frc.robot.controls;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.RobotMap;

public class GunnerControls extends Controls
    implements ProportionalDrive, CargoControl {
  public static final double TURN_FACTOR = RobotMap.GUNNER_TURN_PROPORTION;
  public static final double SPEED_FACTOR = RobotMap.GUNNER_SPEED_PROPORTION;

  public final JoystickButton cargoRollerButton;
  public final JoystickButton armAdjustUpButton;
  public final JoystickButton armAdjustDownButton;
  public final JoystickButton autoModeButton;
  public final JoystickButton autoHatchModeButton;
  public final JoystickButton autoCargoModeButton;
  public final JoystickButton autoScoreLowButton;
  public final JoystickButton autoScoreMidButton;
  public final JoystickButton autoScoreHighButton;
  public final JoystickButton autoLoadingStationButton;
  public final DPadTrigger armCycleUpTrigger;
  public final DPadTrigger armCycleDownTrigger;
  public final ButtonChordTrigger autoHatchLoadingStationTrigger;
  public final ButtonChordTrigger autoHatchScoreLowTrigger;
  public final ButtonChordTrigger autoHatchScoreMidTrigger;
  public final ButtonChordTrigger autoHatchScoreHighTrigger;
  public final ButtonChordTrigger autoCargoLoadingStationTrigger;
  public final ButtonChordTrigger autoCargoScoreLowTrigger;
  public final ButtonChordTrigger autoCargoScoreMidTrigger;
  public final ButtonChordTrigger autoCargoScoreHighTrigger;

  public GunnerControls(XboxController controller) {
    super(controller);

    cargoRollerButton = new JoystickButton(controller, XboxRaw.A.value);
    armAdjustUpButton = new JoystickButton(controller, XboxRaw.BumperRight.value);
    armAdjustDownButton = new JoystickButton(controller, XboxRaw.BumperLeft.value);
    autoModeButton = new JoystickButton(controller, XboxRaw.Back.value);
    autoHatchModeButton = new JoystickButton(controller, XboxRaw.BumperLeft.value);
    autoCargoModeButton = new JoystickButton(controller, XboxRaw.BumperRight.value);
    autoScoreLowButton = new JoystickButton(controller, XboxRaw.A.value);
    autoScoreMidButton = new JoystickButton(controller, XboxRaw.X.value);
    autoScoreHighButton = new JoystickButton(controller, XboxRaw.Y.value);
    autoLoadingStationButton = new JoystickButton(controller, XboxRaw.B.value);

    armCycleUpTrigger = new DPadTrigger(controller, DPadValue.Up);
    armCycleDownTrigger = new DPadTrigger(controller, DPadValue.Down);

    autoHatchLoadingStationTrigger = new ButtonChordTrigger(
      new JoystickButton[] {autoModeButton, autoHatchModeButton, autoLoadingStationButton}
    );
    autoHatchScoreLowTrigger = new ButtonChordTrigger(
      new JoystickButton[] {autoModeButton, autoHatchModeButton, autoScoreLowButton}
    );
    autoHatchScoreMidTrigger = new ButtonChordTrigger(
      new JoystickButton[] {autoModeButton, autoHatchModeButton, autoScoreMidButton}
    );
    autoHatchScoreHighTrigger = new ButtonChordTrigger(
      new JoystickButton[] {autoModeButton, autoHatchModeButton, autoScoreHighButton}
    );

    autoCargoLoadingStationTrigger = new ButtonChordTrigger(
      new JoystickButton[] {autoModeButton, autoCargoModeButton, autoLoadingStationButton}
    );
    autoCargoScoreLowTrigger = new ButtonChordTrigger(
      new JoystickButton[] {autoModeButton, autoCargoModeButton, autoScoreLowButton}
    );
    autoCargoScoreMidTrigger = new ButtonChordTrigger(
      new JoystickButton[] {autoModeButton, autoCargoModeButton, autoScoreMidButton}
    );
    autoCargoScoreHighTrigger = new ButtonChordTrigger(
      new JoystickButton[] {autoModeButton, autoCargoModeButton, autoScoreHighButton}
    );
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
