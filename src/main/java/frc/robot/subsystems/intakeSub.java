package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.PositionDutyCycle;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;

public class intakeSub extends SubsystemBase {

  private enum state {
    RESTING,
    PIVOTING,
    INTAKING,
    OUTAKING,
    //positions
    SHOOTER,
    FLOOR,
    AMP
  }

  /*
   * 
   * WHEN INTAKE AND outtake BOTH @ShooterSub and @intakeSub SHALL DO THE SAME THING; if one intakes so does the other; requested per design tem
   * 
   */

  static TalonFX pivotMotor = new TalonFX(Constants.CANid.pivotMotor);

  static TalonSRX intakeMotor = new TalonSRX(Constants.CANid.intakeMotor);

  static double initial = pivotMotor.getRotorPosition().getValueAsDouble();

  state current = state.RESTING;

    //convert encoder ticks to degrees  

  final DutyCycleOut m_stop = new DutyCycleOut(0);

  final DutyCycleOut m_intake = new DutyCycleOut(.5);

  final PositionDutyCycle m_inital = new PositionDutyCycle(initial);

  // 2048/4 as to get 90 degrees in rotational units
  final PositionDutyCycle m_90degrees = new PositionDutyCycle(512);

  //135 degrees
  final PositionDutyCycle m_floor = new PositionDutyCycle(768);

  public intakeSub() {}

  public void motorConfig(){
    
    var slot0configs = new Slot0Configs();

    slot0configs.kP = .15;

    pivotMotor.getConfigurator().apply(slot0configs);

  }

  // spins outwards wheels into the robot
  public void intake() {
    current = state.INTAKING;
    intakeMotor.set(ControlMode.PercentOutput, .5);
  }

  public void outtake(){
    current = state.OUTAKING;
    intakeMotor.set(ControlMode.PercentOutput, -.5);
  }

  public void stopIntake() {
    // stop motors on outer intake
    intakeMotor.set(ControlMode.PercentOutput, -.5);
    current = state.RESTING;
  }

  public void resetPos() {

    current = state.PIVOTING;
    stopIntake();
    pivotMotor.setControl(m_inital);
    initial = pivotMotor.getPosition().getValueAsDouble(); 
    // update new reset position as initial
    current = state.RESTING;
  }

  /**
   * 
   * @param Position options are "Shooter", "Amp", "Floor"
   * 
   */
  public void setPos(String Position){

    switch(Position) {

      case "Shooter":
        pivotMotor.setControl(m_inital);
          break;
      case "Amp":
        pivotMotor.setControl(m_90degrees);
          break;
      case "Floor":
        pivotMotor.setControl(m_floor);
          break;

    }

  }

  public void extendnIntake() {
    current = state.PIVOTING;
    setPos("Floor");

    //5 volts
  //  double gRatio = 5; **gear ratio for the actual shooting part of the intake

  //one rotation with motor
    double ticks = 2048;
//the gearbox ratio
    double ratio = 56/24;
//90 degree angle
    double angle = 90;
    
    //one rotation
    double maxTicks = (ticks * ratio)/360; 

    //90 degree rotation
    double m_90degrees = (maxTicks * angle);

    //sets motor to 90 degrees
    PositionDutyCycle wanted = new PositionDutyCycle(m_90degrees);
  
    pivotMotor.setControl(wanted);

    current = state.INTAKING;

    intake();
    
  }
  
  //commands

  /** @param Position of pivot motor (Three states) 
   * "Shooter", "Amp", "Floor"
  */
  public Command C_setPos(String Position){
    return new InstantCommand(() -> setPos(Position));
  }
  /**Resets position of pivotMotor to starting position (Shooter position) */
  public Command C_resetPos(){
    return new InstantCommand(() -> resetPos());
  }
  /**Extends to floor and intakes*/
  public Command C_extendnintake(){
    return new InstantCommand(() -> extendnIntake());
  }
  /**Power of intakeMotor is set to intake */
  public Command C_intake(){
    return new InstantCommand(() -> intake());
  }
  /**Power of intakeMotor is set to outake */
  public Command C_outtake(){
    return new InstantCommand(() -> outtake());
  }
  /**Stops intakeMotor */
  public Command C_stopIntake(){
    return new InstantCommand(() -> stopIntake());
  }
  /**Delay with seconds */
  public Command C_waitCommand(double seconds){
    return new WaitCommand(seconds);
  }

  /** Returns current State as a String */
  public String getState() {
    return current.toString();
  }
}
