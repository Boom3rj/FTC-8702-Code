package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.MecanumDriveLIMELIGHTAutoAiming.Turret;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;

import java.util.List;

public class AutoAim {
    public static double tx = 0;

    public void init() {
        // Initialization handled in main opmode to avoid duplicate hardware maps
    }

    public double find(double target, double tID, LLResult result) {

        double turretOutput =0;
        double speed = Turret.getVelocity();
        double distToTarget = target - Turret.getCurrentPosition();
        double P = 1;
        double I = 0.2;
        double M = 2000;
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
                    double angToTarget = (tx*5.97) - Turret.getCurrentPosition();
                    //use new target (angToTarget) to aim PI
                    turretOutput = ((angToTarget * P) - (speed * I)) / M;
                }
            }
        }
        return turretOutput;
    }
}