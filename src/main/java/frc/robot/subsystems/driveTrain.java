package frc.robot.subsystems;

import java.sql.Time;
//import java.util.Timer;

import com.ctre.phoenix6.hardware.TalonFX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;

public class driveTrain extends SubsystemBase {

  private enum state {
    RESTING,
    ROTATING,
    SETTING;
  }

  // sets current state as RESTING when robot starts up

  private Timer timer = new Timer();
  static state current = state.RESTING;

  static final TalonFX backLeftMotor = new TalonFX(Constants.CANid.backLeftMotor);
  static final TalonFX frontLeftMotor = new TalonFX(Constants.CANid.frontLeftMotor);
  static final TalonFX backRightMotor = new TalonFX(Constants.CANid.backRightMotor);
  static final TalonFX frontRightMotor = new TalonFX(Constants.CANid.frontRightMotor);


  private static AHRS g_ahrs = new AHRS(SPI.Port.kMXP);

  public boolean D_toggle = true;

  public void motorConfig() {
    backLeftMotor.setInverted(true);
    frontLeftMotor.setInverted(true);
  }

  public void VariableSpeedIncrease() {
    Constants.multiplier = .9;
  }

  public void VariableSpeedDecrease() {
    Constants.multiplier = .5;
  }
//drive in feet
  public static void driveInDistance(double feet){

    double output = .8;

    double totalInches = feet * Constants.inches;

    double motorRotations = (totalInches / Constants.circumferenceInInches) * Constants.ratio * Constants.encoderTicks;

    var initial = backLeftMotor.getRotorPosition().getValueAsDouble() * Constants.encoderTicks;

    backLeftMotor.setPosition(0);
    backRightMotor.setPosition(0);
    frontLeftMotor.setPosition(0);
    frontRightMotor.setPosition(0);

//both < and > implemeneted to account for negative and positive value
if(Math.abs(feet) == feet){
      while ((motorRotations + initial) >= backLeftMotor.getRotorPosition().getValueAsDouble() * Constants.encoderTicks) {
    RunMotors(output);
    System.out.println("Current position: " + backLeftMotor.getRotorPosition().getValueAsDouble() * Constants.encoderTicks);
    System.out.println(backLeftMotor.getRotorPosition());
    System.out.println("Will be less than: " + motorRotations + initial);
    } 
}else{
  while ((motorRotations + initial) <= backLeftMotor.getRotorPosition().getValueAsDouble() * Constants.encoderTicks) {
    RunMotors(-output);
    System.out.println("Current position: " + backLeftMotor.getRotorPosition().getValueAsDouble() * Constants.encoderTicks);
    System.out.println("Will be greater than: " + motorRotations + initial);
  }
}

//stopmotors
System.out.println("Stopping Motors. (feet)");
    StopMotors();
  }

    public Command C_driveinFeet(double feet) {
    return new InstantCommand(() -> driveInDistance(feet));
  }


  //* IGNORE THIS, I AM TESTING THIS FOR PID. THIS SHOULD NOT BE RUN AND WILL NOT BE RAN AT ALL!!! - Anthony S. */
  public static void PID(){
  final double kP = 0; 
  double setpoint = 0; 

  double error = setpoint - kP;

  
  }
  public static void driveInInches(double inches){

    double output = .5;

    //Sets a total amount of inches needed to tracvel
    double totalInches = inches;

    //The amount of 
    double motorRotations = (totalInches / Constants.circumferenceInInches) * Constants.ratio * Constants.encoderTicks;

    backLeftMotor.setPosition(0);
    backRightMotor.setPosition(0);
    frontLeftMotor.setPosition(0);
    frontRightMotor.setPosition(0);

    var initial = backLeftMotor.getRotorPosition().getValueAsDouble() * Constants.encoderTicks;

if(Math.abs(inches) == inches){
    while ((motorRotations + initial) >=  backLeftMotor.getRotorPosition().getValueAsDouble() * Constants.encoderTicks) {
      RunMotors(output);
    }
}else{
     while ((motorRotations + initial) <=  backLeftMotor.getRotorPosition().getValueAsDouble() * Constants.encoderTicks) {
      RunMotors(-output);
      System.out.println("Current position: " + backLeftMotor.getRotorPosition().getValueAsDouble() * Constants.encoderTicks);
      System.out.println(backLeftMotor.getRotorPosition());
      System.out.println("Will be greater than: " + motorRotations + initial);
      SmartDashboard.putNumber("Current Position: ", backLeftMotor.getRotorPosition().getValueAsDouble() * Constants.encoderTicks);
      SmartDashboard.putNumber("Will be greater than", motorRotations);
    }
}
//stopmotors
    System.out.println("Stopping Motors. (inches)");
  StopMotors();
  }

