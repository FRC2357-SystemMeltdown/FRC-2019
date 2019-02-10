package frc.robot.overlays;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Commands.IntakeInCommand;
import frc.robot.Commands.IntakeOutCommand;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.RobotMap;

public class GunnerFailSafe extends GunnerCreepDrive implements ProportionalDrive {
  public static final double TURN_FACTOR = RobotMap.GUNNER_PROPORTION;
  public static final double SPEED_FACTOR = RobotMap.GUNNER_PROPORTION;

  private final static Button INTAKE_IN_BUTTON = Button.A;
  private final static Button INTAKE_OUT_BUTTON = Button.B;

  private IntakeInCommand intakeInCommand;
  private IntakeOutCommand intakeOutCommand;

  public GunnerFailSafe(XboxController controller) {
    super(controller);

    intakeInCommand = new IntakeInCommand();
    intakeOutCommand = new IntakeOutCommand();
  }

  public double getTurn() {
      return controller.getX(Hand.kRight) * TURN_FACTOR;
  }

  public double getSpeed() {
      return controller.getY(Hand.kLeft) * SPEED_FACTOR;
  }
  
  @Override
  protected void handleButtonInputs()
  {
    if(wasButtonPressed(INTAKE_IN_BUTTON))
    {
      intakeInCommand.start();
    }
    if(wasButtonReleased(INTAKE_IN_BUTTON))
    {
      intakeInCommand.cancel();
    }

    if(wasButtonPressed(INTAKE_OUT_BUTTON))
    {
      intakeOutCommand.start();
    }
    if(wasButtonReleased(INTAKE_OUT_BUTTON))
    {
      intakeOutCommand.cancel();
    }
  }

}
