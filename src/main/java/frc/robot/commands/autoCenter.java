package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.driveTrain;
import frc.robot.subsystems.intakeSub;
import frc.robot.subsystems.shooterSub;


public class autoCenter extends Command {

  
  public autoCenter(driveTrain drive, shooterSub shoot, intakeSub intake) {

    Commands.sequence(
      shoot.C_ShooterOut(),
      intake.C_outtake(),
      intake.C_extendnintake(),
      drive.C_driveinInches(-36),
      intake.C_setPos("Shooter"),
      intake.C_stopIntake(),
      drive.C_driveinInches(36),
      intake.C_outtake(),
      shoot.C_ShooterStop(),
      intake.C_stopIntake(),
      drive.C_driveinInches(-36)
    );

  }
  
}
