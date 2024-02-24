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
    state current = state.REST;
    private Timer timer = new Timer();

    private int counter = 0; 

public void StopHang(){
     L_hangmotor.set(ControlMode.PercentOutput, 0);
     R_hangmotor.set(ControlMode.PercentOutput, 0);  
}
private enum state {
    DEPLOYED,
    DEPLOYING,
    REST
  }

  /* 
  private void HangOneButton(double time, double power){
  current = state.REST;
        if(counter == 0){
            if (current = "REST"){
        
        timer.restart();
    while(timer.get() <= Math.abs(time)){
            L_hangmotor.set(ControlMode.PercentOutput, -power);
            R_hangmotor.set(ControlMode.PercentOutput, power);
           }
            L_hangmotor.set(ControlMode.PercentOutput, 0);
            R_hangmotor.set(ControlMode.PercentOutput, 0);
         timer.stop();
           counter++;
        }
        if (Curr)
        } else {
            StopHang();
        }
  }
  */

  
public void moveHang(double speed){
    L_hangmotor.set(ControlMode.PercentOutput, speed);
    R_hangmotor.set(ControlMode.PercentOutput, -speed);
}
//dont fix it if it ain't broke 
    public void pullinTime(double time, double power){
        if(counter == 0){
        timer.restart();
    while(timer.get() <= Math.abs(time)){
            L_hangmotor.set(ControlMode.PercentOutput, power);
            R_hangmotor.set(ControlMode.PercentOutput, -power);
           }
            L_hangmotor.set(ControlMode.PercentOutput, 0);
            R_hangmotor.set(ControlMode.PercentOutput, 0);
         timer.stop();
           counter++;
        } else {
            StopHang();
        }

        }

     public void releaseinTime(double time, double power){
        if(counter == 0){
        timer.restart();
    while(timer.get() <= Math.abs(time)){
            L_hangmotor.set(ControlMode.PercentOutput, -power);
            R_hangmotor.set(ControlMode.PercentOutput,  power);
           }
            L_hangmotor.set(ControlMode.PercentOutput, 0);
            R_hangmotor.set(ControlMode.PercentOutput, 0);
         timer.stop();
           counter++;
        } else {
            StopHang();
        }       
        //hang only has 1 chance mechanically
    }

/**Positive lets go, negative pulls down. */
    public Command C_pullinTime(double time, double power){
      return new InstantCommand(() -> pullinTime(time, power));
        }
    public Command C_releaseInTime(double time, double power){
        return new InstantCommand(() -> releaseinTime(time, power));
    }
   public Command C_StopHang(){
      return new InstantCommand(() -> StopHang());
        }
    }   