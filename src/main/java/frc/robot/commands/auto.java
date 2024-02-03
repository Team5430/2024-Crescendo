package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.driveTrain;
import frc.robot.subsystems.intakeSub;
import frc.robot.subsystems.shooterSub;
import java.util.Optional;

public class auto extends Command {

  // example auto; will not do anythhing; runs everything in sequence from right to left
  public auto(driveTrain drive, shooterSub shoot, intakeSub intake) {

    Commands.sequence(drive.C_driveInDistance(4),drive.C_driveInDistance(5));

  }
}
