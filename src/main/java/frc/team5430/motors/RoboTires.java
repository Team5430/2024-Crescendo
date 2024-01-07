package frc.team5430.motors;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.math.controller.PIDController;

/**  custom twist of {@link WPI_TalonFX} */

public class RoboTires extends WPI_TalonFX{

//constants
private double ratio = 10.71;
private double circumferenceInInches = 6 * Math.PI;
private double inches = 12;
private double totalInches;
private double motorRotations;
private double encoderTicks = 2048;
//can set above varabiales to a default as shown

    public RoboTires(int CANid){
        super(CANid);
    }

    //individually control motors for distance.
public void driveInDistance(double feet){

totalInches = feet * inches;

motorRotations = (totalInches/circumferenceInInches) * ratio;

double ticknum =  motorRotations * encoderTicks;

double initial = this.getSelectedSensorPosition();

while((ticknum + initial) >= this.getSelectedSensorPosition()){
this.set(ControlMode.PercentOutput, motorRotations * encoderTicks);
}

this.set(ControlMode.PercentOutput, 0);

}
//note to anyone who contributes, driveInDistance should have more than one form of measuremental basis
public void driveInDistance(double feet, PIDController pid){

totalInches = feet * inches;

motorRotations = (totalInches/circumferenceInInches) * ratio;

pid.setSetpoint(motorRotations * encoderTicks);

    while(this.getSelectedSensorPosition() < motorRotations * encoderTicks){
this.set(ControlMode.PercentOutput, pid.calculate(this.getSelectedSensorPosition()));
}
    }

    //used to modify any constants, though defaults are set
public void setCircumference(double diameter){
 circumferenceInInches = diameter * Math.PI;
 }

public double getCircumference(){
    return circumferenceInInches;
}

public void setRatio(double Gratio){
    ratio = Gratio;
}

public double getRatio(){
    return ratio;
}

}