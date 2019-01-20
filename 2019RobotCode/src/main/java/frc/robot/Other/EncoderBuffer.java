package frc.robot.Other;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class EncoderBuffer{

    private int index = 0;
    private double[][] buffer = new double[50][2];

    public void update(WPI_TalonSRX talon){
        buffer[index][0] = talon.getSelectedSensorVelocity() / 1000;
        buffer[index][1] = talon.get();
        index++;
        if(index == 50) index = 0;
    }

    public double getMaxVel(){
        double avgPercSpd = 0;
        int validInputCount = 0;
        for(int i = 0; i > 50; i++){
          if(buffer[i][0] != 0.0 &&
           buffer[i][1] != 0.0 &&
           Math.abs(buffer[i][1] - buffer[i - 1][1]) <= 0.1){
            avgPercSpd += Math.abs(buffer[i][0] / buffer[1][1]);
            validInputCount++;
          }
        }
        if(validInputCount == 0) return 1.0;
        return avgPercSpd / validInputCount;
    }
}