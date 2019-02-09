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
import frc.robot.overlays.DriverFailSafe;
import frc.robot.overlays.GunnerFailSafe;
import frc.robot.Other.Utility;
import frc.robot.overlays.ControlOverlay;

/**
 * Operator Interface
 */
public class OI {
  public static final int CONTROLLER_ID_DRIVER = 0;
  public static final int CONTROLLER_ID_GUNNER = 1;

  private XboxController driverController;
  private XboxController gunnerController;

  private ControlOverlay driverOverlay;
  private ControlOverlay gunnerOverlay;

  public OI() {
    this.driverController = new XboxController(CONTROLLER_ID_DRIVER);
    this.gunnerController = new XboxController(CONTROLLER_ID_GUNNER);

    this.driverOverlay = new DriverFailSafe(driverController);
    this.gunnerOverlay = new GunnerFailSafe(gunnerController);
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

  public double getArmSpeed() {
    //double speed = gunner.getRawAxis(2) - gunner.getRawAxis(3);
    return 0;
  }
}
