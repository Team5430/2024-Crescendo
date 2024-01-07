package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;

import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.team5430.motors.RoboTires;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class driveTrain extends SubsystemBase {
    
    final static RoboTires backLeftMotor = new RoboTires(Constants.CANid.backLeftMotor);
    final static RoboTires frontLeftMotor = new RoboTires(Constants.CANid.frontLeftMotor);
    final static RoboTires backRightMotor = new RoboTires(Constants.CANid.backRightMotor);
    final static RoboTires frontRightMotor = new RoboTires(Constants.CANid.frontRightMotor);
    //grouping
    final static MotorControllerGroup Lgroup = new MotorControllerGroup(backLeftMotor, frontLeftMotor);
    final static MotorControllerGroup Rgroup = new MotorControllerGroup(backRightMotor, frontRightMotor);
    static SupplyCurrentLimitConfiguration configTalonCurrent = new SupplyCurrentLimitConfiguration(true,55,0,0);

 
 //when a button is pressed the solenoid goes up and when you press another button it goes down.
   public static DoubleSolenoid driveshifter = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 0, 1); //change to right pneumatic module
 //When we press a button the flag goes up and when the second button is pressed it retacts.
     public static DoubleSolenoid Pushypush = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 2, 3);//change to right pneumatic module
 
   //When a button is held down it will drive faster
     public static Command driveToggle() {      
         return new InstantCommand(
             () -> driveshifter.toggle()
     );
 }
 
     //It sets the drive value forward
     public static void pneumaticENABLE(){
         driveshifter.set(Value.kReverse);
         Pushypush.set(Value.kReverse);
     }
     
     public static Command RighTriggerOn() {
         return new InstantCommand(
         () ->  Pushypush.set(Value.kForward)
         );
     }
 
     public static Command LefTriggerOff() {
         return new InstantCommand(
         () ->  Pushypush.set(Value.kReverse)
         );
     } 
 
     //Makes the left and right groups of motors move half speed
   //  public void drive(double left, double right){
     
     //    frontrightmotor.set(ControlMode.PercentOutput, right/2); 
      //   backrightmotor.set(ControlMode.PercentOutput, right/2);
      //   frontleftmotor.set(ControlMode.PercentOutput, -left/2);  
      //   backleftmotor.set(ControlMode.PercentOutput, -left/2);
   //  }
 
     //Akira will take a left and right value, and run the drive function using those values.
     //The left motors will move in the negative direction
     public Command Akira (double left, double right){
         return new InstantCommand(
             () -> drive(-left, right)
         );
     }
 
    public void motorSettings(){
        backLeftMotor.setInverted(true);
        frontLeftMotor.setInverted(true);
        //CONFIG
        backLeftMotor.configSupplyCurrentLimit(configTalonCurrent);
        frontLeftMotor.configSupplyCurrentLimit(configTalonCurrent);
        backRightMotor.configSupplyCurrentLimit(configTalonCurrent);
        frontRightMotor.configSupplyCurrentLimit(configTalonCurrent);
    }

    public void VariableSpeedIncrease(){
        Constants.multiplier += .1;
    }

    public void VariableSpeedDecrease(){
        Constants.multiplier -= .1;
    }
    public void drive(double left, double right){
        Lgroup.set(left);
        Rgroup.set(right);        
    }
//Commands are started with "C_" as to identify them as commands rather than methods
    public Command C_drive(double left, double right){
        return new InstantCommand(
            () -> drive(left, right)
        );
    }
        //  
    public void driveInDistance(double feet){
        backLeftMotor.driveInDistance(feet);
        frontLeftMotor.driveInDistance(feet);
        backRightMotor.driveInDistance(feet);
        frontRightMotor.driveInDistance(feet);
    }

    public Command C_driveInDistance(double feet){
        return new InstantCommand(
        () -> driveInDistance(feet)
        );
    }

@Override
public void periodic(){

}
}
