package frc.robot.Other;

/**
 * Picture in picture modes.
 * 
 * These are how the Limelight can include video
 * from the attached USB camera in its stream.
 */
public enum LimelightPiPMode {

  SideBySide(0),  ///< Display both side by side
  Main(1),        ///< The Limelight fills the view
  Secondary(2);   ///< The USB camera fills the view

  public final int value;

  private LimelightPiPMode(int value) {
    this.value = value;
  }
}
