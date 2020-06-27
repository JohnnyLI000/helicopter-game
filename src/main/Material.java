package main;

import com.jogamp.opengl.GL2;

/**
 * 
 * @author Jacqueline Whalley
 */
public class Material {

    // Some predefined materials

    public static Material Emerald = new Material(
        0.0215,  0.1745,   0.0215,
        0.07568, 0.61424,  0.07568,
        0.633,   0.727811, 0.633,
        77.0
    );

    public static Material Jade = new Material(
        0.135,    0.2225,   0.1575,
        0.54,     0.89,     0.63,
        0.316228, 0.316228, 0.316228,
        12.8
    );

    public static Material Obsidian = new Material(
        0.05375,  0.05,     0.06625,
        0.18275,  0.17,     0.22525,
        0.332741, 0.328634, 0.346435,
        39.0
    );

    public static Material Pearl = new Material(
        0.25,     0.20725,  0.20725,
        1.0,      0.829,    0.829,
        0.296648, 0.296648, 0.296648,
        11.0
    );

    public static Material Ruby = new Material(
        0.1745,   0.01175,  0.01175,
        0.61424,  0.04136,  0.04136,
        0.727811, 0.626959, 0.626959,
        77.0
    );

    public static Material Turquoise = new Material(
        0.1,      0.18725, 0.1745,
        0.396,    0.74151, 0.69102,
        0.297254, 0.30829, 0.306678,
        12.8
    );

    public static Material Brass = new Material(
        0.329412, 0.223529, 0.027451,
        0.780392, 0.568627, 0.113725,
        0.992157, 0.941176, 0.807843,
        30.0
    );

    public static Material Bronze = new Material(
        0.2125,   0.1275,   0.054,
        0.714,    0.4284,   0.18144,
        0.393548, 0.271906, 0.166721,
        25.6
    );

    public static Material Chrome = new Material(
        0.25,     0.25,     0.25,
        0.4,      0.4,      0.4,
        0.774597, 0.774597, 0.774597,
        76.8
    );

    public static Material Copper = new Material(
        0.19125,  0.0735,   0.0225,
        0.7038,   0.27048,  0.0828,
        0.256777, 0.137622, 0.086014,
        12.8
    );

    public static Material Gold = new Material(
        0.24725,  0.1995,   0.0745,
        0.75164,  0.60648,  0.22648,
        0.628281, 0.555802, 0.366065,
        51.2
    );

    public static Material Silver = new Material(
        0.19225,  0.19225,  0.19225,
        0.50754,  0.50754,  0.50754,
        0.508273, 0.508273, 0.508273,
        51.2
    );

    public static Material BlackPlastic = new Material(
        0.0,  0.0,  0.0,
        0.01, 0.01, 0.01,
        0.50, 0.50, 0.50,
        32.0
    );

    public static Material CyanPlastic = new Material(
        0.0,        0.1,        0.06,
        0.0,        0.50980392, 0.50980392,
        0.50196078, 0.50196078, 0.50196078,
        32.0
    );

    public static Material GreenPlastic = new Material(
        0.0,  0.0,  0.0,
        0.1,  0.35, 0.1,
        0.45, 0.55, 0.45,
        32.0
    );

    public static Material RedPlastic = new Material(
        0.0, 0.0, 0.0,
        0.5, 0.0, 0.0,
        0.7, 0.6, 0.6,
        32.0
    );

    public static Material WhitePlastic = new Material(
        0.0,  0.0,  0.0,
        0.55, 0.55, 0.55,
        0.70, 0.70, 0.70,
        32.0
    );

   public static Material YellowPlastic = new Material(
        0.0,  0.0,  0.0,
        0.5,  0.5,  0.0,
        0.60, 0.60, 0.50,
        32.0
   );

   public static Material BlackRubber = new Material(
        0.02, 0.02, 0.02,
        0.01, 0.01, 0.01,
        0.4,  0.4,  0.4,
        10.0
    );

    public static Material CyanRubber = new Material(
        0.0,  0.05, 0.05,
        0.4,  0.5,  0.5,
        0.04, 0.7,  0.7,
        10.0
    );

    public static Material GreenRubber = new Material(
        0.0,  0.05, 0.0,
        0.4,  0.5,  0.4,
        0.04, 0.7,  0.04,
        10.0
    );

    public static Material RedRubber = new Material(
        0.05, 0.0,  0.0,
        0.5,  0.4,  0.4,
        0.7,  0.04, 0.04,
        10.0
    );

    public static Material WhiteRubber = new Material(
        0.05, 0.05, 0.05,
        0.5,  0.5,  0.5,
        0.7,  0.7,  0.7,
        10.0
    );

   public static Material YellowRubber = new Material(
        0.05, 0.05, 0.0,
        0.5,  0.5,  0.4,
        0.7,  0.7,  0.04,
        10.0
   );
      public static Material blank = new Material(
        1, 1, 1,
        1,  1,  1,
        0,  0,  0,
        10
   );

    public Material(double ambR,  double ambG,  double ambB,
                    double diffR, double diffG, double diffB,
                    double specR, double specG, double specB,
                    double shininess)
    {
        ambient  = new float[]{(float) ambR,  (float) ambG,  (float) ambB,  1.0f};
        diffuse  = new float[]{(float) diffR, (float) diffG, (float) diffB, 1.0f};
        specular = new float[]{(float) specR, (float) specG, (float) specB, 1.0f};
        emissive = new float[]{0.0f, 0.0f, 0.0f, 1.0f};
        this.shininess = (float) shininess;
    }

    public void draw(GL2 gl)
    {
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT,  ambient,  0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE,  diffuse,  0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, specular, 0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_EMISSION, emissive, 0);
        gl.glMaterialf(GL2.GL_FRONT, GL2.GL_SHININESS, shininess);
    }

    private float[] ambient;
    private float[] diffuse;
    private float[] specular;
    private float[] emissive;
    private float   shininess;
}
