package frc.robot.overlays;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.RobotMap;
import frc.robot.Commands.ChangeArmStateCommand;
import frc.robot.Commands.CargoRollerDirectCommand;;

public class GunnerCargoFromFloor extends GunnerCreepDrive implements CargoControl {

  private ChangeArmStateCommand setCargoPickupState;
  private CargoRollerDirectCommand intakeIn;

  public GunnerCargoFromFloor(XboxController controller) {
    super(controller);

    setCargoPickupState = new ChangeArmStateCommand(RobotMap.ARM_CARGO_PICKUP_ANGLE);
    setCargoPickupState.start();

    intakeIn = new CargoRollerDirectCommand(this);
    intakeIn.start();
  }

  @Override
  public double getCargoRollerSpeed() {
    return controller.getTriggerAxis(Hand.kLeft) - controller.getTriggerAxis(Hand.kRight);
  }
}
