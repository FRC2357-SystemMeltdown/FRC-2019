package frc.robot.overlays;

import edu.wpi.first.wpilibj.XboxController;

public abstract class ControlOverlay {
  protected XboxController controller;

  public ControlOverlay(XboxController controller) {
    this.controller = controller;
  }
}
