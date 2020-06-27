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
public class LandingSkids extends TreeNode {

    private float x, y, z;
    private GLUquadric quadric;
    private GLU glu;
    private int displayList;

    public LandingSkids( float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void initialiseDisplayList(GL2 gl) {
                displayList = gl.glGenLists(1);
        gl.glNewList(displayList, GL2.GL_COMPILE);
        gl.glPushMatrix();
        gl.glTranslatef(x - 0.8f, y, z - 1.6f);
        gl.glColor4d(0.24, 0.26, 0.43, 1);
        Stick stick = new Stick(gl, glu, 0.1f, 0.1f, 3);
        stick.drawStick();
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslatef(x - 0.8f, y, z - 0.8f);
        gl.glColor4d(0.24, 0.26, 0.43, 1);
        Stick skidConnecter = new Stick(gl, glu, 0.1f, 0.1f, 0.8f);
        gl.glRotatef(90, 0, 1, 0);
        gl.glRotatef(315, 1, 0, 0);
        skidConnecter.drawStick();
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslatef(x - 0.8f, y, z + 0.8f);
        gl.glColor4d(0.24, 0.26, 0.43, 1);
        skidConnecter = new Stick(gl, glu, 0.1f, 0.1f, 0.8f);
        gl.glRotatef(90, 0, 1, 0);
        gl.glRotatef(315, 1, 0, 0);
        skidConnecter.drawStick();
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glColor4d(0.24, 0.26, 0.43, 1);
        gl.glTranslatef(x + 0.8f, y, z - 1.6f);
        stick.drawStick();
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslatef(x + 0.8f, y, z - 0.8f);
        gl.glColor4d(0.24, 0.26, 0.43, 1);

        skidConnecter = new Stick(gl, glu, 0.1f, 0.1f, 0.8f);
        gl.glRotatef(90, 0, 1, 0);
        gl.glRotatef(225, 1, 0, 0);
        skidConnecter.drawStick();
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslatef(x + 0.8f, y, z + 0.8f);
        gl.glColor4d(0.24, 0.26, 0.43, 1);

        skidConnecter = new Stick(gl, glu, 0.1f, 0.1f, 0.8f);
        gl.glRotatef(90, 0, 1, 0);
        gl.glRotatef(225, 1, 0, 0);
        skidConnecter.drawStick();
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
