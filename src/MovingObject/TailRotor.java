/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MovingObject;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import static java.lang.Math.abs;

/**
 *
 * @author 11488
 */
public class TailRotor extends TreeNode {

    private float x = 0, y = 0, z = 0;
    GL2 gl;
    GLU glu;
    private int displayList;

    public TailRotor(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;

    }

    public void initialiseDisplayList(GL2 gl) {
        displayList = gl.glGenLists(1);
        gl.glNewList(displayList, GL2.GL_COMPILE);
        if (y > .3f) {
            gl.glPushMatrix();
            double pos = abs(Math.tan(System.currentTimeMillis() / 1000.0)) * y;
            gl.glTranslatef(x, y + 1, z + 3.5f);
            gl.glRotated(360 * pos, 1, 0, 0);
            gl.glTranslatef(-x, -y - 1, -z - 3.5f);
            gl.glPushMatrix();
            gl.glTranslatef(x + 0.3f, y + 1, z + 3.5f);
            gl.glColor4d(0.65, 0.18, 0, 1);
            Stick stick = new Stick(gl, glu, 0.1f, 0.1f, 1f);
            stick.drawStick();
            gl.glPopMatrix();
            gl.glPopMatrix();
        } else {
            gl.glPushMatrix();
            gl.glTranslatef(x + 0.3f, y + 1, z + 3.5f);
            gl.glColor4d(0.65, 0.18, 0, 1);
            Stick stick = new Stick(gl, glu, 0.1f, 0.1f, 1f);
            stick.drawStick();
            gl.glPopMatrix();
        }
        gl.glEndList();

    }

    @Override
    void transformNode(GL2 gl) {
    }

    @Override
    void drawNode(GL2 gl) {
        initialiseDisplayList(gl);
        gl.glPushMatrix();
        gl.glCallList(displayList);
        gl.glPopMatrix();
    }
}
