package frc.robot.overlays;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.RobotMap;
import frc.robot.Commands.CargoRollerDirectCommand;

public class GunnerCargoFromFloor extends GunnerLoading implements CargoControl {

  private CargoRollerDirectCommand cargoControlCommand;

  public GunnerCargoFromFloor(XboxController controller) {
    super(controller, RobotMap.ARM_CARGO_PICKUP_ANGLE);

    cargoControlCommand = new CargoRollerDirectCommand(this);
    cargoControlCommand.start();
  }

  @Override
  public double getCargoRollerSpeed() {
    return controller.getTriggerAxis(Hand.kLeft) - controller.getTriggerAxis(Hand.kRight);
  }
}
