/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.overlays.ProportionalDrive;
import frc.robot.overlays.VelocityDrive;
import frc.robot.Other.Utility;
import frc.robot.overlays.ControlOverlay;

/**
 * Operator Interface
 */
public class OI {
  public static final int CONTROLLER_ID_DRIVER = 0;
  public static final int CONTROLLER_ID_GUNNER = 1;

  // D-pad values (probably move this somewhere else)
  public enum DPadValue {
    Up(0),
    Right(1),
    Down(2),
    Left(3),
    Unpressed(4);

    public final int value;

    // Convert a POV value from GenericHID to the enum.
    // Snaps to a quadrant in case a diagonal is pressed.
    public static DPadValue fromPOV(int povVal) {
      if(povVal < 0) {
        return Unpressed;
      }
      if(povVal < 45 || povVal >= 315) {
          return Up;
      }
      if(povVal < 135) {
        return Right;
      }
      if(povVal < 225) {
        return Down;
      }
      return Left;
    }

    private DPadValue(int aValue) {
      value = aValue;
    }
  }

  private XboxController driverController;
  private XboxController gunnerController;

  private ControlOverlay driverOverlay;
  private ControlOverlay gunnerOverlay;

  public OI() {
    this.driverController = new XboxController(CONTROLLER_ID_DRIVER);
    this.gunnerController = new XboxController(CONTROLLER_ID_GUNNER);
  }

  public double getProportionalTurn(){
    double turn = 0;

    if (driverOverlay instanceof ProportionalDrive) {
      turn += ((ProportionalDrive) driverOverlay).getTurn();
    }
    if (gunnerOverlay instanceof ProportionalDrive) {
      turn += ((ProportionalDrive) gunnerOverlay).getTurn();
    }

    turn = Utility.clamp(turn, -1.0, 1.0);

    return turn;
  }

  public double getProportionalSpeed() {
    double speed = 0;

    if (driverOverlay instanceof ProportionalDrive) {
      speed += ((ProportionalDrive) driverOverlay).getSpeed();
    }
    if (gunnerOverlay instanceof ProportionalDrive) {
      speed += ((ProportionalDrive) gunnerOverlay).getSpeed();
    }

    speed = Utility.clamp(speed, -1.0, 1.0);

    return speed;
  }

  /**
   *
   * @return The desired turn rate in degrees/sec
   */
  public double getGyroBasedTurn() {
    double turn = 0.0;

    if(driverOverlay instanceof VelocityDrive) {
      turn += ((VelocityDrive) driverOverlay).getTurnDegreesPerSecond();
    }
    if(gunnerOverlay instanceof VelocityDrive) {
      turn += ((VelocityDrive) gunnerOverlay).getTurnDegreesPerSecond();
    }

    turn = Utility.clamp(turn, -RobotMap.MAX_TURN_RATE_DEGREES_PER_SECOND, RobotMap.MAX_TURN_RATE_DEGREES_PER_SECOND);

    return turn;
  }

  /**
   * @return The desired speed in inches/sec
   */
  public double getEncoderBasedSpeed() {
    double speed = 0.0;

    if(driverOverlay instanceof VelocityDrive) {
      speed += ((VelocityDrive) driverOverlay).getSpeedInchesPerSecond();
    }
    if(gunnerOverlay instanceof VelocityDrive) {
      speed += ((VelocityDrive) gunnerOverlay).getSpeedInchesPerSecond();
    }

    speed = Utility.clamp(speed, -RobotMap.MAX_VELOCITY_INCHES_PER_SECOND, RobotMap.MAX_VELOCITY_INCHES_PER_SECOND);

    return speed;
  }

  public DPadValue getDriverDPadValue() {
    return DPadValue.fromPOV(driverController.getPOV(0));
  }

  public DPadValue getGunnerDPadValue() {
    return DPadValue.fromPOV(gunnerController.getPOV(0));
  }

  public XboxController getDriverController() {
    return driverController;
  }

  public XboxController getGunnerController() {
    return gunnerController;
  }

  public boolean isArmLevelSelect() {
    return gunnerController.getBButtonPressed();
  }

  public void setDriverOverlay(ControlOverlay overlay) {
    if (this.driverOverlay != null) {
      this.driverOverlay.deactivate();
    }

    driverOverlay = overlay;

    if (this.driverOverlay != null) {
      this.driverOverlay.activate();
    }
  }

  public void setGunnerOverlay(ControlOverlay overlay) {
    if (this.gunnerOverlay != null) {
      this.gunnerOverlay.deactivate();
    }

    this.gunnerOverlay = overlay;

    if (this.gunnerOverlay != null) {
      this.gunnerOverlay.activate();
    }
  }
}
