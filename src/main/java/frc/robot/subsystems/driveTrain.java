package frc.robot.subsystems;

import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.signals.NeutralModeValue;
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
  state current = state.RESTING;

  static final RoboTires backLeftMotor = new RoboTires(Constants.CANid.backLeftMotor);
  static final RoboTires frontLeftMotor = new RoboTires(Constants.CANid.frontLeftMotor);
  static final RoboTires backRightMotor = new RoboTires(Constants.CANid.backRightMotor);
  static final RoboTires frontRightMotor = new RoboTires(Constants.CANid.frontRightMotor);

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
  public void driveInDistance(double feet) {
    current = state.SETTING;
    backLeftMotor.driveInDistance(feet);
    frontLeftMotor.driveInDistance(feet);
    backRightMotor.driveInDistance(feet);
    frontRightMotor.driveInDistance(feet);
  }

  public Command C_driveInDistance(double feet) {
    return new InstantCommand(() -> driveInDistance(feet));
  }

  /** Returns current State as a String */
  public String getState() {
    return current.toString();
  }

  @Override
  public void periodic() {}
}
