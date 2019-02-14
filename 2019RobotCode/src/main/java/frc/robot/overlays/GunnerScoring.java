package frc.robot.overlays;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.RobotMap;
import frc.robot.Commands.ChangeArmStateCommand;
import frc.robot.Other.XboxRaw;

public class GunnerScoring extends GunnerCreepDrive {

  private ChangeArmStateCommand changeStateHigh;
  private ChangeArmStateCommand changeStateMid;
  private ChangeArmStateCommand changeStateLow;
  private ChangeArmStateCommand changeStateMin;

  private JoystickButton changeStateHighButton;
  private JoystickButton changeStateMidButton;
  private JoystickButton changeStateLowButton;
  private JoystickButton changeStateMinButton;

  public GunnerScoring(XboxController controller) {
    super(controller);

    changeStateHigh = new ChangeArmStateCommand(RobotMap.ARM_HIGH_GOAL_ANGLE);
    changeStateMid = new ChangeArmStateCommand(RobotMap.ARM_MID_GOAL_ANGLE);
    changeStateLow = new ChangeArmStateCommand(RobotMap.ARM_LOW_GOAL_ANGLE);
    changeStateMin = new ChangeArmStateCommand(RobotMap.ARM_MIN_ANGLE);

    changeStateHighButton = new JoystickButton(controller, XboxRaw.Y.value);
    changeStateHighButton.whenPressed(changeStateHigh);

    changeStateMidButton = new JoystickButton(controller, XboxRaw.X.value);
    changeStateMidButton.whenPressed(changeStateMid);

    changeStateLowButton = new JoystickButton(controller, XboxRaw.A.value);
    changeStateLowButton.whenPressed(changeStateLow);

    changeStateMinButton = new JoystickButton(controller, XboxRaw.Start.value);
    changeStateMinButton.whenPressed(changeStateMin);
  }
}