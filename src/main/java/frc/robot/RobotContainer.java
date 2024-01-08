// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.auto;
import frc.robot.commands.auto2;
import frc.robot.commands.auto3;
import frc.robot.subsystems.driveTrain;
import frc.robot.subsystems.intakeSub;
import frc.robot.subsystems.shooterSub;

public class RobotContainer {
  
  //subsystems
  private driveTrain m_driveTrain = new driveTrain();
  private shooterSub m_ShooterSub = new shooterSub();
  private intakeSub m_IntakeSub = new intakeSub();

  private CommandJoystick L_Joy = new CommandJoystick(Constants.OperatorC.L_Joy);
  private CommandJoystick R_Joy = new CommandJoystick(Constants.OperatorC.R_Joy_Joy);

  private CommandJoystick CO_Con = new CommandJoystick(Constants.OperatorC.CO_Con);
  //chooser for auton
  public SendableChooser<Command> m_chooser = new SendableChooser<>();

  
  public RobotContainer() {
    //default commands
    
    configureBindings();
    m_driveTrain.motorSettings();

    m_driveTrain.setDefaultCommand(m_driveTrain.C_drive(L_Joy.getY(), R_Joy.getY()));

    //commands

      Command auto_1 = new auto(m_driveTrain, m_ShooterSub, m_IntakeSub);
      Command auto_2 = new auto2(m_driveTrain, m_ShooterSub, m_IntakeSub);
      Command auto_3 = new auto3(m_driveTrain, m_ShooterSub, m_IntakeSub);

  
    //shuffleboard options

    m_chooser.addOption("Auton 1", auto_1);
    m_chooser.addOption("Auton 2", auto_2);
    m_chooser.addOption("Auton 3", auto_3);
    
  }

  private void configureBindings() {

//trigger first, then the use of it
    Trigger R_joyButton = R_Joy.button(3);

    R_joyButton.onTrue(new InstantCommand(m_driveTrain:: VariableSpeedIncrease, m_driveTrain));
    R_joyButton.onFalse(new InstantCommand(m_driveTrain:: VariableSpeedDecrease, m_driveTrain));
  }
  
  public Command getAutonomousCommand() {
    return m_chooser.getSelected();
  }
}
