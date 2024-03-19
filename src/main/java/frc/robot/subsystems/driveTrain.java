//These are the imports
package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
//Class made for robot movement
public class driveTrain extends SubsystemBase {
  
  private enum state { //States are the status of the robot
    RESTING, //means not moving
    TURNING, //means the robot is moving
    SETTING; //means robot is slowing or speeding
  }

  // sets current state as RESTING when robot starts up

  private Timer timer = new Timer(); //Timer variable created as a Timer() function
  static state current = state.RESTING; //default state in RESTING


  // Initiliazing the motor controllers for Drive
  static final TalonFX backLeftMotor = new TalonFX(Constants.CANid.backLeftMotor);
  static final TalonFX frontLeftMotor = new TalonFX(Constants.CANid.frontLeftMotor);
  static final TalonFX backRightMotor = new TalonFX(Constants.CANid.backRightMotor);
  static final TalonFX frontRightMotor = new TalonFX(Constants.CANid.frontRightMotor);

  private static AHRS g_ahrs = new AHRS(SPI.Port.kMXP);  //initialize g_arhs as AHRS Gyroscope

  public void motorConfig() { //Configs all motor inverted (going CCW)
    //backLeftMotor.setInverted(true);
    //frontLeftMotor.setInverted(true);
  }

  // Method to increase variable speed of drivetrain
  public void VariableSpeedIncrease() {
    Constants.multiplier = .9;
  }
//Method to decrease variable speed of drivetrain 
  public void VariableSpeedDecrease() {
    Constants.multiplier = .5;
  }

  public void VariableSpeedIncreaseNORMAL(){
    Constants.multiplier = 2;
  }

  // Method to drive the drivetrain an x amount of inches forwards relative to current position.
  public static void driveinInches(double inches){

    // Calculates the amount of motor roations needed to move x distance
    double rotationsNeeded = (Constants.ratio * inches) / Constants.circumferenceInInches;
    // Sets the initial position of the motor to use relatively to the new position
    double initial = (backLeftMotor.getRotorPosition().getValueAsDouble());

    // If the distance is a positive number (greater than 0)
    if(inches > 0){
      // As long as the distance travelled is less than the rotations needeed
      while(backLeftMotor.getRotorPosition().getValueAsDouble() - initial < rotationsNeeded){
        // Set speed of motors to 80% speed
        RunMotors(Constants.driveinInchespower);
      }
    }else{ // If the distance is a negative number (less than 0)
      // As long as the distance travelled is greater than the rotations needed
      while(backLeftMotor.getRotorPosition().getValueAsDouble() - initial > rotationsNeeded){
        // Set speed of motors to 80% speed backwds
        RunMotors(-Constants.driveinInchespower);
      }
    }
    // Stop motors after all is said and done
    System.out.println("Stopping Motors");
    RunMotors(0);
    
  }
   
  public void drive(double left, double right) {
  //deadzone adding .2, prevents outputs from under 0.2
   if(left > .2 || left < -.2 || right > .2 || right < -.2){
    backLeftMotor.set((left / 2 * Constants.multiplier));
    frontLeftMotor.set((left / 2 * Constants.multiplier));
    backRightMotor.set((-right / 2 * Constants.multiplier));
    frontRightMotor.set((-right / 2 * Constants.multiplier));
    }else{
      // if any of the motors moving less than 20% speed
      StopMotors();
    } 
  }

  // Commands are started with "C_" as to identify them as commands rather than methods

  // Command variant of drive
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
    backRightMotor.set(-speed);
    frontLeftMotor.set(speed);
    frontRightMotor.set(-speed);
    
  }

  /**positive speed value turns RIGHT; neagtive speed value turns LEFT */
  public static void TurnRobot(double speed){
    backLeftMotor.set(speed);
    backRightMotor.set(speed);
    frontLeftMotor.set(speed);
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
   while(timer.get() <= time){ //while timer is not greater or equal to parameter "time"a
  backLeftMotor.set(left); //left motors turn left
    backRightMotor.set(right); //right motors turn right
    frontLeftMotor.set(left);
    frontRightMotor.set(right);
   }
   backLeftMotor.set(0); //else, all motors stop
    backRightMotor.set(0);
    frontLeftMotor.set(0);
    frontRightMotor.set(0);
   }
  }
  public Command C_CurveTurn(double left, double right, double time){ //run CurveTurn() as command method
    return new InstantCommand(() -> CurveTurn(left, right, time));
   }

/** Turn to a desired angle, Negative going counter clockwise, and Positive clockwise */
  public static void turntoAngle(double angle){

    g_ahrs.reset();
  
    double initial = g_ahrs.getRoll();
  
    //if its negative, we want to turn left
    if(angle == -Math.abs(angle)){  //while current angle is less than the current angle + wanted
      while((initial + angle) <= g_ahrs.getRoll()){
        TurnRobot(-.3); //turn robot at -30% (CW)
      UpdateVal(); //gets gyroscope angle value to update
     }
   StopMotors(); //Stops the motors
   // if it's not negative, we're turning right.
   }else{
      while((initial + angle) >= g_ahrs.getRoll()){
        TurnRobot(+.3); //turn robot at 30% (CCW)
      UpdateVal(); //gets gyroscope angle value to update
     }
   StopMotors();
   }
}
  /**Negative turns CounterClockwise, while positive, Clockwise COMMAND VERSION */
public Command C_turntoAngle(double angle){
  return new InstantCommand(() -> turntoAngle(angle));
}

// Command variant of drive in inches

public Command C_driveinInches(double inches){
  return new InstantCommand(() -> driveinInches(inches));
}

  /** Returns current State as a String */
  public String getState() {
    return current.toString();
  }

  //used a fix for a present delay
  public static void UpdateVal(){  //function to get the gyroscope angle value
    Constants.gyroPos = g_ahrs.getRoll();
  }  
  
}
