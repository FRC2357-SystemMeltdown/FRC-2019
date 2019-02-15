package frc.robot.Other;

import frc.robot.RobotMap;;

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
  
}