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
public class CarBody extends TreeNode {

    private float[] location = new float[3]; 
    GL2 gl;
    private GLU glu;
    private GLUquadric quadric;
    private int displayList;

    public CarBody(float x, float y, float z) {

        location[0] = x;
        location[1] =y;
        location[2] =z;
        glu = new GLU();
        quadric = glu.gluNewQuadric();
        //giving different colors to different sides

    }

    public float[] getLocation(){
        return this.location;
    }
    public void initialiseDisplayList(GL2 gl) {
        glu = new GLU();
        quadric = glu.gluNewQuadric();
        //giving different colors to different sides
        gl.glPushMatrix();
        displayList = gl.glGenLists(1);
        gl.glNewList(displayList, GL2.GL_COMPILE);
        gl.glPushMatrix();
         gl.glTranslatef(location[0], location[1], location[2]+0.5f);
        gl.glScalef(3.2f, 0.4f, 0.4f);
        glu.gluSphere(quadric, 0.3f, 20, 20);
        gl.glPopMatrix();
        
                gl.glPushMatrix();
         gl.glTranslatef(location[0], location[1], location[2]-0.5f);
        gl.glScalef(3.2f, 0.4f, 0.4f);
        glu.gluSphere(quadric, 0.3f, 20, 20);
        gl.glPopMatrix();
        
        gl.glTranslatef(location[0], location[1], location[2]);
        gl.glScaled(0.7, 0.5, 1.4);
        glu.gluSphere(quadric, 0.7f, 20, 20);
        gl.glEndList();
        gl.glPopMatrix();

    }

    void transformNode(GL2 gl) {
    }

    void drawNode(GL2 gl) {
        initialiseDisplayList(gl);
        gl.glPushMatrix();
        gl.glCallList(displayList);
        gl.glPopMatrix();
    }



}
