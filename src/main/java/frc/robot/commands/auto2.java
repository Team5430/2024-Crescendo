package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.driveTrain;
import frc.robot.subsystems.shooterSub;

public class auto2 extends Command{
    
    public auto2(driveTrain drive, shooterSub shoot){
        Commands.sequence(null);
    }
}
