package frc.robot.shuffleboard;

public class ShuffleboardController {
  private TestTab testTab = new TestTab();
  private DriveTab driveTab = new DriveTab(); 
  private TabBase currentTab;

  public void test() {
    switchToTab(testTab);
  }

  public void drive() {
    switchToTab(driveTab);
  }

  public void periodic() {
    if(currentTab != null) {
      currentTab.periodic();
    }
  }

  private void switchToTab(TabBase tab) {
    currentTab = tab;
    currentTab.show();
  }
}
