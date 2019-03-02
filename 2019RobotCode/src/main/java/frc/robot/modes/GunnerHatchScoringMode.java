package frc.robot.modes;

import frc.robot.Robot;
import frc.robot.Commands.ArmPositionCommand;
import frc.robot.overlays.GunnerHatchScoring;

public class GunnerHatchScoringMode extends ModeBase {

  private ArmPositionCommand armPositionCommand = new ArmPositionCommand();
  @Override
  public void activate() {
    Robot.OI.setGunnerOverlay(new GunnerHatchScoring(Robot.OI.getGunnerController()));
    Robot.ARM_SUB.setDefaultCommand(armPositionCommand);
  }

  @Override
  public void deactivate() {

  }
}