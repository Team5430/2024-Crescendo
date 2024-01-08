package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.driveTrain;
import frc.robot.subsystems.shooterSub;

public class auto extends Command {

//example auto; will not do anythhing; runs everything in sequence from right to left
    public auto(driveTrain drive, shooterSub shoot){
        Commands.sequence(drive.C_driveInDistance(0), drive.C_drive(0, 0)); 
    }
    
}