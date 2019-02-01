/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.Commands.AutoTestCommand;
import frc.robot.Commands.ManualOverrideCommand;

/**
 * Add your docs here.
 */
public class OI {
    private XboxController driver = new XboxController(0);
    private XboxController gunner = new XboxController(1);
    private JoystickButton driverA = new JoystickButton(driver, 1);
    private JoystickButton driverStart = new JoystickButton(driver, 8);

    public OI(){
        driverA.whenPressed(new AutoTestCommand());
        driverStart.whenPressed(new ManualOverrideCommand());
    }


    public double getTurn(){
        return driver.getX(Hand.kRight);
    }

    public double getSpeed(){
        return driver.getY(Hand.kLeft);
    }
}
