package frc.robot.subsystems;

import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.team5430.motors.RoboTires;

public class driveTrain extends SubsystemBase {

  private enum state {
    RESTING,
    ROTATING,
    SETTING;
  }

  // sets current state as RESTING when robot starts up
  static state current = state.RESTING;

  static final RoboTires backLeftMotor = new RoboTires(Constants.CANid.backLeftMotor);
  static final RoboTires frontLeftMotor = new RoboTires(Constants.CANid.frontLeftMotor);
  static final RoboTires backRightMotor = new RoboTires(Constants.CANid.backRightMotor);
  static final RoboTires frontRightMotor = new RoboTires(Constants.CANid.frontRightMotor);

  private static AHRS g_ahrs = new AHRS(SPI.Port.kMXP);

  public boolean D_toggle = true;

  public void motorSettings() {
    backLeftMotor.setInverted(true);
    frontLeftMotor.setInverted(true);
  }

  public void VariableSpeedIncrease() {
    Constants.multiplier = .9;
  }

  public void VariableSpeedDecrease() {
    Constants.multiplier = .5;
  }

  public void drive(double left, double right) {

    // record inputs
    DutyCycleOut m_left = new DutyCycleOut(left / 2 * Constants.multiplier);
    DutyCycleOut m_right = new DutyCycleOut(right / 2 * Constants.multiplier);

    backLeftMotor.setControl(m_left);
    frontLeftMotor.setControl(m_left);
    backRightMotor.setControl(m_right);
    frontRightMotor.setControl(m_right);
  }

  // toggle between coast and break mode.
  public void CoastingBreakToggle() {
    if (D_toggle) {
      backLeftMotor.setNeutralMode(NeutralModeValue.Coast);
      frontLeftMotor.setNeutralMode(NeutralModeValue.Coast);
      backRightMotor.setNeutralMode(NeutralModeValue.Coast);
      frontRightMotor.setNeutralMode(NeutralModeValue.Coast);
      D_toggle = false;
    } else {
      backLeftMotor.setNeutralMode(NeutralModeValue.Brake);
      frontLeftMotor.setNeutralMode(NeutralModeValue.Brake);
      backRightMotor.setNeutralMode(NeutralModeValue.Brake);
      frontRightMotor.setNeutralMode(NeutralModeValue.Brake);
      D_toggle = true;
    }
  }

  // Commands are started with "C_" as to identify them as commands rather than methods
  public Command C_drive(double left, double right) {
    return new InstantCommand(() -> drive(left, right));
  }

  //
  public static void driveInDistance(double feet) {
    current = state.SETTING;
    backLeftMotor.driveInDistance(feet);
    frontLeftMotor.driveInDistance(feet);
    backRightMotor.driveInDistance(feet);
    frontRightMotor.driveInDistance(feet);
  }

  public static void StopMotors(){
    backLeftMotor.stopMotor();
    backRightMotor.stopMotor();
    frontLeftMotor.stopMotor();
    frontRightMotor.stopMotor();
  }

  public void RunMotors(double speed){
    backLeftMotor.set(speed);
    backRightMotor.set(speed);
    frontLeftMotor.set(speed);
    frontRightMotor.set(speed);
    
  }

  /**positive speed value turns RIGHT; neagtive speed value turns LEFT */
  public static void TurnRobot(double speed){
    backLeftMotor.set(speed);
    backRightMotor.set(-speed);
    frontLeftMotor.set(speed);
    frontRightMotor.set(-speed);
  }

  

  public static Command C_driveInDistance(double feet) {
    return new InstantCommand(() -> driveInDistance(feet));
  }

  /**Negative turns CounterClockwise, while positive, Clockwise 
 * @return */
  public static Command[] turntoAngle(double angle){

    g_ahrs.reset();
  
    double initial = g_ahrs.getAngle();
  
    //if its negative, we want to turn left
    if(angle == -Math.abs(angle)){
      //while current angle is less than the current angle + wanted
      while((initial + angle) >= g_ahrs.getAngle()){
        TurnRobot(-.3);
      UpdateVal();
     }
   StopMotors();
   }else{
      while((initial + angle) >= g_ahrs.getAngle()){
        TurnRobot(-.3);
      UpdateVal();
     }
   StopMotors();
   }
    return null;
}

/**Negative turns CounterClockwise, while positive, Clockwise COMMAND VERSION */
public static Command C_turntoAngle(double angle){
  return new InstantCommand(() -> turntoAngle(angle));
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
  public void periodic() {}

  
}
