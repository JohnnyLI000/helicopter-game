/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MovingObject;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

/**
 *
 * @author 11488
 */
public class Stick {

    private float top, base, height;
    private GLU glu;
    private GLUquadric quadric;
    GL2 gl;

    public Stick(GL2 gl, GLU glu, float top, float base, float height) {
        this.top = top;
        this.base = base;
        this.height = height;
        this.gl = gl;
    }

    public void drawStick() {
                glu = new GLU();

        quadric = glu.gluNewQuadric();
        gl.glPushMatrix();
        glu.gluCylinder(quadric, top, base, height, 12, 1);
        gl.glPopMatrix();
        gl.glFlush();
        gl.glEnd();
    }
}
