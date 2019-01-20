package frc.robot.Other;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class EncoderBuffer{

    private int index;
    private double[][] buffer;

    public EncoderBuffer(int lengthX){
        buffer = new double[lengthX][2];
        index = 0;
    }

    public void update(WPI_TalonSRX talon){
        buffer[index][0] = talon.getSelectedSensorVelocity() / 1000;
        buffer[index][1] = talon.get();
        index++;
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
        return avgPercSpd / validInputCount;
    }
}