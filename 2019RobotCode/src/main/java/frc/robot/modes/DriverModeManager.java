package frc.robot.modes;

import frc.robot.OI.DPadValue;

/**
 * Initializes the driver mode list.
 */
public class DriverModeManager extends DPadModeManager {
  public DriverModeManager() {
    super(
      new DriverFailsafeMode(),
      new DriverPIDMode(),
      null,
      null,
      DPadValue.Up,
      DPadValue.Up
    );
  }
}
