package Terrain;

/**
 *
 * @author 11488
 */
public class MaterialColor {

    public float r = 0.0f;
    public float g = 0.0f;
    public float b = 0.0f;

    public MaterialColor() {
        super();
    }

    public float[] getColor() {
        float[] color = new float[3];
        color[0] = r;
        color[1] = g;
        color[2] = b;
        return color;
    }

    public MaterialColor(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public MaterialColor(MaterialColor other) {
        this.r = other.r;
        this.g = other.g;
        this.b = other.b;
    }

    public void setTo(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public void setTo(MaterialColor color) {
        this.r = color.r;
        this.g = color.g;
        this.b = color.b;
    }
}
