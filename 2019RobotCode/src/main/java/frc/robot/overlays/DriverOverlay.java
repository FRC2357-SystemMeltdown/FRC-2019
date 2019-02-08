package frc.robot.overlays;

import edu.wpi.first.wpilibj.XboxController;

public abstract class DriverOverlay {
  protected XboxController controller;

  public DriverOverlay(XboxController controller) {
    this.controller = controller;
  }
}
