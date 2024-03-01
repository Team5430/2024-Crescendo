// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.Autos;
import frc.robot.subsystems.driveTrain;
import frc.robot.subsystems.hangSub;
import frc.robot.subsystems.intakeSub;
import frc.robot.subsystems.shooterSub;

public class RobotContainer {

  // subsystems
  private driveTrain m_driveTrain = new driveTrain();
  private hangSub m_HangSub = new hangSub();
  private intakeSub m_IntakeSub = new intakeSub();
  

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

    //m_intakeSub.motorConfig();
//Temporary Comment out
    //m_shooterSub.motorConfig();

    //drive active input during match
    m_driveTrain.setDefaultCommand(
     new RunCommand( () -> m_driveTrain.drive(L_Joy.getY(), R_Joy.getY()), m_driveTrain));
    m_IntakeSub.setDefaultCommand(
     new RunCommand( () -> m_IntakeSub.IntakeControl(CO_Con.getRawAxis(5)), m_IntakeSub)); // makes intake pivot motor controllable by right joystick of controller
     
    // shuffleboard options
  
    m_chooser.addOption("Right", Autos.autoRight(m_driveTrain));
    m_chooser.setDefaultOption("Left", Autos.autoLeft(m_driveTrain));
    m_chooser.addOption("Center", Autos.autoCenter(m_driveTrain, m_IntakeSub));

    Shuffleboard.getTab("Auton")
     .add("AutonChoice", m_chooser);
    
  }

  private void configureBindings() {


    // trigger first, then the use of it
    Trigger R_joyButton = R_Joy.button(3);
    Trigger A_Button = CO_Con.button(1);
    Trigger B_Button = CO_Con.button(2);
    Trigger UP_DPad = CO_Con.povUp();
    Trigger DOWN_DPad = CO_Con.povDown();
    Trigger RIGHT_DPad = CO_Con.povRight();
  
    R_joyButton.onTrue(new InstantCommand(m_driveTrain::VariableSpeedIncrease, m_driveTrain));
    R_joyButton.onFalse(new InstantCommand(m_driveTrain::VariableSpeedDecrease, m_driveTrain));
     
    UP_DPad.onTrue(m_IntakeSub.C_setPos("Shooter"));
    DOWN_DPad.onTrue(m_IntakeSub.C_setPos("Floor"));
    RIGHT_DPad.onTrue(m_IntakeSub.C_setPos("Amp"));
     
  

    //A_Button.onTrue(new InstantCommand(m_HangSub::extendclimbertimer, m_HangSub));
   
   // B_Button.onTrue(new InstantCommand(m_IntakeSub::outtake, m_IntakeSub));
   // B_Button.onFalse(new InstantCommand(m_IntakeSub::stopIntake, m_IntakeSub));

    A_Button.onTrue(m_HangSub.C_pullinTime(1, 0.39));
   
        
   //A_Button.toggleOnTrue(new InstantCommand(m_HangSub::C_Doe, m_HangSub));

   // B_Button.onTrue(new InstantCommand(m_HangSub:: C_ExtendClimberTimer));
  }

  public Command getAutonomousCommand() {
    return m_chooser.getSelected();
  }
}
