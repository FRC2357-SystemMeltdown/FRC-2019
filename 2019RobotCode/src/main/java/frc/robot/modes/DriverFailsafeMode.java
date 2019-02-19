package frc.robot.modes;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.first.cameraserver.CameraServer;
import frc.robot.Robot;
import frc.robot.overlays.DriverFailSafe;

public class DriverFailsafeMode extends ModeBase {

  private UsbCamera webCam;
  private UsbCamera limelightCam;
  private VideoSink videoServer;

  @Override
  public void activate() {
    Robot.OI.setDriverOverlay(new DriverFailSafe(Robot.OI.getDriverController()));

    webCam = CameraServer.getInstance().startAutomaticCapture(0);
  }

  @Override
  public void deactivate() {

  }
}
