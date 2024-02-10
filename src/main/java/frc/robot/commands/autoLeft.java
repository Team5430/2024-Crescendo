package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.driveTrain;
import frc.robot.subsystems.intakeSub;
import frc.robot.subsystems.shooterSub;


public class autoLeft extends Command {

  
  public autoLeft(driveTrain drive) {
    //commented actions are temporary for testing
    Commands.sequence(
      //shoot.C_ShooterOut(),//turns on the shooter motor
      //intake.C_intake(),//makes sure the note is properly secure
      //intake.C_waitCommand(1),//lets the shooter warm up to be at speed
      //intake.C_outtake(),//moves the note into the shooter to then be flung
      //intake.C_waitCommand(.5),//lets the note be flung out with time
      //intake.C_stopIntake(),//turns off the intake motor
      //shoot.C_ShooterStop(),//turns off the shooter motor
      drive.C_driveinInches(-11.046),//moves backwards 11.046 inches
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
  
}
/*var ali = DriverStation.getAlliance();

    if (ali.get().toString() == Alliance.Red.toString()) {
       Commands.sequence(

       drive.C_turntoAngle(-6), //Turn clockwise a little to left to line up and back up
        drive.C_driveinFeet(-4), //get out of the friendly zone
        drive.C_turntoAngle(12), //turn clockwise to the ampstation
        drive.C_driveinFeet(4) //get to amp station 

       );
  
    } else {

      Commands.sequence(drive.C_drive(0, 0));
    } */
