package Terrain;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author 11488
 */
class MaterialObject {

    private String name;

    private MaterialColor ambientColor = new MaterialColor(0f, 0f, 0f);
    private MaterialColor diffuseColor = new MaterialColor(1.0f, 1.0f, 1.0f);
    private MaterialColor specularColor = new MaterialColor(0.0f, 0.0f, 0.0f);
    private MaterialColor transmissionColor = new MaterialColor(0.0f, 0.0f, 0.0f);
    private float[] diffuse = new float[]{1, 1, 1, 1};
    ;
    private float[] specular = new float[]{1, 1, 1, 1};
    ;

    private float specularExponent = 0.0f;
    private float dissolve = 1.0f;

    private String ambientTexture;
    private String diffuseTexture;
    private String specularTexture;
    private String specularExponentTexture;
    private String dissolveTexture;

    private Texture texture;
    private boolean activeTexture = false;

    public MaterialObject() {
        super();
    }

    public MaterialObject(MaterialObject other) {
        this.ambientColor.setTo(other.ambientColor);
        this.diffuseColor.setTo(other.diffuseColor);
        this.specularColor.setTo(other.specularColor);
        this.transmissionColor.setTo(other.transmissionColor);
        this.specularExponent = other.specularExponent;
        this.dissolve = other.dissolve;
        this.ambientTexture = other.ambientTexture;
        this.diffuseTexture = other.dissolveTexture;
        this.specularTexture = other.specularTexture;
        this.specularExponentTexture = other.specularExponentTexture;
        this.dissolveTexture = other.dissolveTexture;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public float[] getAmbientColor() {

        return ambientColor.getColor();
    }

    public void setAmibientColor(String[] file) {
        float color[] = new float[3];
        color[0] = Float.parseFloat(file[1]);
        color[1] = Float.parseFloat(file[2]);
        color[2] = Float.parseFloat(file[3]);
        this.ambientColor.setTo(color[0], color[1], color[2]);
    }

    public float[] getDiffuseColor() {
        return diffuseColor.getColor();
    }

    public void setDiffuseColor(String[] file) {
        float color[] = new float[3];
        color[0] = Float.parseFloat(file[1]);
        color[1] = Float.parseFloat(file[2]);
        color[2] = Float.parseFloat(file[3]);

        this.diffuseColor.setTo(color[0], color[1], color[2]);
    }

    public float[] getSpecularColor() {
        return specularColor.getColor();
    }

    public void setSpecularColor(String[] file) {
        float color[] = new float[3];
        color[0] = Float.parseFloat(file[1]);
        color[1] = Float.parseFloat(file[2]);
        color[2] = Float.parseFloat(file[3]);

        this.specularColor.setTo(color[0], color[1], color[2]);
    }

    public float[] getTransmissionColor() {
        return transmissionColor.getColor();
    }

    public void setSpecularExponent(float specularExponent) {
        this.specularExponent = specularExponent;
    }

    public float getSpecularExponent() {
        return specularExponent;
    }

    public void setDissolve(float dissolve) {
        this.dissolve = dissolve;
    }

    public float getDissolve() {
        return dissolve;
    }

    public void setAmbientTexture(String filename) {
        this.ambientTexture = filename;
        try {
            this.texture = TextureIO.newTexture(new File(ambientTexture), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getAmbientTexture() {
        return ambientTexture;
    }

    public void setDiffuseTexture(String filename) {
        this.diffuseTexture = filename;
        try {
            this.texture = TextureIO.newTexture(new File(diffuseTexture), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getDiffuseTexture() {
        return diffuseTexture;
    }

    public void setSpecularTexture(String filename) {
        this.specularTexture = filename;
        try {
            this.texture = TextureIO.newTexture(new File(specularTexture), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getSpecularTexture() {
        return specularTexture;
    }

    public void setSpecularExponentTexture(String filename) {
        this.specularExponentTexture = filename;
        try {
            this.texture = TextureIO.newTexture(new File(specularExponentTexture), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getSpecularExponentTexture() {
        return specularExponentTexture;
    }

    public void setDissolveTexture(String filename) {
        this.dissolveTexture = filename;
        try {
            this.texture = TextureIO.newTexture(new File(dissolveTexture), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getDissolveTexture() {
        return dissolveTexture;
    }

    public Texture getTurexure() {
        return this.texture;
    }
}
