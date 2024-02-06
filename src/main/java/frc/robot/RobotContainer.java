// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.autoCenter;
import frc.robot.commands.autoLeft;
import frc.robot.commands.autoRight;
import frc.robot.subsystems.driveTrain;
import frc.robot.subsystems.intakeSub;
import frc.robot.subsystems.shooterSub;

public class RobotContainer {

  // subsystems
  private driveTrain m_driveTrain = new driveTrain();
  private shooterSub m_shooterSub = new shooterSub();
  private intakeSub m_intakeSub = new intakeSub();


  public CommandJoystick L_Joy = new CommandJoystick(Constants.OperatorC.L_Joy);
  public CommandJoystick R_Joy = new CommandJoystick(Constants.OperatorC.R_Joy);

  private CommandJoystick CO_Con = new CommandJoystick(Constants.OperatorC.CO_Con);
  // chooser for auton
  public SendableChooser<Command> m_chooser = new SendableChooser<>();

  public RobotContainer() {
    // default commands

    configureBindings();

    //motor configs
    m_driveTrain.motorConfig();

    m_intakeSub.motorConfig();

    m_shooterSub.motorConfig();

    //drive active input during match
    m_driveTrain.setDefaultCommand(
      new RunCommand( () -> m_driveTrain.drive(L_Joy.getY(), R_Joy.getY()), m_driveTrain));

    // autonmous routines
    
    Command auto_1 = new autoCenter(m_driveTrain, m_shooterSub, m_intakeSub);
    Command auto_2 = new autoLeft(m_driveTrain, m_shooterSub, m_intakeSub);
    Command auto_3 = new autoRight(m_driveTrain, m_shooterSub, m_intakeSub);

    
    // shuffleboard options

    m_chooser.addOption("Auton 1", auto_1);
    m_chooser.addOption("Auton 2", auto_2);
    m_chooser.addOption("Auton 3", auto_3);

    
  }

  private void configureBindings() {

    // trigger first, then the use of it
    Trigger R_joyButton = R_Joy.button(3);

    R_joyButton.onTrue(new InstantCommand(m_driveTrain::VariableSpeedIncrease, m_driveTrain));
    R_joyButton.onFalse(new InstantCommand(m_driveTrain::VariableSpeedDecrease, m_driveTrain));
    
  }

  public Command getAutonomousCommand() {
    return m_chooser.getSelected();
  }
}
