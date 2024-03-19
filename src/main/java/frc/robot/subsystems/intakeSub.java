//These are the imports
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.PositionDutyCycle;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
//Class made to manage intake
public class intakeSub extends SubsystemBase {
//Status of the intake motor
//"RESTING" means the intake is not moving
//"PIVOTING" means the intake is currently changing position
//"INTAKING" means the intake can take notes from the floor
//"OUTAKING" means the intake is moving the note outwards
//"SHOOTER" means the intake motor is in the shooter pivot position
//"FLOOR" means the intake motor is in the floor pivot position
//"AMP" means the intake motor is in the amp pivot position

  private enum state {
    RESTING,
    PIVOTING,
    INTAKING,
    OUTAKING,
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

  static state current = state.RESTING;

    //convert encoder ticks to degrees  

  final DutyCycleOut m_stop = new DutyCycleOut(0);

  final DutyCycleOut m_intake = new DutyCycleOut(.7);

  final static PositionDutyCycle m_floor = new PositionDutyCycle(0);

  //75 degrees
  final static PositionDutyCycle m_amp = new PositionDutyCycle(Constants.degree * 75);

  // 160 degrees
  final static PositionDutyCycle m_shoot = new PositionDutyCycle(Constants.degree * 160 );

  public intakeSub() {}

  public void motorConfig(){
    // makes a new config

    var slot0configs = new Slot0Configs();
    // The new config = .15 
    slot0configs.kP = .09;

    slot0configs.kI = .2;

    slot0configs.kD = 0;



    var mfeed = new FeedbackConfigs();
    // makes a new configutation setting 
    mfeed.SensorToMechanismRatio = Constants.Iratio;
    // 5 rotations = 1 big rotation 
    mfeed.FeedbackSensorSource = FeedbackSensorSourceValue.RotorSensor;

    pivotMotor.getConfigurator().apply(slot0configs);
    //Applies configuration
    pivotMotor.getConfigurator().apply(mfeed);
    //Applies configuration 
    //Sets position to 0
    pivotMotor.setPosition(0);

  
  }
  // spins intake wheels into the robot
  public static void intake() {
    current = state.INTAKING;
    intakeMotor.set(ControlMode.PercentOutput, -.7);
  }

//spins intake wheels away from robot
  public static void outtake(){
    current = state.OUTAKING;
    intakeMotor.set(ControlMode.PercentOutput, .7);
  }

  public static void stopIntake() {
    // stop motors on outer intake
    intakeMotor.set(ControlMode.PercentOutput, 0);
    current = state.RESTING;
  }

//stops motor and sets pivot to shooter
  public void resetPos() {

    current = state.PIVOTING;

    stopIntake();

    setShoot();

    current = state.RESTING;
  }
//Below initializes both methods and commands of "setShoot", "setFloor", and "setAmp"
  /**
   * @param Position options are "Shooter", "Amp", "Floor"
   */
  public static void setShoot(){
            pivotMotor.setControl(m_shoot);
  }

  public static void setAmp(){
            pivotMotor.setControl(m_amp);
  }

  public static void setFloor(){
             pivotMotor.setControl(m_floor);
  }

  public static Command C_setShoot(){
    return new InstantCommand(() -> setShoot());
  }

  public static Command C_setAmp(){
    return new InstantCommand(() -> setAmp());
  }

  public static Command C_setFloor(){
    return new InstantCommand(() -> setFloor());
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
  
  public static Command C_intake(){
    return new InstantCommand(() -> intake());
  }
  
  /**Power of intakeMotor is set to outake */
  public  static Command C_outtake(){
    return new InstantCommand(() -> outtake());
  }

  /**Stops intakeMotor */
  public static Command C_stopIntake(){
    return new InstantCommand(() -> stopIntake());
  }
  
  /** Returns current State as a String */
  public String getState() {
    return current.toString();
  }

  //This loops indefinitely just gets the pivot position
  @Override
  public void periodic(){

    var pivot = pivotMotor.getRotorPosition();

    double encoder = pivot.getValueAsDouble();

    SmartDashboard.putNumber("Encoder", encoder);
  }

  
}
