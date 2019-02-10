package frc.robot.Other;

public class Utility{
    public static double clamp(double input, double min, double max){
        double value = input;
        value = Math.max(value, -1.0);
        value = Math.min(value, 1.0);
        return value;
    }
}