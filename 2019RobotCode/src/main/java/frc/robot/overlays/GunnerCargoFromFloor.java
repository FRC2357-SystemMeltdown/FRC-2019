package frc.robot.overlays;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.RobotMap;
import frc.robot.Commands.CargoRollerIntakeCommand;

public class GunnerCargoFromFloor extends GunnerLoading implements CargoControl {

  private CargoRollerIntakeCommand cargoControlCommand;

  public GunnerCargoFromFloor(XboxController controller) {
    super(controller, RobotMap.ARM_CARGO_PICKUP_ANGLE);

    cargoControlCommand = new CargoRollerIntakeCommand(this);
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

  @Override
  public double getCargoRollerSpeed() {
    return controller.getTriggerAxis(Hand.kLeft) - controller.getTriggerAxis(Hand.kRight);
  }
}
