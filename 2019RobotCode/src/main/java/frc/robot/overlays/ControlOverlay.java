package frc.robot.overlays;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public abstract class ControlOverlay {
  protected XboxController controller;

  protected enum Button
  {
    A,
    B,
    X,
    Y,
    BumperLeft,
    BumperRight,
    StickLeft,
    StickRight,
    Back,
    Start,

    ButtonCount
  }

  private boolean[] pressed;
  private boolean[] released;
  private boolean[] down;

  public ControlOverlay(XboxController controller) {
    this.controller = controller;

    pressed = new boolean[Button.ButtonCount.ordinal()];
    released = new boolean[Button.ButtonCount.ordinal()];
    down = new boolean[Button.ButtonCount.ordinal()];
  }

  public void pollButtons()
  {
    updateButtonStates();
    handleButtonInputs();
  }

  protected boolean wasButtonPressed(Button button)
  {
    return pressed[button.ordinal()];
  }

  protected boolean wasButtonReleased(Button button)
  {
    return released[button.ordinal()];
  }

  protected boolean isButtonDown(Button button)
  {
    return down[button.ordinal()];
  }

  // Override this to respond to button inputs
  protected void handleButtonInputs()
  {
    // Do nothing by default
  }

  private void updateButtonStates()
  {
    pressed[Button.A.ordinal()] = controller.getAButtonPressed();
    pressed[Button.B.ordinal()] = controller.getBButtonPressed();
    pressed[Button.X.ordinal()] = controller.getXButtonPressed();
    pressed[Button.Y.ordinal()] = controller.getYButtonPressed();
    pressed[Button.BumperLeft.ordinal()] = controller.getBumperPressed(Hand.kLeft);
    pressed[Button.BumperRight.ordinal()] = controller.getBumperPressed(Hand.kRight);
    pressed[Button.StickLeft.ordinal()] = controller.getStickButtonPressed(Hand.kLeft);
    pressed[Button.StickRight.ordinal()] = controller.getStickButtonReleased(Hand.kRight);
    pressed[Button.Back.ordinal()] = controller.getBackButtonPressed();
    pressed[Button.Start.ordinal()] = controller.getStartButtonPressed();

    released[Button.A.ordinal()] = controller.getAButtonReleased();
    released[Button.B.ordinal()] = controller.getBButtonReleased();
    released[Button.X.ordinal()] = controller.getXButtonReleased();
    released[Button.Y.ordinal()] = controller.getYButtonReleased();
    released[Button.BumperLeft.ordinal()] = controller.getBumperReleased(Hand.kLeft);
    released[Button.BumperRight.ordinal()] = controller.getBumperReleased(Hand.kRight);
    released[Button.StickLeft.ordinal()] = controller.getStickButtonReleased(Hand.kLeft);
    released[Button.StickRight.ordinal()] = controller.getStickButtonReleased(Hand.kRight);
    released[Button.Back.ordinal()] = controller.getBackButtonReleased();
    released[Button.Start.ordinal()] = controller.getStartButtonReleased();

    down[Button.A.ordinal()] = controller.getAButton();
    down[Button.B.ordinal()] = controller.getBButton();
    down[Button.X.ordinal()] = controller.getXButton();
    down[Button.Y.ordinal()] = controller.getYButton();
    down[Button.BumperLeft.ordinal()] = controller.getBumper(Hand.kLeft);
    down[Button.BumperRight.ordinal()] = controller.getBumper(Hand.kRight);
    down[Button.StickLeft.ordinal()] = controller.getStickButton(Hand.kLeft);
    down[Button.StickRight.ordinal()] = controller.getStickButton(Hand.kRight);
    down[Button.Back.ordinal()] = controller.getBackButton();
    down[Button.Start.ordinal()] = controller.getStartButton();
  }
}
