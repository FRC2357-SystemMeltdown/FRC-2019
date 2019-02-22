package frc.robot.shuffleboard;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

/**
 * Base class for Shuffleboard tabs. This handles some basic functions
 * and presents a unified interface to ShuffleboardController.
 */
public abstract class TabBase {
  protected ShuffleboardTab tab;
  private String title;

  public TabBase(String title) {
    this.title = title;
    tab = Shuffleboard.getTab(getTitle());
  }

  public abstract void periodic();

  public String getTitle() {
    return title;
  }

  public void show() {
    Shuffleboard.selectTab(getTitle());
  }
}