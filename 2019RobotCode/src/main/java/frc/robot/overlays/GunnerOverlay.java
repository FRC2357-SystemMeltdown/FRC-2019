package frc.robot.overlays;

import edu.wpi.first.wpilibj.XboxController;

public abstract class GunnerOverlay {
  protected XboxController controller;

  public GunnerOverlay(XboxController controller) {
    this.controller = controller;
  }
}
