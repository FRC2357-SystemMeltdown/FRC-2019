/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Commands.ArmAdjustCommand;
import frc.robot.Commands.ArmPresetCycleCommand;
import frc.robot.Commands.VisionPipelineSetCommand;
import frc.robot.Commands.CargoRollerCommand;
import frc.robot.Other.Utility;
import frc.robot.Subsystems.ArmSub.Direction;
import frc.robot.Subsystems.VisionSub.PipelineIndex;
import frc.robot.controls.DriverControls;
import frc.robot.controls.GunnerControls;
import frc.robot.controls.ProportionalDrive;
import frc.robot.controls.VelocityDrive;

/**
 * Operator Interface
 */
public class OI implements ProportionalDrive, VelocityDrive {
  public static final int CONTROLLER_ID_DRIVER = 0;
  public static final int CONTROLLER_ID_GUNNER = 1;

  private DriverControls driverControls;
  private GunnerControls gunnerControls;

  public OI() {
    this.driverControls = new DriverControls(new XboxController(CONTROLLER_ID_DRIVER));
    this.gunnerControls = new GunnerControls(new XboxController(CONTROLLER_ID_GUNNER));

    gunnerControls.cargoRollerButton.whileHeld(new CargoRollerCommand(gunnerControls));

    gunnerControls.armAdjustDownButton.whileHeld(new ArmAdjustCommand(Direction.DOWN));
    gunnerControls.armAdjustUpButton.whileHeld(new ArmAdjustCommand(Direction.UP));

    gunnerControls.armCycleDownTrigger.whenActive(new ArmPresetCycleCommand(Direction.DOWN));
    gunnerControls.armCycleUpTrigger.whenActive(new ArmPresetCycleCommand(Direction.UP));

    gunnerControls.autoModeButton.whenPressed(new VisionPipelineSetCommand(PipelineIndex.VISION_TARGET));
    gunnerControls.autoModeButton.whenReleased(new VisionPipelineSetCommand(PipelineIndex.HUMAN_VIEW));
  }

  @Override
  public double getProportionalTurn() {
    double turn = 0;

    turn += driverControls.getProportionalTurn();
    turn += gunnerControls.getProportionalTurn();

    turn = Utility.clamp(turn, -1.0, 1.0);

    return turn;
  }

  @Override
  public double getProportionalSpeed() {
    double speed = 0;

    speed += driverControls.getProportionalSpeed();
    speed += gunnerControls.getProportionalSpeed();

    speed = Utility.clamp(speed, -1.0, 1.0);

    return speed;
  }

  @Override
  public double getTurnDegreesPerSecond() {
    double turn = 0.0;

    turn += driverControls.getTurnDegreesPerSecond();

    turn = Utility.clamp(turn, -RobotMap.MAX_TURN_RATE_DEGREES_PER_SECOND, RobotMap.MAX_TURN_RATE_DEGREES_PER_SECOND);

    return turn;
  }

  @Override
  public double getSpeedInchesPerSecond() {
    double speed = 0.0;

    speed += driverControls.getSpeedInchesPerSecond();

    speed = Utility.clamp(speed, -RobotMap.MAX_VELOCITY_INCHES_PER_SECOND, RobotMap.MAX_VELOCITY_INCHES_PER_SECOND);

    return speed;
  }
}
