package frc.robot;

public class Constants {
//multiplier must not be 0
  public static double multiplier = .5;
  
  public static double gyroPos = 0;
  public static double ratio = 10.71;
  public static double encoderTicks = 2048;
  public static double circumferenceInInches = 6 * Math.PI;
  public static double inches = 12;
  public static double Iratio = 12/56; 
  public static double degree = 1.0891/90;

  public static class CANid {
    public static  int L_hangmotor = 2;
    public static int R_hangmotor = 1;
    //public static final int shooterMotor = 15;

    // Talon CANid's
    public static int backLeftMotor = 5;
    public static int frontLeftMotor = 4;
    public static int backRightMotor = 3;
    public static int frontRightMotor = 6;
    public static int shooterMotor = 15;
    public static int pivotMotor = 7;
    public static int intakeMotor = 8;
    public static int transversalMotor = 9;
  }

  public static class OperatorC {
    //Sets the controller in USB order from driver station
    public static int CO_Con = 0;
    //sets the left stick in USB order from driver station
    public static int L_Joy = 1;
    //sets the right stick in USB order from driver station
    public static int R_Joy = 2;
  }
}
