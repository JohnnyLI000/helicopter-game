/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Terrain;

import com.jogamp.opengl.GL2;
import java.util.ArrayList;

/**
 *
 * @author 11488
 */
public class Face {

    GL2 gl2;

    private ArrayList<Vertex> vertices = new ArrayList<>();
    private double[] normals = new double[3];
    private ArrayList<double[]> textureList = new ArrayList<>();
    public Face(GL2 gl2) {
        this.gl2 = gl2;
    }

    public Face() {

    }

    public void addVertex(Vertex vertex) {
        this.vertices.add(vertex);
    }

    public ArrayList<Vertex> getVertex() {
        return this.vertices;
    }
    public void textureList(){
        System.out.println(textureList.size());
    }
    public void addTextureCoordinates(double[] coordinates) {
        textureList.add(coordinates);
    }

    public double[] getTextureCoordinates(int i) {

        return this.textureList.get(i);
    }

    public void addNormals(double[] normals) {
        this.normals = normals;
    }

    public double[] getNormals() {
        return this.normals;
    }

    public void getNormalData() {
        System.out.println("x : " + normals[0] + ", y: " + normals[1] + ", z: " + normals[2]);
    }
    
    public double getHeight() {
        double height = 0;
        for (Vertex vertex : this.vertices) {
            height += vertex.getPosition()[1];
        }
        height = height/vertices.size()-0.04f;
        return height;
    }
    
    public void draw(GL2 gl2, float size, float x, float y, float z) {
        Vertex vertex;
        gl2.glBegin(GL2.GL_POLYGON);
        for (int i = 0; i < this.vertices.size(); i++) {
            vertex = this.vertices.get(i);
            if(!this.textureList.isEmpty())
            {
            try{
                double[] textureCoordinates = this.getTextureCoordinates(i);
                if (textureCoordinates!= null) {
                    gl2.glTexCoord2dv(textureCoordinates, 0);
                }
            }catch(IndexOutOfBoundsException ex)
            {
                System.err.println(ex);
            }
            }
            //  vertex.addNormals(normals);
            // vertex.getNormalData();
            //gl2.glNormal3d(vertex.getNormals()[0],vertex.getNormals()[1], vertex.getNormals()[2]);
            gl2.glVertex3d(vertex.getPosition()[0] / size + x, vertex.getPosition()[1] / size + y, vertex.getPosition()[2] / size + z);
            // System.out.println("texture co"+ textureCoordinates[0]);

        }
        gl2.glEnd();

    }

}
