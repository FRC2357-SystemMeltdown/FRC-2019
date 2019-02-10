/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class GyroPID extends PIDSubsystem {

  private long lastTime;
  private double lastPosition;
  public double output;

  public GyroPID() {
    // Intert a subsystem name and PID values here
    super(RobotMap.PID_P_GYRO, RobotMap.PID_I_GYRO, RobotMap.PID_D_GYRO);
    this.lastTime = 0l;
    this.lastPosition = 0.0;
    getPIDController().setContinuous(false);
    setAbsoluteTolerance(1.0);
    setOutputRange(-1.0, 1.0);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  @Override
  protected double returnPIDInput() {
    // Return your input value for the PID loop
    // e.g. a sensor, like a potentiometer:
    // yourPot.getAverageVoltage() / kYourMaxVoltage;
    return getTurnRateDegreesPerSecond();
  }

  @Override
  protected void usePIDOutput(double output) {
    // Use output to drive your system, like a motor
    // e.g. yourMotor.set(output);
    this.output = output;
  }

  public double getTurnRateDegreesPerSecond() {
    long currentTime = System.currentTimeMillis();
    long deltaTime = currentTime - lastTime;
    lastTime = currentTime;

    double currentPosition = Robot.DRIVE_SUB.getYaw();
    double deltaPosition = currentPosition - lastPosition;
    lastPosition = currentPosition;

    double velocityDegreesPerMilisecond = deltaPosition / deltaTime;
    double velocityDegreesPerSecond = velocityDegreesPerMilisecond * RobotMap.MILLISECONDS_PER_SECOND;
    return velocityDegreesPerSecond;
  }
}
