/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Subsystems;

import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.Commands.EncoderBasedDrive;
import frc.robot.Commands.ProportionalDriveCommand;
import frc.robot.Other.PIDValues;
import frc.robot.Other.Utility;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.RemoteSensorSource;
import com.ctre.phoenix.motorcontrol.SensorTerm;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.ctre.phoenix.sensors.PigeonIMU_StatusFrame;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * Add your docs here.
 */
public class DriveSub extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public WPI_TalonSRX leftMaster = new WPI_TalonSRX(RobotMap.CAN_ID_LEFT_DRIVE);
  public WPI_TalonSRX leftSlave = new WPI_TalonSRX(RobotMap.CAN_ID_LEFT_DRIVE_SLAVE);
  public WPI_TalonSRX rightMaster = new WPI_TalonSRX(RobotMap.CAN_ID_RIGHT_DRIVE);
  public WPI_TalonSRX rightSlave = new WPI_TalonSRX(RobotMap.CAN_ID_RIGHT_DRIVE_SLAVE);
  public DifferentialDrive drive;// = new DifferentialDrive(leftMaster, rightMaster);

  private PigeonIMU gyro = new PigeonIMU(RobotMap.CAN_ID_PIGEON_IMU);
  private double[] yawPitchRoll = new double[RobotMap.GYRO_AXIS_TOTAL];

  private static final int DRIVE_PID_SLOT = 0;
  private static final int TURN_PID_SLOT = 1;

  public DriveSub(){
    leftSlave.follow(leftMaster);
    rightSlave.follow(rightMaster);
    rightSlave.setInverted(true);

    /*
    This sets up the left and right Talons to use their encoders and the Pigeon yaw.

    The right Talon runs two PIDs. PID0 uses the average of the left and right
    encoder counts as feedback, and PID1 uses the Pigeon yaw as feedback. This
    gives two PID outputs, PID0 + PID1 and PID0 - PID1. We assign one to each Talon
    master.
    */

    rightMaster.configFactoryDefault();

    // The left feedback sensor will be transmitted to the right controller
    leftMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);

    // set right remote0 to the left encoder
    rightMaster.configRemoteFeedbackFilter(
      leftMaster.getDeviceID(),
      RemoteSensorSource.TalonSRX_SelectedSensor,
      0
    );

    // set the right feedback to (remote0 - its encoder)/2
    rightMaster.configSensorTerm(SensorTerm.Diff0, FeedbackDevice.RemoteSensor0);
    rightMaster.configSensorTerm(SensorTerm.Diff1, FeedbackDevice.QuadEncoder);
    rightMaster.configSelectedFeedbackSensor(FeedbackDevice.SensorDifference);
    rightMaster.configSelectedFeedbackCoefficient(0.5);

    rightMaster.setSensorPhase(true);
    rightMaster.setInverted(true);
    rightMaster.configAuxPIDPolarity(false);

    // set right aux PID feedback to the Pigeon yaw
    rightMaster.configRemoteFeedbackFilter(
      gyro.getDeviceID(),
      RemoteSensorSource.Pigeon_Yaw,
      1
    );
    rightMaster.configSelectedFeedbackSensor(FeedbackDevice.RemoteSensor1, 1, 0);
    rightMaster.configSelectedFeedbackCoefficient(1, 1, 0);

    rightMaster.setStatusFramePeriod(StatusFrame.Status_12_Feedback1, 5);
    rightMaster.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 20);
    rightMaster.setStatusFramePeriod(StatusFrame.Status_14_Turn_PIDF1, 20);
    leftMaster.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 5);
    gyro.setStatusFramePeriod(PigeonIMU_StatusFrame.CondStatus_9_SixDeg_YPR, 5);

    // configure PIDs
    Utility.configTalonPID(leftMaster, RobotMap.PID_LEFT_DRIVE);
    Utility.configTalonPID(rightMaster, DRIVE_PID_SLOT, RobotMap.PID_RIGHT_DRIVE);
    Utility.configTalonPID(rightMaster, TURN_PID_SLOT, RobotMap.PID_GYRO);
    rightMaster.selectProfileSlot(DRIVE_PID_SLOT, 0);
    rightMaster.selectProfileSlot(TURN_PID_SLOT, 1);

    rightMaster.configClosedloopRamp(5);

    resetSensors();
  }

  private void resetSensors() {
    // reset sensors
    rightMaster.getSensorCollection().setQuadraturePosition(0, 0);
    leftMaster.getSensorCollection().setQuadraturePosition(0, 0);
    gyro.setYaw(0);
    gyro.setAccumZAngle(0);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new EncoderBasedDrive()); // ProportionalDriveCommand());
  }

  /**
   *
   * @param speed The desired speed in inches/sec
   * @param turn The desired turn rate in degrees/sec
   */
  public void PIDDrive(double speed, double turn) {
    //double speedTicksPer100ms = Robot.OI.getDriverController().getAButton() ? 400 : 200;// calcMotorControlSpeed(speed);
    //double speedTicksPer100ms = (Robot.OI.getDriverController().getAButton() ? 400 : 200) * -Robot.OI.getDriverController().getY(Hand.kLeft);
    // double speedTicksPer100ms = calcMotorControlSpeed(speed);

    // // get the gyro yaw
    double heading = rightMaster.getSelectedSensorPosition(1);
    double headingDelta = turn * RobotMap.GYRO_UNITS_PER_ROTATION / 360.0 * Robot.getInstance().getPeriod();

    // // set the PID points on the right master
    // rightMaster.set(ControlMode.Velocity, speedTicksPer100ms, DemandType.AuxPID, heading + headingDelta);
    // //leftMaster.set(ControlMode.PercentOutput, 1);

    if(Robot.OI.getDriverController().getAButton()) {
      resetSensors();
    }

    //speed = Utility.clamp(-Robot.OI.getDriverController().getY(Hand.kLeft), -1, 1);
    turn = Utility.clamp(Robot.OI.getDriverController().getX(Hand.kRight), -1, 1);
    rightMaster.set(ControlMode.Velocity, calcMotorControlSpeed(speed), DemandType.AuxPID, 1024 * turn); // Robot.OI.getDriverController().getBButton() ? 1024 : 0 );
    // tell the left master to use the right's conjugate PID output
    leftMaster.follow(rightMaster, FollowerType.AuxOutput1);
    
  }

  /**
   *
   * @return The gyro's current yaw value in degrees
   */
  public double getYaw() {
    gyro.getYawPitchRoll(yawPitchRoll);
    double yaw = yawPitchRoll[RobotMap.GYRO_AXIS_YAW];
    if(yaw >= 180){
      yaw -= 360;
    }
    if(yaw <= -180){
      yaw += 360;
    }
    return yaw;
  }

  /**
   * @param speedInchesPerSecond The desired speed in inches/sec
   * @return The speed converted to encoder ticks/100ms
   */
  private double calcMotorControlSpeed(double speedInchesPerSecond) {
    double speedInchesPer100ms = speedInchesPerSecond * 100 / RobotMap.MILLISECONDS_PER_SECOND;
    double speedRotationsPer100ms = speedInchesPer100ms / RobotMap.WHEEL_CIRCUMFERENCE_INCHES;
    double speedTicksPer100ms = speedRotationsPer100ms * RobotMap.ENCODER_TICKS_PER_ROTATION;
    return speedTicksPer100ms;
  }
}
