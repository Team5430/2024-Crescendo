package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.driveTrain;

import java.util.Optional;

public class autoCenter extends Command {

  /* 
  // example auto; will not do anythhing; runs everything in sequence from right to left
  public autoCenter(driveTrain drive) {
    Optional<Alliance> ali = DriverStation.getAlliance();
    // checks what side you are on
    if (ali.get() == Alliance.Red) {
     
     // Commands.sequence(driveTrain.C_driveInDistance(0), driveTrain.C_turntoAngle(0));

    } else {
      // runs if on blue, or just not connected to FMS
      Commands.sequence();

    }
  }
  */
}
