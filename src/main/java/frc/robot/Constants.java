package frc.robot;

public class Constants {

  public static double multiplier = 0;
  public static double gyroPos = 0;
  public static double ratio = 10.71;
  public static double encoderTicks = 2048;
  public static double circumferenceInInches = 6 * Math.PI;
  public static double inches = 12;

  

  public static class CANid {
    // Talons CANid
    public static int backLeftMotor = 2;
    public static int frontLeftMotor = 4;
    public static int backRightMotor = 3;
    public static int frontRightMotor = 5;
    public static int shooterMotor;
    public static int pivotMotor;
    public static int intakeMotor;
    public static int hang_motor;
  }

  public static class OperatorC {
    public static int CO_Con = 0;
    public static int L_Joy = 1;
    public static int R_Joy = 2;
  }
}
