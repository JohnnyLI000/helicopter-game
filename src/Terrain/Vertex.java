/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Terrain;

/**
 *
 * @author 11488
 */
public class Vertex {
    private double[] position = new double[3];
    private double[] normals = new double[3];
    private boolean hasNormal  = false ;
    private double[] color;
    public Vertex(double x, double y,double z)
    {
        position[0] = x;
        position[1] = y;
        position[2] = z;
    }


    public void addNormals(double[] normal){
        this.normals = normal;
        this.hasNormal = true;
    }
    public double[] getNormals(){
        return normals;
    }

        public void getNormalData(){
        System.out.println("x : "+ normals[0]+", y: "+normals[1]+", z: "+normals[2]);
    }
    public void getData(){
        System.out.println("x : "+ position[0]+", y: "+position[1]+", z: "+position[2]);
    }

    public double[] getPosition(){
        return this.position;
    }
    
}
