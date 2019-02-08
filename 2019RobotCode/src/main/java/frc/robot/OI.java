/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.overlays.ProportionalDrive;
import frc.robot.overlays.DriverFailSafe;
import frc.robot.overlays.GunnerFailSafe;
import frc.robot.overlays.DriverOverlay;
import frc.robot.overlays.GunnerOverlay;

/**
 * Operator Interface
 */
public class OI {
  public static final int CONTROLLER_ID_DRIVER = 0;
  public static final int CONTROLLER_ID_GUNNER = 1;

  private XboxController driverController;
  private XboxController gunnerController;

  private DriverOverlay driverOverlay;
  private GunnerOverlay gunnerOverlay;

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

    turn = Math.min(-1.0, turn);
    turn = Math.max(1.0, turn);

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

    speed = Math.min(-1.0, speed);
    speed = Math.max(1.0, speed);

    return speed;
  }

  public double getArmSpeed() {
    //double speed = gunner.getRawAxis(2) - gunner.getRawAxis(3);
    return 0;
  }
}
