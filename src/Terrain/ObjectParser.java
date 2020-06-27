package Terrain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 *
 * @author 11488
 */
public class ObjectParser {

    private static final String COMMAND_VERTEX = "v";
    private static final String COMMAND_TEXCOORD = "vt";
    private static final String COMMAND_NORMAL = "vn";
    private static final String COMMAND_OBJECT = "o";
    private static final String COMMAND_FACE = "f";
    private static final String COMMAND_GROUP = "g";

    private static final String COMMAND_MATERIAL_REF = "usemtl";
    private static final String COMMAND_MATERIAL_LIB = "mtllib";

    OBJObject objObject;
    HashMap<String, MaterialObject> mtlibrary;
    String path;

    public OBJObject load(File fileName) throws IOException {
        String line;
        String strValues[];
        objObject = new OBJObject();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"))) {
            this.path = fileName.getParent();
            while ((line = br.readLine()) != null) {
                //   System.out.println(line);
                strValues = line.split(" ");

                switch (strValues[0].trim()) {
                    case COMMAND_VERTEX: {
                        this.processVertex(strValues);
                        break;
                    }
                    case COMMAND_TEXCOORD: {
                        this.processTextureCoordinates(strValues);
                        break;
                    }
                    case COMMAND_NORMAL: {
                        this.processNormal(strValues);
                        break;
                    }
                    case COMMAND_OBJECT: {
                        this.processObject(strValues);
                        break;
                    }
                    case COMMAND_FACE: {
                        this.processFace(strValues);
                        break;
                    }
                    case COMMAND_MATERIAL_REF: {
                        this.processMaterialReference(strValues);
                        break;
                    }
                    case COMMAND_MATERIAL_LIB: {
                        this.processMaterialLibrary(strValues);
                        break;
                    }
                    case COMMAND_GROUP: {
                        this.processGroup(strValues);
                        break;
                    }
                }
            }
            return this.objObject;
        }
    }

    public void processVertex(String[] strValues) {
        if (strValues.length != 4) {
            throw new IllegalArgumentException("wrong vertex data");
        }
        double[] vertexPosition = new double[3];
        vertexPosition[0] = Float.parseFloat(strValues[1]);
        vertexPosition[1] = Float.parseFloat(strValues[2]);
        vertexPosition[2] = Float.parseFloat(strValues[3]);
        Vertex vertex = new Vertex(vertexPosition[0], vertexPosition[1], vertexPosition[2]);
        this.objObject.addVertex(vertex);
    }

    public void processTextureCoordinates(String[] strValues) {
        if (strValues.length == 1) {
            throw new IllegalArgumentException("wrong texture coor data");
        }

        double[] textureCoordinates = new double[3];
        textureCoordinates[0] = Double.parseDouble(strValues[1]);

        if (strValues.length > 2) {
            textureCoordinates[1] = Double.parseDouble(strValues[2]);

        }
        if (strValues.length > 3) {
            textureCoordinates[2] = Double.parseDouble(strValues[3]);

        }
        this.objObject.addTextureCoordinates(textureCoordinates);
    }

    public void processNormal(String[] strValues) {
        if (strValues.length != 4) {
            throw new IllegalArgumentException("wrong normal  data");
        }

        double[] normal = new double[3];

        normal[0] = Double.parseDouble(strValues[1]);
        normal[1] = Double.parseDouble(strValues[2]);
        normal[2] = Double.parseDouble(strValues[3]);
        //  System.out.println("x : " + normal[0] + ", y: " + normal[1] + ", z: " + normal[2]);
        this.objObject.addNormals(normal);
//        for(int i = 0 ; i<objObject.normals.size();i++)
//        {
//            System.out.println(objObject.normals.get(i)[0]);
//        }
    }

    public void processObject(String[] strValues) {
        if (strValues.length < 2) {
            throw new IllegalArgumentException("wrong object data");

        }
        PolygonObject object = new PolygonObject(strValues[1]);
        this.objObject.addObject(object);
    }

    public void processGroup(String[] strValues) {
        if (strValues.length != 2) {
            throw new IllegalArgumentException("wrong object data");

        }

        PolygonObject object = new PolygonObject(strValues[1]);
        this.objObject.addObject(object);
    }

    public void processFace(String[] strValues) {
        Face face = new Face();
        int numVertices = strValues.length - 1;
        String strIndexes[];
        int index;
        Vertex vertex;

        for (int i = 0; i < numVertices; i++) {
            strIndexes = strValues[i + 1].split("/");
            index = Integer.parseInt(strIndexes[0]);
            vertex = this.objObject.getVertex(index - 1);
            if (strIndexes.length >= 2) {
                if (!strIndexes[1].isEmpty()) {
                    index = Integer.parseInt(strIndexes[1]);
                    face.addTextureCoordinates(this.objObject.getTextureCoordinate(index - 1));
                }
            }
            if (strIndexes.length == 3) {
                if (!strIndexes[2].isEmpty()) {
                    index = Integer.parseInt(strIndexes[2]);
                    vertex.addNormals(this.objObject.getNormals(index - 1));
                }
            }
            face.addVertex(vertex);
        }

        for (Vertex v : face.getVertex()) {
            v.addNormals(face.getNormals());
        }
        this.objObject.getLastObject().addFace(face);
    }

    public void processMaterialLibrary(String[] strValues) throws IOException {
        if (strValues.length != 2) {
            throw new IllegalArgumentException("wrong material librabry data");
        }
        MaterialParse parser = new MaterialParse();
        String fileName = this.path + File.separator + strValues[1];
        this.mtlibrary = parser.load(new File(fileName));
        //  this.mtlibrary.forEach((key, value) -> System.out.println(key + " " + value));
    }

    private void processMaterialReference(String[] strValues) {
        if (strValues.length < 2) {
            throw new IllegalArgumentException("wrong material reference data");
        }

        MaterialObject material = this.mtlibrary.get(strValues[1]);
        this.objObject.getLastObject().setMaterial(material);
    }

    private String getFileName(String[] line) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < line.length; i++) {
            builder.append(line[i]);
        }
        return builder.toString();
    }

}
