/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Susbsystems;

import frc.robot.RobotMap;
// import frc.robot.Commands.SplitArcadeDriveCommand;
import frc.robot.Commands.SplitArcadeDriveCommandWithEncoders;
import frc.robot.Other.EncoderBuffer;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class DriveSub extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.


  public WPI_TalonSRX leftMaster = new WPI_TalonSRX(RobotMap.LEFT_DRIVE);
  public WPI_TalonSRX leftSlave = new WPI_TalonSRX(RobotMap.LEFT_DRIVE_SLAVE);
  public SpeedControllerGroup left = new SpeedControllerGroup(leftMaster, leftSlave);
  public WPI_TalonSRX rightMaster = new WPI_TalonSRX(RobotMap.RIGHT_DRIVE);
  public WPI_TalonSRX rightSlave = new WPI_TalonSRX(RobotMap.RIGHT_DRIVE_SLAVE);
  public SpeedControllerGroup right = new SpeedControllerGroup(rightMaster, rightSlave);
  public DifferentialDrive drive = new DifferentialDrive(right, left);

  public PigeonIMU gyro = new PigeonIMU(6);
  public double[] gyroArray = new double[3];

  public int leftEncoderClicks = 0;
  public int rightEncoderClicks = 0;
  public EncoderBuffer leftRingBuff = new EncoderBuffer();
  public EncoderBuffer rightRingBuff = new EncoderBuffer();
  public double maxVel;

  public DriveSub(){
    //leftSlave.setInverted(true);
    //rightSlave.setInverted(true);
    leftMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
    rightMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new SplitArcadeDriveCommandWithEncoders());
  }

  public void drive(double left, double right){
    drive.tankDrive(left, right);
  }

  public void driveWithEncoders(double left, double right){
    leftRingBuff.update(leftMaster);
    rightRingBuff.update(rightMaster);

    double leftMax = leftRingBuff.getMaxVel();
    double rightMax = rightRingBuff.getMaxVel();

    if(leftMax > rightMax){
      maxVel = rightMax;
    }else{
      maxVel = leftMax;
    }

    drive.tankDrive(left * (leftMax / maxVel), right * (rightMax / maxVel));
  }

  public double limit(double x){
    if(x > 1.0){
      return 1.0;
    }else if(x < -1.0){
      return -1.0;
    }
    return x;
  }

  public double getYaw(){
    gyro.getYawPitchRoll(gyroArray);
    return gyroArray[0];
  }

}
