package frc.robot.Other;

/**
 * This class stores a set of PID configuration values.
 */
public class PIDValues {
  public final double kp;
  public final double ki;
  public final double kd;
  public final double kf;

  // The integral will only run if the error is within this zone.
  // Used in the Talon PID controller.
  public final int izone;

  public PIDValues(
    double kp,
    double ki,
    double kd,
    double kf,
    int izone
  ) {
    this.kp = kp;
    this.ki = ki;
    this.kd = kd;
    this.kf = kf;
    this.izone = izone;
  }
}