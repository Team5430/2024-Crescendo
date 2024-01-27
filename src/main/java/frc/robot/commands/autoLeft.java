package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.driveTrain;

import java.util.Optional;

public class autoLeft extends Command {

  
  public autoLeft(driveTrain drive) {
    /* 
    Optional<Alliance> ali = DriverStation.getAlliance();

    if (ali.get() == Alliance.Red) {
      /* Commands.sequence(
       driveTrain.turntoAngle(-6); //Turn clockwise a little to left to line up and back up
        driveTrain.C_driveInDistance(-4); //get out of the friendly zone
        driveTrain.C_turntoAngle(12); //turn clockwise to the ampstation
        driveTrain.C_driveInDistance(4); //get to amp station
        
    );
    

    } else {

      Commands.sequence(null);
    }

    */
  }
  
}
