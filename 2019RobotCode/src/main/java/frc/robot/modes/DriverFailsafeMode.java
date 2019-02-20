package frc.robot.modes;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.first.cameraserver.CameraServer;
import frc.robot.Robot;
import frc.robot.overlays.DriverFailSafe;

public class DriverFailsafeMode extends ModeBase {

  @Override
  public void activate() {
    Robot.OI.setDriverOverlay(new DriverFailSafe(Robot.OI.getDriverController()));

    
  }

  @Override
  public void deactivate() {

  }
}
