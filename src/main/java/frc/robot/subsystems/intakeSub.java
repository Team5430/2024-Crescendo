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

  static TalonFX rotateMotor = new TalonFX(Constants.CANid.rotateMotor);
  static TalonFX pullMotor = new TalonFX(Constants.CANid.pullMotor);

  static double initial = rotateMotor.getRotorPosition().getValueAsDouble();

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
    rotateMotor.setControl(m_inital);
    initial =
        rotateMotor.getRotorPosition().getValueAsDouble(); // update new reset position as initial
    current = state.RESTING;
  }

  public void extendnIntake() {
    current = state.ROTATING;
    // motor spin out using encoders
    current = state.INTAKING;
    intake();
  }

  /** Returns current State as a String */
  public String getState() {
    return current.toString();
  }
}
