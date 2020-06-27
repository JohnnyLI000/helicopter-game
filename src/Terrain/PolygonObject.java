package Terrain;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 *
 * @author JIER
 */
public class PolygonObject {

    private String name;
    ArrayList<Face> faces = new ArrayList<>();
    private MaterialObject material;
    boolean turnOnSmoothShading = true;
    GL2 gl2;

    public PolygonObject(GL2 gl2, String name) {
        this.gl2 = gl2;
        this.name = name;
    }

    public PolygonObject(String name) {
        this.name = name;
    }

    public void setGL2PolygonObject(GL2 gl2) {
        this.gl2 = gl2;
    }

    public String getName() {
        return name;
    }

    public void draw(GL2 gl2, float size, float x, float y, float z) {
        this.setUpMaterial();
        this.setUpTexture(gl2);
        faces.forEach((face) -> {
            //face.addNormals(normals);
            face.draw(gl2, size, x, y, z);

            //face.getNormalData();
        });
        gl2.glDisable(GL2.GL_TEXTURE_2D);
    }

    public void setMaterial(MaterialObject material) {
        this.material = material;
    }

    public void addFace(Face face) {
        this.faces.add(face);
    }

    public void setUpMaterial() {
        gl2.glEnable(GL2.GL_LIGHTING);
        if (this.turnOnSmoothShading) {
            gl2.glShadeModel(GL2.GL_SMOOTH);
        } else {
            gl2.glShadeModel(GL2.GL_FLAT);
        }

        gl2.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, this.material.getAmbientColor(), 0);
        gl2.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, this.material.getDiffuseColor(), 0);
    }

    public void setUpTexture(GL2 gl2) {
        Texture tex = this.material.getTurexure();
        if (tex != null) {
            gl2.glEnable(GL2.GL_TEXTURE_2D);
            tex.bind(gl2);
            tex.setTexParameteri(gl2, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
            tex.setTexParameteri(gl2, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
            tex.setTexParameteri(gl2, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR_MIPMAP_LINEAR);
        }
    }
}
