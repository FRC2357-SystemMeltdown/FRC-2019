package frc.robot.shuffleboard;

import edu.wpi.cscore.VideoSource;

public class DriveTab extends TabBase {

  private static final String TITLE = "Drive Tab";

  private VideoSource limelight;
  private VideoSource usbCamera;

  public DriveTab() {
    super(TITLE);

    for(VideoSource source : VideoSource.enumerateSources()) {
    if(source.getName() == "limelight") {
      limelight = source;
      tab.add(limelight);
    } else if(source.getName() == "USB Camera 0") {
      usbCamera = source;
      tab.add(usbCamera);
    }
    }
  }

  public void periodic() {
    if(limelight != null) {
      for(VideoSource source : VideoSource.enumerateSources()) {
        if(source.getName() == "limelight") {
          limelight = source;
          tab.add(limelight);
        }
      }
    } else if(usbCamera != null) {
      for(VideoSource source : VideoSource.enumerateSources()) {
        if(source.getName() == "USB Camera 0") {
          usbCamera = source;
          tab.add(usbCamera);
        }
      }
    }
  }
}