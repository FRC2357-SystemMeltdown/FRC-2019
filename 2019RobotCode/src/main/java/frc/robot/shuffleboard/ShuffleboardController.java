package frc.robot.shuffleboard;

public class ShuffleboardController {
  private DriveTab driveTab = new DriveTab();
  private SettingsTab settingsTab = new SettingsTab();
  private TestTab testTab = new TestTab();

  public void drive() {
    driveTab.show();
  }

  public void test() {
    testTab.show();
  }

  public void settings() {
    settingsTab.show();
  }

  public void periodic() {
    driveTab.periodic();
    testTab.periodic();
  }
}
