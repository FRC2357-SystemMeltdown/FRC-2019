package frc.robot.shuffleboard;

import edu.wpi.cscore.VideoSource;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class DriveTab {

    private ShuffleboardTab tab;
    private final String TITLE = "Drive Tab";

    private VideoSource limelight;
    private VideoSource USBCamera0;

    public DriveTab() {
        tab = Shuffleboard.getTab(TITLE);

        for(VideoSource source : VideoSource.enumerateSources()) {
            if(source.getName() == "limelight") {
                limelight = source;
                tab.add(limelight);
            } else if(source.getName() == "USB Camera 0") {
                USBCamera0 = source;
                tab.add(USBCamera0);
            }
        }
    }

    public void show() {
        Shuffleboard.selectTab(TITLE);
    }

    public void periodic() {
        if(limelight != null) {
            for(VideoSource source : VideoSource.enumerateSources()) {
                if(source.getName() == "limelight") {
                    limelight = source;
                    tab.add(limelight);
                }
            }
        } else if(USBCamera0 != null) {
            for(VideoSource source : VideoSource.enumerateSources()) {
                if(source.getName() == "USB Camera 0") {
                    USBCamera0 = source;
                    tab.add(USBCamera0);
                }
            }
        }
    }
}