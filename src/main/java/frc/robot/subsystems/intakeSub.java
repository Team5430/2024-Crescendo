package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix6.configs.FeedbackConfigs;
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

  static TalonSRX transversalMotor = new TalonSRX(Constants.CANid.transversalMotor);

  static double initial = pivotMotor.getRotorPosition().getValueAsDouble();

  state current = state.RESTING;

    //convert encoder ticks to degrees  

  final DutyCycleOut m_stop = new DutyCycleOut(0);

  final DutyCycleOut m_intake = new DutyCycleOut(.8);

  final PositionDutyCycle m_inital = new PositionDutyCycle(initial);

  // 1/8 as to get 45 degrees in rotational units
    final PositionDutyCycle m_45degrees = new PositionDutyCycle(initial + .145);

  // degrees
  final PositionDutyCycle m_floor = new PositionDutyCycle(initial + .375);

  public intakeSub() {}

  public void motorConfig(){
    // makes a new config
    var slot0configs = new Slot0Configs();
    // The new config = .15 which = 1 circle
    slot0configs.kP = .15;
    // mfeed = new feedbackconfig
    var mfeed = new FeedbackConfigs();
    // makes a new configutation setting 
    mfeed.SensorToMechanismRatio = Constants.Iratio;
    // 5 rotations = 1 big rotation 
    pivotMotor.getConfigurator().apply(slot0configs);
    //Applies configuration
    pivotMotor.getConfigurator().apply(mfeed);
    //Applies configuration 
    //Sets position to 0
    pivotMotor.setPosition(0);
    
  }

  // spins intake wheels into the robot
  public void intake() {
    current = state.INTAKING;
    intakeMotor.set(ControlMode.PercentOutput, -.7);
  }

//spins intake wheels away from robot
  public void outtake(){
    current = state.OUTAKING;
    intakeMotor.set(ControlMode.PercentOutput, .7);
  }

//transversal goes towards the intake
  public void transversalIN(){
    transversalMotor.set(ControlMode.PercentOutput, .5);
  }

//transversal goes outwards towards the shooter's spout
  public void transversalOUT(){
  transversalMotor.set(ControlMode.PercentOutput, -.5);
  }

//stop the transversal motor from running
  public void transversalSTOP(){
    transversalMotor.set(ControlMode.PercentOutput, 0);
  }

  public void stopIntake() {
    // stop motors on outer intake
    intakeMotor.set(ControlMode.PercentOutput, 0);
    current = state.RESTING;
  }

//stops motor and sets pivot to shooter
  public void resetPos() {

    current = state.PIVOTING;

    stopIntake();

    setPos("Shooter");

    current = state.RESTING;
  }

  /**
   * 
   * @param Position options are "Shooter", "Amp", "Floor"
   * 
   */
  public void setPos(String Position){//A switch case method used to change the pivot position of the motor

    switch(Position) {

      case "Shooter"://moves to Shooter position
        pivotMotor.setControl(m_inital);
          break;
      case "Amp"://moves to Amp position
        pivotMotor.setControl(m_45degrees);
          break;
      case "Floor"://moves to Floor position
        pivotMotor.setControl(m_floor);
          break;

    }
    
  }

    public Command C_setPos(String Position){//Command calls a method
    return new InstantCommand(() -> setPos(Position));
  }

  
  public void extendnIntake() {//this method sets the intake to the floor, and then activates the intake motor
    current = state.PIVOTING;//turning the status of the pivot motor to Pivoting

    pivotMotor.setControl(m_floor);//sets the pivot motor to the floor

    pivotMotor.stopMotor();//turns off the motor

    current = state.INTAKING;//changes the state of the robot to intaking

    intake();//turns on the intake motor
  }
  
  public void IntakeControl(double power){
     pivotMotor.set(power);
  }

  

    //commands

  /** @param Position of pivot motor (Three states) 
   * "Shooter", "Amp", "Floor" */
  

  
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
  
  public Command C_transerversalSTOP(){
    return new InstantCommand(() -> transversalSTOP());
  }

  public Command C_transerversalOUT(){
    return new InstantCommand(() -> transversalOUT());
  }

  /** Returns current State as a String */
   
  public String getState() {
    return current.toString();
  }
   
  
}
