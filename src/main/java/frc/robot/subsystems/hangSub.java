
//These are just imports
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;


package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;

import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.Timer;


//This class manages the hang subsystem
public class hangSub extends SubsystemBase{
//Initializes the hang motors and sets their state to "resting" or no movement
    static TalonSRX L_hangmotor = new TalonSRX(Constants.CANid.L_hangmotor);
    static TalonSRX R_hangmotor = new TalonSRX(Constants.CANid.R_hangmotor);
    state current = state.RESTING;
    //Timer is used to even out both left and right side motors
    //It also runs in the background as a condition for some methods
    private Timer timer = new Timer();

    private int counter = 0; 
//States mean what the motors are doing
//"RESTING" means no movement
//"DEPLOYING" means it is currently moving
//"DEPLOYING" means it 
private enum state {
    DEPLOYED,
    DEPLOYING,
    RESTING
  }

//This method stops hang on both left and right side
public void StopHang(){
     L_hangmotor.set(ControlMode.PercentOutput, 0);
     R_hangmotor.set(ControlMode.PercentOutput, 0);  
}

//dont fix it if it ain't broke (Meant for programmers)
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
            StopHang(); //saftey measure jju
        }

        }

//command wrappers

/**negative pulls down. */
    public Command C_pullinTime(double time, double power){
      return new InstantCommand(() -> pullinTime(time, power));
        }

   public Command C_StopHang(){
      return new InstantCommand(() -> StopHang());
        }
    }   

