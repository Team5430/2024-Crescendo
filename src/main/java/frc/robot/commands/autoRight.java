package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.driveTrain;
import frc.robot.subsystems.intakeSub;
import frc.robot.subsystems.shooterSub;


public class autoRight extends Command {


  public autoRight(driveTrain drive, shooterSub shoot, intakeSub intake) {

    Commands.sequence(
      shoot.C_ShooterOut(),//gets the shooter ready to shoot
      intake.C_intake(),//makes sure the note is correctly in place
      intake.C_waitCommand(1),//gives the shooter time to shoot
      intake.C_outtake(),//moves the note into the shooter which then flings it out
      intake.C_waitCommand(.5),//lets the note fling out
      intake.C_stopIntake(),//turns off the intake motor
      shoot.C_ShooterStop(),//turns off the shooter motor
      drive.C_driveinInches(-11.046),//moves backwards 11.046 inches
      drive.C_turntoAngle(-60.02),//turns left 60.02 degrees
      intake.C_extendnintake(),//moves intake to floor position and turns the intake on
      drive.C_driveinInches(-55.5),//moves backwards 55.5 inches
      intake.C_setPos("Shooter"),//sets the intake to shooter position
      intake.C_stopIntake(),//turns off the intake motor
      drive.C_driveinInches(55.5),//goes forward 55.5 inches
      drive.C_turntoAngle(60.02),//turns right 60.02 degrees
      drive.C_driveinInches(11.046),//goes forward 11.046 inches
      shoot.C_ShooterOut(),//turns on shooter motor
      intake.C_waitCommand(1),//lets the shooter motor warm up
      intake.C_outtake(),//moves the note into the shooter to then be flung
      intake.C_waitCommand(.5),//gives time for the note to be flung out
      shoot.C_ShooterStop(),//turns off the shooter motor
      intake.C_stopIntake(),//turns off intake motor
      drive.C_driveinInches(-11.046),//moves backwards 11.046 inches
      drive.C_turntoAngle(-60.02),//turns left 60.02 degrees
      drive.C_driveinInches(-55.5),//moves backwards 55.5 inches
      intake.C_resetPos()//resets the intake pivot position
    );
 
  }
  
}
