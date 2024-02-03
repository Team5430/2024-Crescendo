package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.PositionDutyCycle;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.team5430.math.pid;

public class intakeSub extends SubsystemBase {

  private enum state {
    RESTING,
    ROTATING,
    INTAKING
  }

  /*
   * 
   * WHEN INTAKE AND OUTAKE BOTH @ShooterSub and @intakeSub SHALL DO THE SAME THING; if one intakes so does the other; requested per design tem
   * 
   */

  static TalonFX pivotMotor = new TalonFX(Constants.CANid.pivotMotor);

  static TalonSRX pullMotor = new TalonSRX(Constants.CANid.pullMotor);

  static double initial = pivotMotor.getRotorPosition().getValueAsDouble();

  state current = state.RESTING;

    //convert encoder ticks to degrees  
  double ticks = pivotMotor.getRotorPosition().getValueAsDouble()/2048 * 360;

  final DutyCycleOut m_stop = new DutyCycleOut(0);

  final DutyCycleOut m_intake = new DutyCycleOut(.5);

  final PositionDutyCycle m_inital = new PositionDutyCycle(initial);

  // 2048/4 as to get 90 degrees in rotational units
  final PositionDutyCycle m_90degrees = new PositionDutyCycle(512);

  //135 degrees
  final PositionDutyCycle m_floor = new PositionDutyCycle(768);

  public intakeSub() {}

  // spins outwards wheels into the robot
  public void intake() {
    current = state.INTAKING;
    pullMotor.set(ControlMode.PercentOutput, .5);
  }

  public void stopIntake() {
    // stop motors on outer intake
    pullMotor.set(ControlMode.PercentOutput, -.5);
    current = state.RESTING;
  }

  public void resetPos() {

    current = state.ROTATING;
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
    current = state.ROTATING;

    //5 volts
  //  double gRatio = 5; **gear ratio for the actual shooting part of the intake

    double diameter = 10;

    double angle = 90;
    
    //convert circumference to value of 1 degree
    double degreeConversion = (diameter * Math.PI)/360;

    double wanted = angle * degreeConversion; 

    //compare???

    while(wanted <= ticks){
      pivotMotor.set(.3);
    }
    //OR!
    pivotMotor.setControl(m_90degrees);

    current = state.INTAKING;

    intake();
    
  }

 

  /** Returns current State as a String */
  public String getState() {
    return current.toString();
  }
}
