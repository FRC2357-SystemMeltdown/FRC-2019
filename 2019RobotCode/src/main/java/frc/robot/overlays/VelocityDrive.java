package frc.robot.overlays;

/**
 * A proportional drive control.
 *
 * This represents the simplest of drive system controls.
 */
public interface VelocityDrive {
  /**
   * Gets a turn value in degrees/second.
   * @return A value between -MAX_TURN_RATE_DEGREES_PER_SECOND (full left) and 
   * MAX_TURN_RATE_DEGREES_PER_SECOND (full right)
   */
  public double getTurnDegreesPerSecond();

  /**
   * Gets a proportional speed value.
   * @return A value between -MAX_VELOCITY_INCHES_PER_SECOND (full reverse) and
   * MAX_VELOCITY_INCHES_PER_SECOND (full forward)
   */
  public double getSpeedInchesPerSecond();
}