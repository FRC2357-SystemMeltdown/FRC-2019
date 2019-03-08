/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.Subsystems.CargoSub;
import frc.robot.Subsystems.ClimberSub;
import frc.robot.Subsystems.DriveSub;
import frc.robot.Subsystems.ArmSub;
import frc.robot.Subsystems.HatchSub;
import frc.robot.Subsystems.VisionSub;
import frc.robot.modes.DPadModeManager;
import frc.robot.modes.DriverModeManager;
import frc.robot.modes.GunnerModeManager;
import frc.robot.modes.ModeManager;
import frc.robot.shuffleboard.ShuffleboardController;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
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

  private boolean hatchLeftLimitClosed;
  private boolean hatchRightLimitClosed;

  public Robot(){
    robotInstance = this;
    this.shuffleboardController = new ShuffleboardController();
    driverModeMgr = new DriverModeManager();
    gunnerModeMgr = new GunnerModeManager();
  }

  @Override
  public void robotInit() {
    hatchLeftLimitClosed = HATCH_SUB.isLeftLimitClosed();
    hatchRightLimitClosed = HATCH_SUB.isRightLimitClosed();
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
    if (! OI.isArmLevelSelect()) {
      driverModeMgr.updateDPadValue(OI.getDriverDPadValue());
      gunnerModeMgr.updateDPadValue(OI.getGunnerDPadValue());
    }

    // If the hatch limit switch is closed, reset the encoder
    if(HATCH_SUB.isLeftLimitClosed() && !hatchLeftLimitClosed) {
      HATCH_SUB.resetLeftEncoder();
    }
    if(HATCH_SUB.isRightLimitClosed() && !hatchRightLimitClosed) {
      HATCH_SUB.resetRightEncoder();
    }
    hatchLeftLimitClosed = HATCH_SUB.isLeftLimitClosed();
    hatchRightLimitClosed = HATCH_SUB.isRightLimitClosed();
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
    shuffleboardController.drive();
    ARM_SUB.compressor.setClosedLoopControl(true);
  }

  @Override
  public void teleopInit() {
    shuffleboardController.drive();
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
    shuffleboardController.periodic();
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
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

  public ModeManager getDriverModeManager() {
    return driverModeMgr;
  }

  public ModeManager getGunnerModeManager() {
    return gunnerModeMgr;
  }
}
