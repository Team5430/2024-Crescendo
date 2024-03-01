package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj.Timer;


public class shooterSub extends SubsystemBase{
   private Timer timer = new Timer();
  
    
   // static TalonSRX shooterMotor = new TalonSRX(Constants.CANid.shooterMotor);
    static TalonSRX shooterMotor = new TalonSRX(Constants.CANid.shooterMotor);
    public shooterSub(){}

    public void motorConfig(){
       //  shooterMotor.configVoltageCompSaturation(.5);
    }

    public void ShooterOutTime(double power, double time){
       // shooterMotor.set(ControlMode.PercentOutput, .5);
       timer.restart();
       while(timer.get() <= Math.abs(time)){
       shooterMotor.set(ControlMode.PercentOutput, power);
       timer.stop();
       }
    }
    public void ShooterOut(){
      shooterMotor.set(ControlMode.PercentOutput, 1);
    }

    public void ShooterInTime(double power, double time){
        //shooterMotor.set(ControlMode.PercentOutput, -.5);
        shooterMotor.set(ControlMode.PercentOutput, -power);
    }
    public void ShooterIn(){
      shooterMotor.set(ControlMode.PercentOutput, -1);
    }
    
    public void ShooterStop(){
       // shooterMotor.set(ControlMode.PercentOutput, 0);
       shooterMotor.set(ControlMode.PercentOutput, 0);
    }
    /** Sets motor to shoot out gamepiece*/
    public Command C_ShooterOut(){
        return new InstantCommand(() -> ShooterOut());
    }
    public Command C_ShooterOutTime(double power, double time){
    return new InstantCommand(() -> ShooterOutTime(power, time));
    }
    
    /**Sets motor to intake gamepiece*/
    public Command C_ShooterIn(){
        return new InstantCommand(() -> ShooterIn());
    }
      public Command C_ShooterInTime(double power, double time){
    return new InstantCommand(() -> ShooterInTime(power, time));
    }

    /**Stops motor*/
    public Command C_ShooterStop(){
        return new InstantCommand(() -> ShooterStop());
   }
 
}
