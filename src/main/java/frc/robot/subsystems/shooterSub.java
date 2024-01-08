package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class shooterSub extends SubsystemBase {

private enum state{
    RESTING,
    OUTTAKING,
    INTAKING
}

static state current = state.RESTING;
// Intake and shooter are both the same thing
public shooterSub(){}
// Shows the right and left motor  
static TalonSRX L_motor = new TalonSRX(Constants.CANid.L_shooterMotor);
static TalonSRX R_motor = new TalonSRX(Constants.CANid.R_shooterMotor);

    public static void Intake(){
        current = state.INTAKING;
        L_motor.set(ControlMode.PercentOutput, .6);
        R_motor.set(ControlMode.PercentOutput, -.6);
    }

    public static void Stop(){
        current = state.RESTING;
        L_motor.set(ControlMode.PercentOutput, 0);
        R_motor.set(ControlMode.PercentOutput, 0); 
    }

    //aka shooting
    public static void Outake(){
        current = state.OUTTAKING;  
        L_motor.set(ControlMode.PercentOutput, 0.5);
        R_motor.set(ControlMode.PercentOutput, 0.5);
    }

/**Returns current State as a String */
    public String getState(){
        return current.toString();
    }  
}

