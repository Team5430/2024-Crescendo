package frc.robot;

public class Constants {
//multiplier must not be 0
  public static double multiplier = .5;
  
  //
  public static double gyroPos = 0;
  //represents the amount of rotations the motor needs in order to make one full gear rotation
  public static double ratio = 10.71;
  //represents an increase or decrease in the number that the related software gives.
  public static double encoderTicks = 2048;
  //represents the total length of the wheel
  public static double circumferenceInInches = 6 * Math.PI;
  //represents number of inches in a foot
  public static double inches = 12;
  // ratio of the intake
  public static double Iratio = 56/12; 
  // represents the amount of gear ratio * rotations needed for 1 degree of pivot rotation
  public static double degree = 1.0891/90;
  // the time it takes to pull the arms down
  public static double pulltime = .9;
  //the power the hang will be at
  public static double pullpower = -0.35;
  // the power the driveininches will be at
  public static double driveinInchespower = 0.3;

  public static double delay = 1;
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
