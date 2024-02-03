package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.driveTrain;
import frc.robot.subsystems.intakeSub;
import frc.robot.subsystems.shooterSub;


public class autoLeft extends Command {

  
  public autoLeft(driveTrain drive, shooterSub shoot, intakeSub intake) {
    
    var ali = DriverStation.getAlliance();

    if (ali.get().toString() == Alliance.Red.toString()) {
       Commands.sequence(

       drive.C_turntoAngle(-6), //Turn clockwise a little to left to line up and back up
        drive.C_driveinFeet(-4), //get out of the friendly zone
        drive.C_turntoAngle(12), //turn clockwise to the ampstation
        drive.C_driveinFeet(4) //get to amp station 

       );
  
    } else {

      Commands.sequence(null);
    }

    
  }
  
}
