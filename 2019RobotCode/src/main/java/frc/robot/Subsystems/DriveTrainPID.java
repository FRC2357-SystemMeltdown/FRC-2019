/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class DriveTrainPID extends PIDSubsystem {
  /**
   * Add your docs here.
   */

  private WPI_TalonSRX talon;
  private long lastTime;
  private int lastPosition;
  public double output;

  public DriveTrainPID(double pp, double ii, double dd, WPI_TalonSRX talon) {
    // Intert a subsystem name and PID values here
    super(pp, ii , dd, RobotMap.DRIVE_TRAIN_SAMPLE_PERIOD);
    // Use these to get going:
    // setSetpoint() - Sets where the PID controller should move the system
    // to
    // enable() - Enables the PID controller.

    this.talon = talon;
    this.lastTime = System.currentTimeMillis();
    this.lastPosition = talon.getSelectedSensorPosition();
    setAbsoluteTolerance(.2);
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
    return getVelocityInchesPerSecond();
  }

  @Override
  protected void usePIDOutput(double output) {
    // Use output to drive your system, like a motor
    // e.g. yourMotor.set(output);
    this.output = output;
  }

  public double getVelocityRotationsPerSecond() {
    long currentTime = System.currentTimeMillis();
    long deltaTime = currentTime - lastTime;
    lastTime = currentTime;

    int currentPosition = talon.getSelectedSensorPosition();
    int deltaPosition = currentPosition - lastPosition;
    lastPosition = currentPosition;

    double velocityTicksPerMilisecond = deltaPosition / deltaTime;
    double velocityRotationsPerMilisecond = velocityTicksPerMilisecond / RobotMap.ENCODER_TICKS_PER_ROTATION;
    double velocityRotationsPerSecond = velocityRotationsPerMilisecond * RobotMap.MILLISECONDS_PER_SECOND;
    return velocityRotationsPerSecond;
  }

  public double getVelocityInchesPerSecond() {
      double velocityInchesPerSecond = getVelocityRotationsPerSecond() * RobotMap.WHEEL_CIRCUMFERENCE;
      return velocityInchesPerSecond;
  }
}
