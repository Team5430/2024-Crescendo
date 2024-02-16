package frc.robot;

public class Constants {
//multiplier must not be 0
  public static double multiplier = .5;
  public static double gyroPos = 0;
  public static double ratio = 10.71;
  public static double encoderTicks = 2048;
  public static double circumferenceInInches = 6 * Math.PI;
  public static double inches = 12;
  public static double Iratio = 7/3; 

  public static class CANid {
    public static final int hangmotor = 0;
    // Talons CANid
    public static int backLeftMotor = 2;
    public static int frontLeftMotor = 4;
    public static int backRightMotor = 3;
    public static int frontRightMotor = 5;
    public static int shooterMotor = 7;
    public static int pivotMotor = 9;
    public static int intakeMotor = 10;
  }

  public static class OperatorC {
    public static int CO_Con = 0;
    public static int L_Joy = 1;
    public static int R_Joy = 2;
  }
}
