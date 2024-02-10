package frc.robot.subsystems;

import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class driveTrain extends SubsystemBase {

  private enum state {
    RESTING,
    ROTATING,
    SETTING;
  }

  // sets current state as RESTING when robot starts up
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

  public void driveInDistance(double feet){

    final DutyCycleOut m_request = new DutyCycleOut(.5);

    final DutyCycleOut m_stop = new DutyCycleOut(0);

    double totalInches = feet * Constants.inches;

    double motorRotations = (totalInches / Constants.circumferenceInInches) * Constants.ratio;

    double ticknum = motorRotations * Constants.encoderTicks;

    var initial = backLeftMotor.getRotorPosition();

    var posSignal = backLeftMotor.getRotorPosition();

    while ((ticknum + initial.getValue()) >= posSignal.getValueAsDouble()) {

      backLeftMotor.setControl(m_request);
      backRightMotor.setControl(m_request);
      frontLeftMotor.setControl(m_request);
      frontRightMotor.setControl(m_request);
      // refresh as to get the latest input of data.
      posSignal.refresh();
    }
//stopmotors
    backLeftMotor.setControl(m_stop);
    backRightMotor.setControl(m_stop);
    frontLeftMotor.setControl(m_stop);
    frontRightMotor.setControl(m_stop);
  }

  public void drive(double left, double right) {

    backLeftMotor.set((left / 2 * Constants.multiplier));
    frontLeftMotor.set((left / 2 * Constants.multiplier));
    backRightMotor.set((right / 2 * Constants.multiplier));
    frontRightMotor.set((right / 2 * Constants.multiplier));
    
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
  public  Command C_drive(double left, double right) {
    return new InstantCommand(() -> drive(left, right));
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

  public Command C_driveinFeet(double feet) {
    return new InstantCommand(() -> driveInDistance(feet));
  }

  public Command C_driveinInches(double inches) {
    return new InstantCommand(() -> driveInDistance(inches/12));
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

  /** Returns current State as a String */
  public String getState() {
    return current.toString();
  }

  //used a fix for a present delay
  public static void UpdateVal(){
    Constants.gyroPos = g_ahrs.getAngle();
  }
  
  
}
