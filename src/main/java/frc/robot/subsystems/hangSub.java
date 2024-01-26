package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.robot.Constants;

public class hangSub {

    private enum state {
        RESTING,
        HANGING,
        DEHANGING
      }
      //state of robot
      state current = state.RESTING;
     // Shows the motor for hang
  static TalonSRX hang_motor = new TalonSRX(Constants.CANid.hang_motor);

   //Activate hang
   public  void HangActivate() {
    current = state.HANGING;
    hang_motor.set(ControlMode.PercentOutput, 0.5);
    
   }

  public  void HangStop() {
    current = state.DEHANGING;
    hang_motor.set(ControlMode.PercentOutput, 0);
    
  }
}
