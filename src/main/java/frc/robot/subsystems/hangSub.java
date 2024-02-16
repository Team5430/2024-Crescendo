package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;


public class hangSub extends SubsystemBase{

    static TalonSRX hangmotor = new TalonSRX(Constants.CANid.hangmotor);
   // static TalonSRX hangmotor = new TalonSRX(15);
    public state current = state.RESTING;
    private Timer timer = new Timer();

        private enum state{
        RESTING,
        EXTENDED,
    }

public void extendclimbertimer(){
    timer.restart();
    System.out.println("A button pressed");
    if(current == state.RESTING){
        while(timer.get() <= 5){
            hangmotor.set(ControlMode.PercentOutput, .5);
        }
        current = state.EXTENDED;
        
    }
    else{
        while(timer.get() <= 5){
                    hangmotor.set(ControlMode.PercentOutput, -.5);
                    
                }
            current = state.RESTING;
    }
    System.out.println(timer.get());
    }

    public Command C_ExtendClimberTimer(){
      return new InstantCommand(() -> extendclimbertimer());
        }

    public Command C_Doe(){
      return new InstantCommand(() -> System.out.println("AHOY AHOY COMMANDS AIN'T WORKING"));
        }
        
    }   

