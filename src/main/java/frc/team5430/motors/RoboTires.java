package frc.team5430.motors;

import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;

/** custom twist of {@link WPI_TalonFX} */
public class RoboTires extends TalonFX {

  // constants
  private double ratio = 10.71;
  private double circumferenceInInches = 6 * Math.PI;
  private double inches = 12;
  private double totalInches;
  private double motorRotations;
  private double encoderTicks = 2048;

  // can set above varabiales to a default as shown

  public RoboTires(int CANid) {
    super(CANid);
  }

  // individually control motors for distance.
  public void driveInDistance(double feet) {

    final DutyCycleOut m_request = new DutyCycleOut(.5);

    final DutyCycleOut m_stop = new DutyCycleOut(0);

    totalInches = feet * inches;

    motorRotations = (totalInches / circumferenceInInches) * ratio;

    double ticknum = motorRotations * encoderTicks;

    var initial = this.getRotorPosition();

    var posSignal = this.getRotorPosition();

    while ((ticknum + initial.getValue()) >= posSignal.getValueAsDouble()) {

      this.setControl(m_request);
      // refresh as to get the latest input of data.
      posSignal.refresh();
    }
    this.setControl(m_stop);
  }

  // used to modify any constants, though defaults are set
  public void setCircumference(double diameter) {
    circumferenceInInches = diameter * Math.PI;
  }

  public double getCircumference() {
    return circumferenceInInches;
  }

  public void setRatio(double Gratio) {
    ratio = Gratio;
  }

  public double getRatio() {
    return ratio;
  }
}
