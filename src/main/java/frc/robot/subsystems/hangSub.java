package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;

import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.team5430.motors.RoboTires;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;


public class hangSub extends SubsystemBase{
    private enum state{
        RESTING,
        EXTENDED,
    }
    static TalonFX hangmotor;
    public state current = state.RESTING;
    private Timer timer = new Timer();

public void extendclimbertimer(){
    if(current == state.RESTING){
        while(timer.get() <= 5){
            hangmotor.set(ControlMode.PercentOutput, .5);
            current = state.EXTENDED;
        }
    else{
        while(timer.get() <= 5){
                    hangmotor.set(ControlMode.PercentOutput, -.5);
                    current = state.EXTENDED;
                }
    }
    

    }    
}

