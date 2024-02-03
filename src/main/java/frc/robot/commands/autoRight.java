package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.driveTrain;
import frc.robot.subsystems.intakeSub;
import frc.robot.subsystems.shooterSub;


public class autoRight extends Command {


  public autoRight(driveTrain drive, shooterSub shoot, intakeSub intake) {

    Commands.sequence(
      shoot.C_ShooterOut(),
      intake.C_intake(),
      intake.C_waitCommand(1),
      intake.C_outtake(),
      intake.C_stopIntake(),
      shoot.C_ShooterStop(),
      drive.C_driveinInches(-11.046),
      drive.C_turntoAngle(-60.02),
      intake.C_extendnintake(),
      drive.C_driveinInches(-55.5),
      intake.C_setPos("Shooter"),
      intake.C_stopIntake(),
      drive.C_driveinInches(55.5),
      drive.C_turntoAngle(60.02),
      drive.C_driveinInches(11.046),
      shoot.C_ShooterOut(),
      intake.C_waitCommand(1),
      intake.C_outtake(),
      shoot.C_ShooterStop(),
      intake.C_stopIntake(),
      drive.C_driveinInches(-11.046),
      drive.C_turntoAngle(-60.02),
      drive.C_driveinInches(-55.5),
      intake.C_resetPos()
    );
 
  }
  
}
