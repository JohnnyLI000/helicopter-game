/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MovingObject;

import com.jogamp.opengl.GL2;

/**
 *
 * @author 11488
 */
public class Car extends TreeNode {
    private float[] location = new float[3]; 

    public Car(float x ,float y, float z){
        location[0] = x;
        location[1] =y;
        location[2] =z;
    }

    public float[] getLocation(){
        return this.location;
    }
    
    @Override
    void transformNode(GL2 gl) {
    }

    @Override
    void drawNode(GL2 gl) {
    }




}
