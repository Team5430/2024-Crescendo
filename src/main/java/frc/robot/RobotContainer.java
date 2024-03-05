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

public class RobotContainer {

  // subsystems as variables
  private driveTrain m_driveTrain = new driveTrain();
  private hangSub m_HangSub = new hangSub();
  private intakeSub m_IntakeSub = new intakeSub();
 // private shooterSub m_shooterSub = new shooterSub();

  //Joysticks are initialized as CommandJoysticks
  public CommandJoystick L_Joy = new CommandJoystick(Constants.OperatorC.L_Joy);
  public CommandJoystick R_Joy = new CommandJoystick(Constants.OperatorC.R_Joy);

  private CommandJoystick CO_Con = new CommandJoystick(Constants.OperatorC.CO_Con); //Controller is initalized as CommandJoySticks
  
  public SendableChooser<Command> m_chooser = new SendableChooser<>(); //  object|scope for auton choosing

  public RobotContainer() {
    // default commands

    configureBindings();

    //motor configs
    m_driveTrain.motorConfig();

    m_IntakeSub.motorConfig();

    //m_shooterSub.motorConfig(); - Temporary commented

    //drive active input during match
   m_driveTrain.setDefaultCommand(
    new RunCommand( () -> m_driveTrain.drive(L_Joy.getY(), R_Joy.getY()), m_driveTrain)); //The drivetrain control will be set as defaultCommand
    //DefaultCommand will run first then all of the subsystems 
    // newRunCommand( () -> m_HangSub.)
     //new RunCommand( () -> 
    // shuffleboard options
  
  
    m_chooser.addOption("Right", Autos.autoRight(m_driveTrain, m_IntakeSub));
    m_chooser.setDefaultOption("Left", Autos.autoLeft(m_driveTrain, m_IntakeSub));
    m_chooser.addOption("Center", Autos.autoCenter(m_driveTrain, m_IntakeSub));

    Shuffleboard.getTab("Auton")
     .add("AutonChoice", m_chooser);
    
  }

  private void configureBindings() { //initializes and binds all the buttons to methods inside subsystem class

//Code down here are buttons from joysticks/gamepad intialized and used for subsystems
  //word before Trigger is button, word before = is functions act the buttons
  //X = 3 Y = 4 L1 = 5 R1 = 6 Back arrow = 7 Forward arrow = 8 Left joystick = 9 Right joystick = 10
    Trigger A_Button = CO_Con.button(1); 
    Trigger B_Button = CO_Con.button(2);
    Trigger X_Button = CO_Con.button(3);
    Trigger Y_Button = CO_Con.button(4);
    Trigger L1_Button = CO_Con.button(5);
    Trigger R2_Button = CO_Con.button(6);
    Trigger Back_Arrow = CO_Con.button(7);
    Trigger Forward_Arrow = CO_Con.button(8);
    Trigger LeftJoystick_Button = CO_Con.button(9);
    Trigger RightJoystick_Button = CO_Con.button(10);  
    Trigger UP_DPad = CO_Con.povUp();
    Trigger DOWN_DPad = CO_Con.povDown();
    Trigger RIGHT_DPad = CO_Con.povRight();

    //Code down here are buttons assigned to the specific methods/actions from subsystems
    //in "instantCommand( ));" is a methods from subsystem binded to the buttona
  
   UP_DPad.onTrue(new InstantCommand(intakeSub::setShoot)); // (deprecated) Dpad for intake to move up in shoot position to score speaker
  DOWN_DPad.onTrue(new InstantCommand(intakeSub::setFloor)); //Dpad down for intake to move in floor position to intake note
  RIGHT_DPad.onTrue(new InstantCommand(intakeSub::setFloor)); //Dpad right for intake to move into Amp Position
      
  A_Button.and(B_Button).whileFalse(new InstantCommand(intakeSub::C_stopIntake));
  A_Button.onTrue(new InstantCommand(intakeSub:: C_outtake));
  B_Button.onTrue(new InstantCommand(intakeSub:: C_intake));
      //Junkyards below, but these won't be deleted because we might need them

    // trigger first, then the use of it
    //Trigger R_joyButton = R_Joy.button(3);
      //  Trigger B_Button = CO_Con.button(2);
          //Trigger Up_L_Joystick = CO_Con.axisGreaterThan(1, 0);
    //R_joyButton.onTrue(new InstantCommand(m_driveTrain::VariableSpeedIncrease, m_driveTrain));
    //R_joyButton.onFalse(new InstantCommand(m_driveTrain::VariableSpeedDecrease, m_driveTrain)); 
    //A_Button.onTrue(new InstantCommand(intakeSub::C_intake));
    // Goes to shooter point after pressing up
    //A_Button.onTrue(new InstantCommand(m_HangSub::extendclimbertimer, m_HangSub));
   // B_Button.onTrue(new InstantCommand(m_IntakeSub::outtake, m_IntakeSub));
   // B_Button.onFalse(new InstantCommand(m_IntakeSub::stopIntake, m_IntakeSub));
    A_Button.onTrue(m_HangSub.C_pullinTime(1.8, -0.6)); 
    //B_Button.onTrue(m_HangSub.C_releaseInTime(3, 0.4)); 
   //A_Button.toggleOnTrue(new InstantCommand(m_HangSub::C_Doe, m_HangSub));
   // B_Button.onTrue(new InstantCommand(m_HangSub:: C_ExtendClimberTimer));
  }

  public Command getAutonomousCommand() {
    return m_chooser.getSelected();
  }
}