  public static void myNewcommand(double inches){

    double rotationsNeeded = (Constants.ratio * inches) / Constants.circumferenceInInches;
    double initial = (backLeftMotor.getRotorPosition().getValueAsDouble());

    if(inches > 0){
      while(backLeftMotor.getRotorPosition().getValueAsDouble() - initial < rotationsNeeded){
        RunMotors(0.8);
      }
    }else{
      while(backLeftMotor.getRotorPosition().getValueAsDouble() - initial > rotationsNeeded){
        RunMotors(-0.8);
      }
    }
    System.out.println("Stopping Motors");
    RunMotors(0);
    
  }
   
    public Command C_driveinInches(double inches) {
    return new InstantCommand(() -> driveInInches(inches));
  }

  

  public void drive(double left, double right) {
  //deadzone adding .2
   if(left > .2 || left < -.2 || right > .2 || right < -.2){
    backLeftMotor.set((left / 2 * Constants.multiplier));
    frontLeftMotor.set((left / 2 * Constants.multiplier));
    backRightMotor.set((right / 2 * Constants.multiplier));
    frontRightMotor.set((right / 2 * Constants.multiplier));
    }else{
      StopMotors();
    }
    
    
    
  }

  // Commands are started with "C_" as to identify them as commands rather than methods
  public  Command C_drive(double left, double right) {
    return new InstantCommand(() -> drive(left, right));
  }

 /** will stop all the motors */
  public static void StopMotors(){
    backLeftMotor.stopMotor();
    backRightMotor.stopMotor();
    frontLeftMotor.stopMotor();
    frontRightMotor.stopMotor();
  }

  /** continously running the motor  */
  public static void RunMotors(double speed){
    backLeftMotor.set(speed);
    backRightMotor.set(speed);
    frontLeftMotor.set(speed);
    frontRightMotor.set(speed);
    
  }

  /**positive speed value turns RIGHT; neagtive speed value turns LEFT */
  public static void TurnRobot(double speed){
    backLeftMotor.set(-speed);
    backRightMotor.set(speed);
    frontLeftMotor.set(-speed);
    frontRightMotor.set(speed);
  }






/** one side turning front must be greater than the other side turning back. 
 * EXAMPLE:
 * TURNING LEFT: CurveTurn(left: 0.8, right: 0.4, time: 5.0)
 * TURNING RIGHT: CurveTurn(left: 0.4, right: 0.8, time: 5.0)
 */
  public void CurveTurn(double left, double right, double time){
  {
  timer.restart(); 
   while(timer.get() <= time){
  backLeftMotor.set(left);
    backRightMotor.set(right);
    frontLeftMotor.set(left);
    frontRightMotor.set(right);
   }
   backLeftMotor.set(0);
    backRightMotor.set(0);
    frontLeftMotor.set(0);
    frontRightMotor.set(0);
   }
  }
  public Command C_CurveTurn(double left, double right, double time){
    return new InstantCommand(() -> CurveTurn(left, right, time));
   }
  








/** Turn to a desired angle, Negative going counter clockwise, and Positive clockwise */
  public static void turntoAngle(double angle){

    g_ahrs.reset();
  
    double initial = g_ahrs.getAngle();
  
    //if its negative, we want to turn left
    if(angle == -Math.abs(angle)){
      //while current angle is less than the current angle + wanted
      while((initial + angle) <= g_ahrs.getAngle()){
        TurnRobot(-.3);
      UpdateVal();
     }
   StopMotors();
   }else{
      while((initial + angle) >= g_ahrs.getAngle()){
        TurnRobot(+.3);
      UpdateVal();
     }
   StopMotors();
   }
}
  /**Negative turns CounterClockwise, while positive, Clockwise COMMAND VERSION */
public Command C_turntoAngle(double angle){
  return new InstantCommand(() -> turntoAngle(angle));
}











public Command C_myCommand(double inch){
  return new InstantCommand(() -> myNewcommand(inch));
}

  /** Returns current State as a String */
  public String getState() {
    return current.toString();
  }

  //used a fix for a present delay
  public static void UpdateVal(){
    Constants.gyroPos = g_ahrs.getAngle();
  }  

  @Override
  public void periodic(){
    var r = backRightMotor.getRotorPosition();
    var l = backLeftMotor.getRotorPosition();

    SmartDashboard.putNumber("L", r.getValueAsDouble());
    SmartDashboard.putNumber("R", l.getValueAsDouble());

    new WaitCommand(.02);
  }
  
}
