package frc.robot.Other;

/**
 * LED modes defined in the Limelight API
 */
public enum LimelightLEDMode {
  PipelineDefault(0), ///< Use the mode defined in the active pipeline
  Off(1),
  Blink(2),
  On(3);

  public final int value;

  private LimelightLEDMode(int value) {
    this.value = value;
  }
}
