package frc.robot.shuffleboard;

public class ShuffleboardController {
  private TestTab testTab = new TestTab();
  private DriveTab driveTab = new DriveTab(); 

  public void test() {
    testTab.show();
    driveTab.show();
  }

  public void periodic() {
    testTab.periodic();
    driveTab.periodic();
  }
}
