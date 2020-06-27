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
public class Tail extends TreeNode {

    private float x, y, z;
    private GLUquadric quadric;
    private GLU glu;
    private int displayList;

    public Tail(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        glu = new GLU();
        quadric = glu.gluNewQuadric();
    }

    public void initialiseDisplayList(GL2 gl) {
        displayList = gl.glGenLists(1);
        gl.glNewList(displayList, GL2.GL_COMPILE);
        quadric = glu.gluNewQuadric();
        gl.glPushMatrix();
        gl.glTranslatef(x, y + 1, z + 1.5f);
        gl.glColor4d(0.24, 0.26, 0.43, 1);
        glu.gluCylinder(quadric, 0.2, 0.2, 3f, 4, 10);
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
