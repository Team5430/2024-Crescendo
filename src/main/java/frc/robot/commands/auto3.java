package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.driveTrain;
import frc.robot.subsystems.intakeSub;
import frc.robot.subsystems.shooterSub;
import java.util.Optional;

public class auto3 extends Command {

  public auto3(driveTrain drive, shooterSub shoot, intakeSub intake) {

    Optional<Alliance> ali = DriverStation.getAlliance();

    if (ali.get() == Alliance.Red) {
      Commands.sequence(null);

    } else {

      Commands.sequence(null);
    }
  }
}
