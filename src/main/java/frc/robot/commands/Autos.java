package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.driveTrain;

public class Autos {
    
    public static Command autoRight(driveTrain drive) {
//commented actions are temporary for testing
    return Commands.sequence(
      //shoot.C_ShooterOut(),//gets the shooter ready to shoot
     //intake.C_intake(),//makes sure the note is correctly in place
      //intake.C_waitCommand(1),//gives the shooter time to shoot
      //intake.C_outtake(),//moves the note into the shooter which then flings it out
      //intake.C_waitCommand(.5),//lets the note fling out
      //intake.C_stopIntake(),//turns off the intake motor
      //shoot.C_ShooterStop(),//turns off the shooter motor
      drive.C_driveinInches(-11.046),//moves backwards 11.046 inches
      drive.C_turntoAngle(-60.02),//turns left 60.02 degrees
      //intake.C_extendnintake(),//moves intake to floor position and turns the intake on
      drive.C_driveinInches(-55.5),//moves backwards 55.5 inches
      //intake.C_setPos("Shooter"),//sets the intake to shooter position
      //intake.C_stopIntake(),//turns off the intake motor
      drive.C_driveinInches(55.5),//goes forward 55.5 inches
      drive.C_turntoAngle(60.02),//turns right 60.02 degrees
      drive.C_driveinInches(11.046),//goes forward 11.046 inches
      //shoot.C_ShooterOut(),//turns on shooter motor
      //intake.C_waitCommand(1),//lets the shooter motor warm up
      //intake.C_outtake(),//moves the note into the shooter to then be flung
      //intake.C_waitCommand(.5),//gives time for the note to be flung out
      //shoot.C_ShooterStop(),//turns off the shooter motor
      //intake.C_stopIntake(),//turns off intake motor
      drive.C_driveinInches(-11.046),//moves backwards 11.046 inches
      drive.C_turntoAngle(-60.02),//turns left 60.02 degrees
      drive.C_driveinInches(-55.5)//moves backwards 55.5 inches
      //intake.C_resetPos()//resets the intake pivot position
    );
 
  }

  public static Command autoLeft(driveTrain drive) {
    //commented actions are temporary for testing
     return Commands.sequence(
      //shoot.C_ShooterOut(),//turns on the shooter motor
      //intake.C_intake(),//makes sure the note is properly secure
      //intake.C_waitCommand(1),//lets the shooter warm up to be at speed
      //intake.C_outtake(),//moves the note into the shooter to then be flung
      //intake.C_waitCommand(.5),//lets the note be flung out with time
      //intake.C_stopIntake(),//turns off the intake motor
      //shoot.C_ShooterStop(),//turns off the shooter motor
      drive.C_driveinInches(11.046),//moves backwards 11.046 inches
      drive.C_turntoAngle(60.02),//turns the robot 60.02 degrees right
      //intake.C_extendnintake(),//moves the intake to the floor pivot positionand turns on the intake motor
      drive.C_driveinInches(-55.5),//moves backwards 55.5 inches, picking up the note in a sweep
      //intake.C_setPos("Shooter"),//moves the intake to the shooter position
      //intake.C_stopIntake(),//turns off the intake motor
      drive.C_driveinInches(55.5),//moves forward 55.5 inches
      drive.C_turntoAngle(-60.02),//turns left 60.02 degrees
      drive.C_driveinInches(11.046),//moves forward 11.046 inches
      //shoot.C_ShooterOut(),//turns on the shooter motor
      //intake.C_waitCommand(1),//lets the shooter warm up
      //intake.C_outtake(),//makes the intake push the note into the shooter
      //intake.C_waitCommand(.5),//lets the note be flung by the shooter into the speaker
      //shoot.C_ShooterStop(),//turns off the shooter motor
      //intake.C_stopIntake(),//turns off the intake motor
      drive.C_driveinInches(-11.046),//drives backwards 11.046 inches
      drive.C_turntoAngle(60.02),//turns the robot 60.02 degrees right
      drive.C_driveinInches(-55.5)//moves backwards 55.5 inches
      //intake.C_resetPos()//moves the intake into the shooter position
    );
  }
  public static Command autoCenter(driveTrain drive) {
    //commented actions are temporary for testing
        return Commands.sequence(
          //shoot.C_ShooterOut(),
          //intake.C_outtake(),
          //intake.C_extendnintake(),
          drive.C_driveinInches(-36),
          //intake.C_setPos("Shooter"),
          //intake.C_stopIntake(),
          drive.C_driveinInches(36),
          //intake.C_outtake(),
          //shoot.C_ShooterStop(),
          //intake.C_stopIntake(),
          drive.C_driveinInches(-36)
        );
    
      }
    }


