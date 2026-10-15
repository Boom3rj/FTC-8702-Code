package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.MecanumDriveLIMELIGHTAutoAiming.Turret;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;

import java.util.List;

public class AutoAim {
    public static double tx = 0;
    public double P = 1;
    public double I = 0.2;
    public double M = 2000;
    public double turretLimit = 1250;
    public void init() {
        // Initialization handled in main opmode to avoid duplicate hardware maps
    }

    public double find(double target, double tID, LLResult result) {

        double turretOutput =0;
        double speed = Turret.getVelocity();
        double distToTarget = target - Turret.getCurrentPosition();
        //see if there is a target position if not use limelight
        if (target != 0){
            //PI (PID - D)
            turretOutput= ((distToTarget * P) - (speed * I)) / M;

        } else if (result != null && result.isValid()) {
            //see if the limelight sees the correct tag
            List<LLResultTypes.FiducialResult> fiducials = result.getFiducialResults();
            if (fiducials != null && !fiducials.isEmpty()) {
                LLResultTypes.FiducialResult detectedTag = fiducials.get(0);
                int detectedTagId = detectedTag.getFiducialId();
                if (detectedTagId == (int) tID) {
                    tx = detectedTag.getTargetXDegrees();
                    //multiply degrees by ticks per degree
                    double angToTarget = (tx*5.97) + Turret.getCurrentPosition();
                    //use new target (angToTarget) to aim PI
                    turretOutput = ((angToTarget * P) - (speed * I)) / M;
                }
            }
        }
        if (turretOutput > 0 && Turret.getCurrentPosition() > turretLimit){
            turretOutput = 0;
        } else if (turretOutput < 0 && Turret.getCurrentPosition() < -turretLimit) {
            turretOutput = 0;
        }
        return turretOutput;
    }
}