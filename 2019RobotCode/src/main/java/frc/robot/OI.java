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
import frc.robot.Commands.AutoHatchLoadingStationCommand;
import frc.robot.Commands.AutoModePreviewCommand;
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

  private int lastEncoderSpeed;
  private boolean autoModePreview;
  private DriverControls driverControls;
  private GunnerControls gunnerControls;

  public OI() {
    this.lastEncoderSpeed = 0;
    this.autoModePreview = false;
    this.driverControls = new DriverControls(new XboxController(CONTROLLER_ID_DRIVER));
    this.gunnerControls = new GunnerControls(new XboxController(CONTROLLER_ID_GUNNER));

    gunnerControls.cargoRollerButton.whileHeld(new CargoRollerCommand(gunnerControls));

    gunnerControls.armAdjustDownButton.whileHeld(new ArmAdjustCommand(Direction.DOWN));
    gunnerControls.armAdjustUpButton.whileHeld(new ArmAdjustCommand(Direction.UP));

    gunnerControls.armCycleDownTrigger.whenActive(new ArmPresetCycleCommand(Direction.DOWN));
    gunnerControls.armCycleUpTrigger.whenActive(new ArmPresetCycleCommand(Direction.UP));

    gunnerControls.autoModeButton.whenPressed(new AutoModePreviewCommand(true));
    gunnerControls.autoModeButton.whenReleased(new AutoModePreviewCommand(false));

    gunnerControls.autoHatchLoadingStationTrigger.whileActive(new AutoHatchLoadingStationCommand());
  }

  public boolean isAutoModePreview() {
    return autoModePreview;
  }

  public void setAutoModePreview(boolean autoModePreview) {
    if (this.autoModePreview != autoModePreview) {
      this.autoModePreview = autoModePreview;

      if (autoModePreview) {
        Robot.VISION_SUB.setPipeline(PipelineIndex.VISION_TARGET);
      } else {
        Robot.VISION_SUB.setPipeline(PipelineIndex.HUMAN_VIEW);
      }
    }
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
  public int getEncoderTurnDifferential() {
    int turn = 0;

    turn += driverControls.getEncoderTurnDifferential();
    turn += gunnerControls.getEncoderTurnDifferential();
    turn = Utility.clamp(turn, -RobotMap.DRIVER_ENCODER_TURN_RATE, RobotMap.DRIVER_ENCODER_TURN_RATE);

    return turn;
  }

  @Override
  public int getEncoderSpeed() {
    int speed = 0;

    speed += driverControls.getEncoderSpeed();
    speed += gunnerControls.getEncoderSpeed();
    speed = Utility.clamp(speed, -RobotMap.DRIVER_ENCODER_SPEED, RobotMap.DRIVER_ENCODER_SPEED);

    // Limit the input speed on forward motion (to avoid tipping)
    double limitFactor = RobotMap.DRIVER_ENCODER_MAX_FORWARD_LIMIT_FACTOR;

    // Default is max from zero forward (reverse accel doesn't matter)
    int maxDiff = RobotMap.DRIVER_ENCODER_MAX_DIFF;

    if (speed - lastEncoderSpeed > maxDiff) {
      // Forward accel is too fast.
      int max = maxDiff;

      if (lastEncoderSpeed > 0) {
        // Limit forward acceleration.
        max = (int)(lastEncoderSpeed * limitFactor);
      } else if (lastEncoderSpeed < 0) {
        // Limit reverse deceleration.
        max = (int)(lastEncoderSpeed / limitFactor);
        max = max > -maxDiff ? 0 : max;
      }

      if (speed > max) {
        System.out.println("limit: " + speed + " to " + max + " (lastEncoderSpeed=" + lastEncoderSpeed + ")");
        speed = max;
      }
    }

    lastEncoderSpeed = speed;
    return speed;
  }
}
