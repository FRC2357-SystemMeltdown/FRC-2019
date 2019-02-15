package frc.robot.overlays;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.RobotMap;
import frc.robot.Commands.ChangeArmStateCommand;
import frc.robot.Commands.IntakeInCommand;

public class GunnerCargoFromFloor extends GunnerCreepDrive {

  private ChangeArmStateCommand setCargoPickupState;
  private IntakeInCommand intakeIn;

  public GunnerCargoFromFloor(XboxController controller) {
    super(controller);

    setCargoPickupState = new ChangeArmStateCommand(RobotMap.ARM_CARGO_PICKUP_ANGLE);
    setCargoPickupState.start();

    intakeIn = new IntakeInCommand();
    intakeIn.start();
  }
}
