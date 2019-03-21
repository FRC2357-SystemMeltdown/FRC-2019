package frc.robot.controls;

import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.Trigger;
import frc.robot.Robot;

public class NonAutoModeButtonTrigger extends Trigger {
  private JoystickButton button;

  public NonAutoModeButtonTrigger(JoystickButton button) {
    this.button = button;
  }

  @Override
  public boolean get() {
    return button.get() && !Robot.OI.isAutoModePreview();
  }
}
