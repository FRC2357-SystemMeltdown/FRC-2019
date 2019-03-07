package frc.robot.overlays;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.RobotMap;
import frc.robot.Commands.CargoRollerDirectCommand;

public class GunnerCargoScoring extends GunnerScoring implements CargoControl {

  private CargoRollerDirectCommand cargoControlCommand;

  public GunnerCargoScoring(XboxController controller) {
    super(
      controller,
      RobotMap.ARM_CARGO_LOW_GOAL_ANGLE,
      RobotMap.ARM_CARGO_MID_GOAL_ANGLE,
      RobotMap.ARM_CARGO_HIGH_GOAL_ANGLE
    );

    cargoControlCommand = new CargoRollerDirectCommand(this);
  }

  @Override
  public double getCargoRollerSpeed() {
    return controller.getTriggerAxis(Hand.kLeft) - controller.getTriggerAxis(Hand.kRight);
  }

  @Override
  public void activate() {
    super.activate();
    cargoControlCommand.start();
  }

  @Override
  public void deactivate() {
    super.deactivate();
    cargoControlCommand.cancel();
  }
}
