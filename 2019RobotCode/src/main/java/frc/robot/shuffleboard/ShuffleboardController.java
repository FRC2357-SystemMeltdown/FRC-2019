package frc.robot.shuffleboard;

public class ShuffleboardController {
  private TestTab testTab = new TestTab();

  public void test() {
    testTab.show();
  }

  public void periodic() {
    testTab.periodic();
  }
}
