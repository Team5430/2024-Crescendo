package frc.robot.subsystems;

import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.PositionDutyCycle;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

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
  static TalonFX pullMotor = new TalonFX(Constants.CANid.pullMotor);

  static double initial = pivotMotor.getRotorPosition().getValueAsDouble();

  state current = state.RESTING;

  final DutyCycleOut m_stop = new DutyCycleOut(0);

  final DutyCycleOut m_intake = new DutyCycleOut(.5);

  final PositionDutyCycle m_inital = new PositionDutyCycle(initial);

  public intakeSub() {}

  // spins outwards wheels into the robot
  public void intake() {
    current = state.INTAKING;
    pullMotor.setControl(m_intake);
  }

  public void stopIntake() {
    // stop motors on outer intake
    pullMotor.setControl(m_stop);
    current = state.RESTING;
  }

  public void resetPos() {

    current = state.ROTATING;
    stopIntake();
    pivotMotor.setControl(m_inital);
    initial = pivotMotor.getRotorPosition().getValueAsDouble(); 
    // update new reset position as initial
    current = state.RESTING;
  }

  public void extendnIntake() {
    current = state.ROTATING;

    //5 volts
    double gRatio = 5;

    double diameter = 10;

    double angle = 90;

    //convert encoder ticks to degrees
    double ticks = pivotMotor.getRotorPosition().getValueAsDouble()/2048 * 360;
    
    //convert circumference to value of 1 degree
    double degreeConversion = (diameter * Math.PI)/360 * gRatio;

    double wanted = angle * degreeConversion; 

    current = state.INTAKING;

    intake();
    
  }

  /** Returns current State as a String */
  public String getState() {
    return current.toString();
  }
}
