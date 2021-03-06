package frc.robot.controls;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Trigger;

public class DPadTrigger extends Trigger {
  XboxController controller;
  DPadValue triggerValue;
  DPadValue lastValue;

  public DPadTrigger(XboxController controller, DPadValue triggerValue) {
    this.triggerValue = triggerValue;
    this.controller = controller;
  }

  @Override
  public boolean get() {
    DPadValue dPadValue = DPadValue.fromPOV(controller.getPOV(0));

    if (dPadValue != lastValue) {
      lastValue = dPadValue;
      return (dPadValue == triggerValue);
    }
    return false;
  }
}
