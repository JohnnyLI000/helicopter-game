
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
public class MaterialParse {

    private static final String COMMAND_MATERIAL = "newmtl";
    private static final String COMMAND_AMBIENT_COLOR = "Ka";
    private static final String COMMAND_DIFFUSE_COLOR = "Kd";
    private static final String COMMAND_SPECULAR_COLOR = "Ks";
    private static final String COMMAND_TRANSMISSION_COLOR = "Tf";
    private static final String COMMAND_DISSOLVE = "d";
    private static final String COMMAND_SPECULAR_EXPONENT = "Ns";
    private static final String COMMAND_AMBIENT_TEXTURE = "map_Ka";
    private static final String COMMAND_DIFFUSE_TEXTURE = "map_Kd";
    private static final String COMMAND_SPECULAR_TEXTURE = "map_Ks";
    private static final String COMMAND_SPECULAR_EXPONENT_TEXTURE = "map_Ns";
    private static final String COMMAND_DISSOLVE_TEXTURE = "map_d";

    HashMap<String, MaterialObject> mtlMap = new HashMap();
    MaterialObject mtlObject;
    String path;

    public HashMap<String, MaterialObject> load(File fileName) throws IOException {
        String line;
        String strValues[];

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"))) {
            this.path = fileName.getParent();
            while ((line = br.readLine()) != null) {
                //System.out.println(line);
                strValues = line.split(" ");
                //   System.out.println(strValues);
                switch (strValues[0].trim()) {
                    case COMMAND_MATERIAL: {
                        this.processMaterial(strValues);
                        break;
                    }
                    case COMMAND_AMBIENT_COLOR: {
                        this.processAmbientColor(strValues);
                        break;
                    }
                    case COMMAND_DIFFUSE_COLOR: {
                        this.processDiffuseColor(strValues);
                        break;
                    }
                    case COMMAND_SPECULAR_COLOR: {
                        this.processSpecularColor(strValues);
                        break;
                    }
                    case COMMAND_DISSOLVE: {
                        this.processDissolve(strValues);
                        break;
                    }
                    case COMMAND_AMBIENT_TEXTURE: {
                        this.processAmbientTexture(strValues);
                        break;
                    }
                    case COMMAND_DIFFUSE_TEXTURE: {
                        this.processDiffuseTexture(strValues);
                        break;
                    }

                    case COMMAND_SPECULAR_TEXTURE: {
                        this.processSpecularTexture(strValues);
                        break;
                    }
                    case COMMAND_SPECULAR_EXPONENT_TEXTURE: {
                        this.processSpecularExponentTexture(strValues);
                        break;
                    }
                    case COMMAND_SPECULAR_EXPONENT: {
                        this.processSpecularExponent(strValues);
                        break;

                    }
                    case COMMAND_DISSOLVE_TEXTURE: {
                        this.processDissolveTexture(strValues);
                        break;
                    }
                }
            }
            return mtlMap;
        }
    }
    

    private void processMaterial(String[] line) {
        if (line.length == 1) {
            throw new IllegalArgumentException("material missing");

        }
        mtlObject = new MaterialObject();
        mtlObject.setName(line[1]);
        this.mtlMap.put(line[1], mtlObject);
    }

    private void processAmbientColor(String[] line) {
        if (line.length != 4) {
            throw new IllegalArgumentException("it is not RGB ");
        }

        mtlObject.setAmibientColor(line);
    }

    private void processDiffuseColor(String[] line) {
        if (line.length != 4) {
            throw new IllegalArgumentException("it is not RGB ");
        }

        mtlObject.setDiffuseColor(line);
    }

    private void processSpecularColor(String[] line) {
        if (line.length != 4) {
            throw new IllegalArgumentException("it is not RGB ");
        }

        mtlObject.setSpecularColor(line);
    }

    private void processDissolve(String[] line) {
        if (line.length != 2) {
            throw new IllegalArgumentException("its not Dissolve ");
        }
        mtlObject.setDissolve(Float.parseFloat(line[1]));
    }

    private void processAmbientTexture(String[] line) {
        if (line.length == 1) {
            throw new IllegalArgumentException("missing ambient texure ");
        }
//        System.out.println(this.path + File.separator + line[1]);
        mtlObject.setAmbientTexture(this.path + File.separator + line[1]);
    }

    private void processDiffuseTexture(String[] line) {
        if (line.length == 1) {
            throw new IllegalArgumentException("missing diffuse texure ");
        }
//        System.out.println(this.path + File.separator + line[1]);
        mtlObject.setDiffuseTexture(this.path + File.separator + line[1]);
    }

    private void processSpecularTexture(String[] line) {
        if (line.length == 1) {
            throw new IllegalArgumentException("missing specular texure ");
        }
//        System.out.println(this.path + File.separator + line[1]);
        mtlObject.setSpecularTexture(this.path + File.separator + line[1]);

    }

    private void processSpecularExponentTexture(String[] line) {
        if (line.length == 1) {
            throw new IllegalArgumentException("missing specular exponent  texure ");
        }
//        System.out.println(this.path + File.separator + line[1]);
        mtlObject.setSpecularExponentTexture(this.path + File.separator + line[1]);
    }

    private void processSpecularExponent(String[] line) {
        if (line.length != 2) {
            throw new IllegalArgumentException("its not specular exponent");
        }
        mtlObject.setSpecularExponent(Float.parseFloat(line[1]));
    }

    private void processDissolveTexture(String[] line) {
        if (line.length == 1) {
            throw new IllegalArgumentException("missing dissolve texure ");
        }
//        System.out.println(this.path + File.separator + line[1]);
        mtlObject.setDissolveTexture(this.path + File.separator + line[1]);
    }
}
