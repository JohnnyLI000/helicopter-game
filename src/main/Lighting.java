/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

/**
 *
 * @author 11488
 */
public class Lighting {
    // position of Light 0

    private float lightPosition[] = {10, 24, 10, 1};
    private double rotation = 0;
    private double lastTick = -1;

    public void createLightSource() {
        GLU glu = new GLU();
        GLUquadric quadric = glu.gluNewQuadric();
        glu.gluSphere(quadric, 6, 16, 8);
    }

    public void animateLight(boolean animateLight) {
        double tick = System.currentTimeMillis() / 1000.0;
        if (lastTick > 0) {
            double delta = tick - lastTick;
            rotation += delta * 20;
        }
        lastTick = tick;

        if (animateLight) {
            lightPosition[0] = (float) (10.0 * Math.cos(Math.toRadians(-rotation * 2)));
            lightPosition[1] = (float) (20.0);
            lightPosition[2] = (float) (10.0 * Math.sin(Math.toRadians(-rotation)));
        } else {
            lightPosition[0] = 10.0f;
            lightPosition[1] = 20.0f;
            lightPosition[2] = 10.0f;
        }
    }

    public float[] getLightPosition() {
        return this.lightPosition;
    }
}
