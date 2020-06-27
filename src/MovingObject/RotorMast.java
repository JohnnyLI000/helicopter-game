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
public class RotorMast extends TreeNode {

    private float x, y, z;
    private GLUquadric quadric;
    private GLU glu;
    private int displayList;

    public RotorMast(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void initialiseDisplayList(GL2 gl) {
        displayList = gl.glGenLists(1);
        gl.glNewList(displayList, GL2.GL_COMPILE);
        gl.glPushMatrix();
        gl.glTranslatef(x, y + 1, z);
        gl.glRotatef(270, 1, 0, 0);
        gl.glColor4d(0.65, 0.18, 0, 1);
        Stick stick = new Stick(gl, glu, 0.1f, 0.1f, 1.1f);
        stick.drawStick();
        gl.glPopMatrix();
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
