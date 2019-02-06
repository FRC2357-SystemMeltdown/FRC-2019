/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

/**
 * Operator Interface
 */
public class OI {
  public static final int CONTROLLER_ID_DRIVER = 0;
  public static final int CONTROLLER_ID_GUNNER = 1;

  public XboxController driver = new XboxController(OI.CONTROLLER_ID_DRIVER);
  public XboxController gunner = new XboxController(OI.CONTROLLER_ID_GUNNER);

  public double getTurn(){
    return driver.getX(Hand.kRight);
  }

  public double getSpeed(){
    return driver.getY(Hand.kLeft);
  }

  public double getArmSpeed(){
    double speed = gunner.getRawAxis(2) - gunner.getRawAxis(3);
    return speed;
  }
}
