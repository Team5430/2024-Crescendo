package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.driveTrain;
import frc.robot.subsystems.shooterSub;

public class auto3 extends Command {
    
    public auto3(driveTrain drive, shooterSub shoot){
        Commands.sequence(null);
    }
}
