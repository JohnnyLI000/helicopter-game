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
public class Wheel extends TreeNode {

    private float x , y , z;
    GL2 gl;
    private GLU glu;
    private GLUquadric quadric;
    private int displayList;

    public Wheel(float x, float y, float z) {
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
        gl.glPushMatrix();
        GLU glu = new GLU();
        GLUquadric quad = glu.gluNewQuadric();

        gl.glPushMatrix();
        gl.glTranslated(x, y, z -Specification.TIRE_DEPTH / 2.0);
        

        gl.glScaled(0.4, 1.2, 1);
        glu.gluSphere(quadric, 0.3f, 20, 20);
        gl.glPopMatrix();
        gl.glEndList();
        gl.glPopMatrix();

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
