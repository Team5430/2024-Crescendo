package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.driveTrain;
import frc.robot.subsystems.intakeSub;

public class Autos {

    
    /*By March 3, 2024, all shooters command lines are commented due to absence of shooter, 
    we will not use the shooter by the time of arizona trip
    */

    public static Command autoRight(driveTrain drive, intakeSub intake) {
    return Commands.sequence(
      //shoot.C_ShooterOut(),//gets the shooter ready to shoot
     intake.C_intake(),//makes sure the note is correctly in place
      new WaitCommand(1),//gives the shooter time to shoot
      intake.C_outtake(),//moves the note into the shooter which then flings it out
      new WaitCommand(.5),//lets the note fling out
      intake.C_stopIntake(),//turns off the intake motor
      //shoot.C_ShooterStop(),//turns off the shooter motor
      drive.C_driveinInches(-11.046),//moves backwards 11.046 inches
      drive.C_turntoAngle(-60.02),//turns left 60.02 degrees
      intake.C_extendnintake(),//moves intake to floor position and turns the intake on
      drive.C_driveinInches(-55.5),//moves backwards 55.5 inches
     // intake.C_setPos("Shooter"),//sets the intake to shooter position
      intake.C_stopIntake(),//turns off the intake motor
      drive.C_driveinInches(55.5),//goes forward 55.5 inches
      drive.C_turntoAngle(60.02),//turns right 60.02 degrees
      drive.C_driveinInches(11.046),//goes forward 11.046 inches
      //shoot.C_ShooterOut(),//turns on shooter motor
      new WaitCommand(1),//lets the shooter motor warm up
      intake.C_outtake(),//moves the note into the shooter to then be flung
      new WaitCommand(1),//gives time for the note to be flung out
     // shoot.C_ShooterStop(),//turns off the shooter motor
      intake.C_stopIntake(),//turns off intake motor
      drive.C_driveinInches(-11.046),//moves backwards 11.046 inches
      drive.C_turntoAngle(-60.02),//turns left 60.02 degrees
      drive.C_driveinInches(-55.5),//moves backwards 55.5 inches
      intake.C_resetPos(),//resets the intake pivot position
      drive.C_driveinInches(-18.95),  //drive.C_driveinInches(-18.95),//No idea how this got here, leaving this here just in case
      drive.C_turntoAngle(73.78)//drive.C_turntoAngle(73.78)//somebody needed it
          );
 
  }

  public static Command autoLeft(driveTrain drive, intakeSub intake) {
     return Commands.sequence(

     
      //shoot.C_ShooterOut(),//turns on the shooter motor
      intake.C_intake(),//makes sure the note is properly secure
      new WaitCommand(1),//lets the shooter warm up to be at speed
      intake.C_outtake(),//moves the note into the shooter to then be flung
      new WaitCommand(1.0),//lets the note be flung out with time
      intake.C_stopIntake(),//turns off the intake motor
      //shoot.C_ShooterStop(),//turns off the shooter motor
      drive.C_driveinInches(11.046),//moves backwards 11.046 inches
      drive.C_turntoAngle(60.02),//turns the robot 60.02 degrees right
      intake.C_extendnintake(),//moves the intake to the floor pivot positionand turns on the intake motor
      drive.C_driveinInches(-55.5),//moves backwards 55.5 inches, picking up the note in a sweep
      intakeSub.C_setShoot(),//moves the intake to the shooter position
      intake.C_stopIntake(),//turns off the intake motor
      drive.C_driveinInches(55.5),//moves forward 55.5 inches
      drive.C_turntoAngle(-60.02),//turns left 60.02 degrees
      drive.C_driveinInches(11.046),//moves forward 11.046 inches
      //shoot.C_ShooterOut(),//turns on the shooter motor
      new WaitCommand(1),//lets the shooter warm up
      intake.C_outtake(),//makes the intake push the note into the shooter
      new WaitCommand(.5),//lets the note be flung by the shooter into the speaker
      //shoot.C_ShooterStop(),//turns off the shooter motor
      intake.C_stopIntake(),//turns off the intake motor
      drive.C_driveinInches(-11.046),//drives backwards 11.046 inches
      drive.C_turntoAngle(60.02),//turns the robot 60.02 degrees right
      drive.C_driveinInches(-55.5),//moves backwards 55.5 inches
      intake.C_resetPos()//moves the intake into the shooter position
      
    


    );
  }
  public static Command autoCenter(driveTrain drive, intakeSub intake) {
        return Commands.sequence(
           
          //shoot.C_ShooterOut(),  //shootes out the note
         new WaitCommand(1),//lets shooter speed up
          intake.C_outtake(),    //outtakes the intake
          intake.C_extendnintake(),//extends the intake
         new WaitCommand(1),
         intake.C_stopIntake(),
          //shoot.C_ShooterOut(),  //shootes out the note
          drive.C_driveinInches(-36),
          //intake.C_setPos("Shooter"),// Set the intake to a shooter position
          intake.C_stopIntake(),  //Stop the intake 
          new WaitCommand(5),
          drive.C_driveinInches(36),
          intake.C_outtake(),    //outtakes the intake 
          //shoot.C_ShooterOut(),  //Shoots out the note
          //shoot.C_ShooterStop(), //Stops the shooter from shooting
          intake.C_stopIntake() //Stops the intake from shooting
        );
    
      }
      
    }


