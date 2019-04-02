/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class DriveWithEncoders extends Command {

  public DriveWithEncoders() {
    requires(Robot.DRIVE_SUB);
  }

  @Override
  protected void execute() {
    int turn = Robot.OI.getEncoderTurnDifferential();
    int speed = Robot.OI.getEncoderSpeed();
    Robot.DRIVE_SUB.PIDDrive(speed, turn);

    /*
    System.out.println(
      "left: " + Robot.DRIVE_SUB.leftMaster.getClosedLoopError() + "|" + ((int)(Robot.DRIVE_SUB.leftMaster.getMotorOutputPercent()*100.0)) +
      " right: " + Robot.DRIVE_SUB.rightMaster.getClosedLoopError() + "|" + ((int)(Robot.DRIVE_SUB.rightMaster.getMotorOutputPercent()*100.0))
    );
    */
  }

  @Override
  protected boolean isFinished() {
    return false;
  }
}
