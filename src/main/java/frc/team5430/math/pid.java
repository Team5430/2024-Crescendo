package frc.team5430.math;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.util.sendable.SendableRegistry;

public class pid implements Sendable {  
  //kP - Proportional Term - to improve the transient response rise time and settling time
    private double kP_;

    //kI - Integral term - Improve steady state response
    private double kI_;

    // kI - Derivative Control - Improve the transient response by way of predicting error will occur in the future 
    private double kD_;

    //Variable Creations
    private double error;
    private double lastError;
    private double errorSum;
    private double dT;
    private double errorRate;
    private double PIDoutput;
    static private int instances;
 
    public pid(){}

    //Variable Creations for kP,kI and kD
    public pid(double kP, double kI, double kD){
        this.kP_ = kP;
        this.kI_ = kI;
        this.kD_ = kD;
        
        instances++;
        SendableRegistry.addLW(this, "PID", instances);
        }

        //Function to set PID 
    public void setPID(double kP, double kI, double kD){
        this.kP_ = kP;
        this.kI_ = kI;
        this.kD_ = kD;  
    }

    public void close() {
        SendableRegistry.remove(this);
    }

    //Function to get KP value
    public double getkP(){
        return kP_;
    }
  //Function to get KP value
    public double getkI(){
        return kI_;
    }
   //Function to get KD value
    public double getkD(){
        return kD_;
    }
    //Function to set/update/change the KP Value
    public void setkP(double kP){
         kP_ = kP;
    }
//Function to set/update/change the KI Value
    public void setkI(Double kI){
         kI_ = kI;
    }
//Function to set/update/change the KD Value
    public void setkD(double kD){
         kD_ = kD;
    }

    //Function of Calculation to set the setpoint value
  public double calc(double setpoint, double input){
    //Motor error founded by setpoint minus the input value
    error = setpoint - input;

//delta dT changed into a constant 0.2 due to the ongoign period of time in teleop, and auton.
    dT = 0.2;

    //get the errorSum by having the error multiply by the dT but turn into positive
    errorSum += error * dT;

    //errorRate be seen by having original error minus the lastError, then divide by the lastTimestamp
    errorRate = (error - lastError) / dT;

    //add all of the values into a final PID Output
    PIDoutput = kP_ * error + kI_ * errorSum + kD_ * errorRate;
    //update values
    lastError = error;
    return PIDoutput;
  }

  //reset error,lastError, and lastTimeStamp to 0
  public void reset(){
    error = 0;
    lastError = 0;
  }

  @Override

  //SmartDashboard add-ons
  public void initSendable(SendableBuilder build){
    build.setSmartDashboardType("PID");
    build.addDoubleProperty("P", this::getkP, this::setkP);
    build.addDoubleProperty("I", this::getkI, this::setkI);
    build.addDoubleProperty("D", this::getkD, this::setkD);
  }
}