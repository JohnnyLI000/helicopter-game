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
public class Body extends TreeNode {

    private float x, y, z;
    GL2 gl;
    private GLU glu;
    private GLUquadric quadric;
    private int displayList;

    public Body(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        glu = new GLU();
        quadric = glu.gluNewQuadric();
        //giving different colors to different sides

    }

    public void initialiseDisplayList(GL2 gl) {
        glu = new GLU();
        quadric = glu.gluNewQuadric();
        //giving different colors to different sides
        displayList = gl.glGenLists(1);
        gl.glNewList(displayList, GL2.GL_COMPILE);
        gl.glTranslatef(x, y + 1, z);
        gl.glScaled(0.7, 0.5, 2);
        glu.gluSphere(quadric, 1.2f, 20, 20);
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
