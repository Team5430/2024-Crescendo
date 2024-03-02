package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.Timer;



public class hangSub extends SubsystemBase{

    static TalonSRX L_hangmotor = new TalonSRX(Constants.CANid.L_hangmotor);
    static TalonSRX R_hangmotor = new TalonSRX(Constants.CANid.R_hangmotor);
    state current = state.RESTING;
    private Timer timer = new Timer();

    private int counter = 0; 

private enum state {
    DEPLOYED,
    DEPLOYING,
    RESTING
  }


public void StopHang(){
     L_hangmotor.set(ControlMode.PercentOutput, 0);
     R_hangmotor.set(ControlMode.PercentOutput, 0);  
}

//dont fix it if it ain't broke 
    public void pullinTime(double time, double power){
        if(counter == 0){
        timer.restart();
//abs value as time cannot go negative
    while(timer.get() <= Math.abs(time)){
            L_hangmotor.set(ControlMode.PercentOutput, power);
            R_hangmotor.set(ControlMode.PercentOutput, power);
           }
            StopHang();
         timer.stop();
        //counter added as to prevent any other running as physically limited
           counter++;
        } else {
            StopHang();
        }

        }

/**negative pulls down. */
    public Command C_pullinTime(double time, double power){
      return new InstantCommand(() -> pullinTime(time, power));
        }

   public Command C_StopHang(){
      return new InstantCommand(() -> StopHang());
        }
    }   