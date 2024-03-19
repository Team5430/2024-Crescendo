// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
  
  public SendableChooser<Command> m_chooser = new SendableChooser<>(); // object|scope for auton choosing

  public RobotContainer() {

    configureBindings();

    //motor configs
    m_driveTrain.motorConfig();
    m_IntakeSub.motorConfig();

    // Default command for Drive Train
    m_driveTrain.setDefaultCommand(
    new RunCommand( () -> m_driveTrain.drive(L_Joy.getY(), R_Joy.getY()), m_driveTrain)); //The drivetrain control will be set as defaultCommand

    // shuffleboard options
  
    m_chooser.setDefaultOption("Run This One", Autos.panicAuto(m_driveTrain));
    //m_chooser.addOption("Right", Autos.autoRight(m_driveTrain, m_IntakeSub));
    //m_chooser.addOption("Center", Autos.autoCenter(m_driveTrain, m_IntakeSub));
    //m_chooser.addOption("Left", Autos.autoLeft(m_driveTrain, m_IntakeSub));

    Shuffleboard.getTab("Auton")
     .add("AutonChoice", m_chooser);

    SmartDashboard.putNumber("Delay", Constants.delay);
    
  }

  private void configureBindings() { //initializes and binds all the buttons to methods inside subsystem class

  //Code down here are buttons from joysticks/gamepad intialized and used for subsystems
  //word before Trigger is button, word before = is functions act the buttons
  //X = 3 Y = 4 L1 = 5 R1 = 6 Back arrow = 7 Forward arrow = 8 Left joystick = 9 Right joystick = 10
    Trigger A_Button = CO_Con.button(1); 
    Trigger B_Button = CO_Con.button(2);

    Trigger L1_Button = CO_Con.button(5);
    Trigger R1_Button = CO_Con.button(6);

    Trigger R_joyButton = R_Joy.button(3);
    Trigger L_JoyButton = L_Joy.button(3);

    Trigger UP_DPad = CO_Con.povUp();
    Trigger DOWN_DPad = CO_Con.povDown();
    Trigger RIGHT_DPad = CO_Con.povRight();

    //Code down here are buttons assigned to the specific methods/actions from subsystems
    //in "instantCommand( ));" is a methods from subsystem binded to the buttona

    R_joyButton.onTrue(new InstantCommand(m_driveTrain::VariableSpeedIncrease, m_driveTrain));
    R_joyButton.onFalse(new InstantCommand(m_driveTrain::VariableSpeedDecrease, m_driveTrain)); 

    L_JoyButton.onTrue(new InstantCommand(m_driveTrain::VariableSpeedIncreaseNORMAL, m_driveTrain));
    L_JoyButton.onFalse(new InstantCommand(m_driveTrain::VariableSpeedDecrease, m_driveTrain));


  
   // UP_DPad.onTrue(new InstantCommand(intakeSub::setShoot)); // (deprecated) Dpad for intake to move up in shoot position to score speaker
   // DOWN_DPad.onTrue(new InstantCommand(intakeSub::setFloor)); //Dpad down for intake to move in floor position to intake note
    //RIGHT_DPad.onTrue(new InstantCommand(intakeSub::setAmp)); //Dpad right for intake to move into Amp Position
      
    //A_Button.onTrue(new InstantCommand(intakeSub:: outtake));
    //A_Button.onFalse(new InstantCommand(intakeSub:: stopIntake));
    //B_Button.onTrue(new InstantCommand(intakeSub:: intake));
    //B_Button.onFalse(new InstantCommand(intakeSub:: stopIntake));

   
    // Goes to shooter point after pressing up
    L1_Button.onTrue(new InstantCommand(m_HangSub:: L_hang)); 
    L1_Button.onFalse(new InstantCommand(m_HangSub:: stop_Lhang));

    R1_Button.onTrue(new InstantCommand(m_HangSub:: R_hang)); 
    R1_Button.onFalse(new InstantCommand(m_HangSub:: stop_Rhang));
  }

  public Command getAutonomousCommand() {
    return m_chooser.getSelected();
  }
}
