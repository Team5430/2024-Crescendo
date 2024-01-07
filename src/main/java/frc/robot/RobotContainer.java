// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.auto;
import frc.robot.subsystems.driveTrain;

public class RobotContainer {
  
  //subsystems
  private driveTrain m_driveTrain = new driveTrain();

  private CommandJoystick L_Joy = new CommandJoystick(Constants.OperatorC.L_Joy);
  private CommandJoystick R_Joy = new CommandJoystick(Constants.OperatorC.L_Joy);
  
  public RobotContainer() {
    configureBindings();
    m_driveTrain.motorSettings();

    m_driveTrain.setDefaultCommand(m_driveTrain.C_drive(L_Joy.getY(), R_Joy.getY()));
  }

  private void configureBindings() {

//trigger first, then the use of it
    Trigger L_joyButton = L_Joy.button(3);
    Trigger R_joyButton = R_Joy.button(3);

    L_joyButton.onTrue(new InstantCommand(m_driveTrain:: VariableSpeedIncrease, m_driveTrain));
    R_joyButton.onTrue(new InstantCommand(m_driveTrain:: VariableSpeedDecrease, m_driveTrain));

  }

  public Command getAutonomousCommand() {
    return auto.defaultAutonomous(m_driveTrain);
  }
}
