package Terrain;

import com.jogamp.opengl.GL2;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author 11488
 */
public class OBJObject {

    HashMap<String, PolygonObject> objects = new HashMap<>();
    Random random = new Random();
    private PolygonObject lastObject;

    ArrayList<Vertex> vertices = new ArrayList<>();
    ArrayList<double[]> normals = new ArrayList<>();
    ArrayList<double[]> textureCoordinates = new ArrayList<>();
    ArrayList<PolygonObject> objectsList;

    public void addVertex(Vertex vertex) {
        this.vertices.add(vertex);
    }

    public Vertex getVertex(int index) {
        return this.vertices.get(index);
    }

    public void clearVertices() {
        if (vertices.size() != 0) {
            this.vertices.clear();
        }
    }

    public void getNormalData() {
        System.out.println("normal size " + normals.size());
        for (int i = 0; i < normals.size(); i++) {
            System.out.println("OBJ object x : " + normals.get(i)[0] + ", y: " + normals.get(i)[1] + ", z: " + normals.get(i)[2]);
        }
    }

    public void addTextureCoordinates(double[] coordinates) {
        textureCoordinates.add(coordinates);
    }

    public double[] getTextureCoordinate(int index) {
        return this.textureCoordinates.get(index);
    }

    public void clearTextureCoordinates() {
        if (this.textureCoordinates.size() != 0) {
            this.textureCoordinates.clear();
        }
    }

    public void addNormals(double[] normals) {
        this.normals.add(normals);
    }

    public double[] getNormals(int index) {
        return this.normals.get(index);
    }

    public void clearNormals() {
        if (this.normals.size() != 0) {
            this.normals.clear();
        }
    }

    public void addObject(PolygonObject polygonObject) {
        this.lastObject = polygonObject;
        this.objects.put(polygonObject.getName(), polygonObject);
    }

    public ArrayList<PolygonObject> getObjects() {
        return new ArrayList<>(objects.values());
    }

    public PolygonObject getLastObject() {
        return this.lastObject;
    }

    public void draw(GL2 gl2, float size, float x, float y, float z) {
        objectsList = this.getObjects();
        for (int i = 0; i < objectsList.size(); i++) {
            objectsList.get(i).setGL2PolygonObject(gl2);
            objectsList.get(i).draw(gl2, size, x, y, z);
        }
    }
}
