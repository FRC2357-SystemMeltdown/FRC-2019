package frc.robot.modes;

import frc.robot.Robot;
import frc.robot.Commands.MoveArmDirectCommand;
import frc.robot.Subsystems.ArmSub;
import frc.robot.overlays.GunnerFailSafe;

public class GunnerFailsafeMode extends ModeBase {

  private MoveArmDirectCommand defaultCommand = new MoveArmDirectCommand(ArmSub.Direction.STOP);

  @Override
  public void activate() {
    Robot.OI.setGunnerOverlay(new GunnerFailSafe(Robot.OI.getGunnerController()));
    Robot.ARM_SUB.setDefaultCommand(defaultCommand);
  }

  @Override
  public void deactivate() {

  }
}
