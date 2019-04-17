package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class AutoModes {
  public enum StartPosition {
    HAB_1_CENTER,
    HAB_1_LEFT,
    HAB_1_RIGHT,
  }

  public enum StartTask {
    NOTHING,
    LAUNCH,
    CARGO_SHIP_LEFT,
    CARGO_SHIP_RIGHT,
    LEFT_ROCKET_LOW,
    RIGHT_ROCKET_LOW,
  }

  public SendableChooser<StartPosition> positionChooser;
  public SendableChooser<StartTask> taskChooser;

  public AutoModes() {
    this.positionChooser = new SendableChooser<StartPosition>();
    this.taskChooser = new SendableChooser<StartTask>();

    positionChooser.setName("Position");
    positionChooser.setDefaultOption("Center", StartPosition.HAB_1_CENTER);
    positionChooser.addOption("Left", StartPosition.HAB_1_LEFT);
    positionChooser.addOption("Right", StartPosition.HAB_1_RIGHT);

    taskChooser.setName("Task");
    taskChooser.setDefaultOption("Nothing", StartTask.NOTHING);
    taskChooser.addOption("Launch", StartTask.LAUNCH);
    taskChooser.addOption("CShip Left", StartTask.CARGO_SHIP_LEFT);
    taskChooser.addOption("CShip Right", StartTask.CARGO_SHIP_RIGHT);
    taskChooser.addOption("L Rkt Low", StartTask.LEFT_ROCKET_LOW);
    taskChooser.addOption("R Rkt Low", StartTask.RIGHT_ROCKET_LOW);
  }

  public Command getAutoCommand() {
    StartPosition position = positionChooser.getSelected();
    StartTask task = taskChooser.getSelected();

    switch (task) {
      case CARGO_SHIP_LEFT:
        return new AutoLaunchHatchCargoShipLeftCommand(position);
      case CARGO_SHIP_RIGHT:
        return new AutoNothingCommand();
      case LEFT_ROCKET_LOW:
        return new AutoLaunchHatchLeftRocket(position);
      case RIGHT_ROCKET_LOW:
        return new AutoNothingCommand();
      case LAUNCH:
        return new AutoLaunchCommand();
      default:
        return new AutoNothingCommand();
    }
  }
}
