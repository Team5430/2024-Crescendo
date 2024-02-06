package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;


public class shooterSub extends SubsystemBase{
    static TalonSRX shooterMotor = new TalonSRX(Constants.CANid.shooterMotor);

    public shooterSub(){}

    public void motorConfig(){
         shooterMotor.configVoltageCompSaturation(.5);
    }

    public void ShooterOut(){
        shooterMotor.set(ControlMode.PercentOutput, .5);
    }

    public void ShooterIn(){
        shooterMotor.set(ControlMode.PercentOutput, -.5);
    }
    
    public void ShooterStop(){
        shooterMotor.set(ControlMode.PercentOutput, 0);
    }

    /**Sets motor to shoot out gamepiece */
    public Command C_ShooterOut(){
        return new InstantCommand(() -> ShooterOut());
    }
    
    /**Sets motor to intake gamepiece*/
    public Command C_ShooterIn(){
        return new InstantCommand(() -> ShooterIn());
    }

    /**Stops motor*/
    public Command C_ShooterStop(){
        return new InstantCommand(() -> ShooterStop());
    }

}