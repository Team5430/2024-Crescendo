package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.team5430.motors.RoboTires;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class driveTrain extends SubsystemBase {

    private enum state{
        RESTING,
        ROTATING,
        SETTING;    
    }
    //sets current state as RESTING when robot starts up 
    state current = state.RESTING;
    

    final static RoboTires backLeftMotor = new RoboTires(Constants.CANid.backLeftMotor);
    final static RoboTires frontLeftMotor = new RoboTires(Constants.CANid.frontLeftMotor);
    final static RoboTires backRightMotor = new RoboTires(Constants.CANid.backRightMotor);
    final static RoboTires frontRightMotor = new RoboTires(Constants.CANid.frontRightMotor);

    public boolean D_toggle = true;

    static SupplyCurrentLimitConfiguration configTalonCurrent = new SupplyCurrentLimitConfiguration(true,55,0,0);

 
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
        Constants.multiplier = .9;  
    }

    public void VariableSpeedDecrease(){
        Constants.multiplier = .5;
    }

    public void drive(double left, double right){
        current = state.ROTATING;
        backLeftMotor.set(ControlMode.PercentOutput, left/2 * Constants.multiplier);
        frontLeftMotor.set(ControlMode.PercentOutput, left/2 * Constants.multiplier);
        backRightMotor.set(ControlMode.PercentOutput, right/2 * Constants.multiplier);
        frontRightMotor.set(ControlMode.PercentOutput, right/2 * Constants.multiplier);
    }
//toggle between coast and break mode.
    public void CoastingBreakToggle(){
        if(D_toggle){
            backLeftMotor.setNeutralMode(NeutralMode.Coast);
            frontLeftMotor.setNeutralMode(NeutralMode.Coast);
            backRightMotor.setNeutralMode(NeutralMode.Coast);
            frontRightMotor.setNeutralMode(NeutralMode.Coast);
            D_toggle = false;
        } else{
            backLeftMotor.setNeutralMode(NeutralMode.Brake);
            frontLeftMotor.setNeutralMode(NeutralMode.Brake);
            backRightMotor.setNeutralMode(NeutralMode.Brake);
            frontRightMotor.setNeutralMode(NeutralMode.Brake);
            D_toggle = true;
        }
    }

//Commands are started with "C_" as to identify them as commands rather than methods
    public Command C_drive(double left, double right){
        return new InstantCommand(
            () -> drive(left, right)
        );
    }
        //  
    public void driveInDistance(double feet){
        current = state.SETTING;
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

/**Returns current State as a String */
    public String getState(){
        return current.toString();
    }

    @Override
    public void periodic(){
    }
}
