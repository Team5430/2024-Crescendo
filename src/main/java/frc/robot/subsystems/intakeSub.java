package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class intakeSub extends SubsystemBase{
    
    private enum state{
        RESTING,
        ROTATING,
        INTAKING
    }

    WPI_TalonFX rotateMotor = new WPI_TalonFX(Constants.CANid.rotateMotor);
    WPI_TalonFX pullMotor = new WPI_TalonFX(Constants.CANid.pullMotor);

    state current = state.RESTING;

    static double initial = rotateMotor.getSelectedSensorPosition();

    public intakeSub(){}
    
    //spins outwards wheels into the robot
    public void intake(){
        current = state.INTAKING;
        pullMotor.set(ControlMode.PercentOutput, .5);
    }

    public void stopIntake(){
        //stop motors on outer intake
        pullMotor.set(ControlMode.PercentOutput, 0);
        current = state.RESTING;
    }

    public void resetPos(){
        
        current = state.ROTATING;
        stopIntake();
        rotateMotor.set(ControlMode.position, initial);
        initial = rotateMotor.getSelectedSensorPosition; //update new reset position as inital   
        current = state.RESTING;

    }

    public void extendnIntake(){
        current = state.ROTATING;
        //motor spin out using encoders
        current = state.INTAKING;
        intake();

    }

/**Returns current State as a String */
    public String getState(){
        return current.toString();
    }

}
