/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Subsystems.CargoSub;
import frc.robot.Subsystems.ClimberSub;
//import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Subsystems.DriveSub;
import frc.robot.Subsystems.ArmSub;
import frc.robot.Subsystems.HatchSub;
import frc.robot.Subsystems.VisionSub;
import frc.robot.modes.DPadModeManager;
import frc.robot.modes.DriverFailsafeMode;
import frc.robot.modes.DriverPIDMode;
import frc.robot.modes.FakeFailsafeMode;
import frc.robot.modes.GunnerFailsafeMode;
import frc.robot.shuffleboard.ShuffleboardController;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  //private final SendableChooser<String> m_chooser = new SendableChooser<>();

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */

   public static final DriveSub DRIVE_SUB = new DriveSub();
   public static final ArmSub ARM_SUB = new ArmSub();
   public static final CargoSub CARGO_SUB = new CargoSub();
   public static final HatchSub HATCH_SUB = new HatchSub();
   public static final ClimberSub CLIMBER_SUB = new ClimberSub();
   public static final VisionSub VISION_SUB = new VisionSub();
   public static final OI OI = new OI();

  private static Robot robotInstance;
  private ShuffleboardController shuffleboardController;
  private DPadModeManager driverModeMgr;
  private DPadModeManager gunnerModeMgr;

  public Robot(){
    robotInstance = this;
    this.shuffleboardController = new ShuffleboardController();
    initModeManagers();
  }

  @Override
  public void robotInit() {
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    Scheduler.getInstance().run();

    // Update the mode managers
    driverModeMgr.updateDPadValue(OI.getDriverDPadValue());
    gunnerModeMgr.updateDPadValue(OI.getGunnerDPadValue());
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    ARM_SUB.compressor.setClosedLoopControl(true);
  }

  @Override
  public void teleopInit() {
    ARM_SUB.compressor.setClosedLoopControl(true);
  }

  @Override
  public void testInit() {
    shuffleboardController.test();
    ARM_SUB.compressor.setClosedLoopControl(true);
  }

  @Override
  public void disabledInit() {
    // @todo: Put some code in here to decelerate gracefully
    // because this will happen between auto and teleop
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    /*
    SmartDashboard.putNumber("Gyro Yaw", DRIVE_SUB.getYaw());
    SmartDashboard.putNumber("Limelight X Angle", VISION_SUB.getTargetXAngle());
    SmartDashboard.putNumber("Limelight Area", VISION_SUB.getTargetArea());
    SmartDashboard.putNumber("Potentiometer Value", ARM_SUB.getPotentiometerAngle());
    */

    shuffleboardController.periodic();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
    shuffleboardController.periodic();
  }

  /**
   * This function is called periodically while disabled.
   */
  @Override
  public void disabledPeriodic() {
    // @todo: See what we need to do here.
  }

  public static Robot getInstance() {
    return robotInstance;
  }

  public String getDriverModeName() {
    return driverModeMgr.getCurrentMode().getModeName();
  }

  public String getGunnerModeName() {
    return gunnerModeMgr.getCurrentMode().getModeName();
  }

  private void initModeManagers() {
    driverModeMgr = new DPadModeManager(
      new DriverFailsafeMode(),
      new DriverPIDMode(),
      null,
      null,
      frc.robot.OI.DPadValue.Up
      );
    gunnerModeMgr = new DPadModeManager(
      new GunnerFailsafeMode(),
      null,
      null,
      null,
      frc.robot.OI.DPadValue.Up
      );
  }
}
