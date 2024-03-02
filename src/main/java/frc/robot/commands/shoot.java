package frc.robot.commands;

import frc.robot.subsystems.intakeSub;

import java.rmi.UnexpectedException;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.shooterSub;

public class shoot extends Command{

//subsystem objects
shooterSub mshooterSub = new shooterSub();
intakeSub m_IntakeSub = new intakeSub();

public shoot(){
m_IntakeSub.C_transerversalOUT();   
mshooterSub.C_ShooterOut();
m_IntakeSub.C_outtake();
new WaitCommand(.5);
    }
        }