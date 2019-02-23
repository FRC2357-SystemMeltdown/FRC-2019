package frc.robot.Other;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import frc.robot.RobotMap;

public class Utility{
    public static double clamp(double input, double min, double max){
        double value = input;
        value = Math.max(value, -1.0);
        value = Math.min(value, 1.0);
        return value;
    }

    public static double getNearestArmPresetAbove(double angle){
      if(angle < RobotMap.ARM_CARGO_PICKUP_ANGLE) {
        return RobotMap.ARM_CARGO_PICKUP_ANGLE;
      } else if(angle < RobotMap.ARM_LOW_GOAL_ANGLE) {
        return RobotMap.ARM_LOW_GOAL_ANGLE;
      } else if(angle < RobotMap.ARM_MID_GOAL_ANGLE) {
        return RobotMap.ARM_MID_GOAL_ANGLE;
      } else if(angle < RobotMap.ARM_HIGH_GOAL_ANGLE) {
        return RobotMap.ARM_HIGH_GOAL_ANGLE;
      }
      return 0.0;
    }
  
    public static double getNearestArmPresetBelow(double angle){
      if(angle > RobotMap.ARM_HIGH_GOAL_ANGLE) {
        return RobotMap.ARM_HIGH_GOAL_ANGLE;
      } else if(angle > RobotMap.ARM_MID_GOAL_ANGLE) {
        return RobotMap.ARM_MID_GOAL_ANGLE;
      } else if(angle > RobotMap.ARM_LOW_GOAL_ANGLE) {
        return RobotMap.ARM_LOW_GOAL_ANGLE;
      } else if(angle > RobotMap.ARM_CARGO_PICKUP_ANGLE) {
        return RobotMap.ARM_CARGO_PICKUP_ANGLE;
      }
      return 0.0;
    }

  /**
   *
   * @param talon The talon SRX to config
   * @param pid PID values to set
   */
  public static void configTalonPID(WPI_TalonSRX talon, PIDValues pid) {
    configTalonPID(talon, 0, pid);
  }

  /**
   *
   * @param talon The talon SRX to config
   * @param slotIdx Index of the profile slot to configure
   * @param pid PID values to set
   */
  public static void configTalonPID(WPI_TalonSRX talon, int slotIdx, PIDValues pid) {
    talon.config_kP(slotIdx, pid.kp);
    talon.config_kI(slotIdx, pid.ki);
    talon.config_kD(slotIdx, pid.kd);
    talon.config_kF(slotIdx, pid.kf);
    talon.config_IntegralZone(slotIdx, pid.izone);
  }    
}