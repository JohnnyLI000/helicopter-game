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
public final class Ground {

    private int numCols, numRows;
    ArrayList<Vertex> vertices;
    GL2 gl2;
    ArrayList<Face> faces;
    private boolean clampS = false;
    private boolean clampT = false;
    private Filtering activeFiltering = Filtering.NEAREST;
    Texture texture;
    private int dlcactus;
    private int dlground;
    Random randomNumber = new Random();
    private OBJObject objObject;
    private ObjectParser parser;
    boolean wireframeOn;

    public Ground(GL2 gl2, boolean wireframeOn) {
        this.gl2 = gl2;
        this.wireframeOn = wireframeOn;
    }

    public void createGrid(int numCols, int numRows, boolean random) {
        this.numCols = numCols;
        this.numRows = numRows;
        vertices = new ArrayList<>();
        float height = 1;
        for (int row = 0; row < numRows + 1; row++) {
            for (int col = 0; col < numCols + 1; col++) {
                if (random == true) {
                    height = randomNumber.nextFloat()/2;
                }
                vertices.add(new Vertex(col - (this.numCols / 2), height, row - (this.numRows / 2)));
            }
        }
    }

    public double[][] readHeightMap() {
        String fileName = "./src/res/rsz_1rsz_2rsz_heightmap.jpg";
        double[][] heightMap = null;
        double min = Double.MAX_VALUE;
        double max = Double.MAX_VALUE;
        try {
            BufferedImage img = ImageIO.read(new File(fileName));
            int width = img.getWidth();
            int height = img.getHeight();
            heightMap = new double[height][width];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int rgb = img.getRGB(x, y);
                    int grey = rgb & 255;

                    // heightMap[y][x] = grey / 100.0;
                    heightMap[y][x] = grey / 200.0;

                    if (heightMap[y][x] < min) {
                        min = heightMap[y][x];
                    }
                    if (heightMap[y][x] > max) {
                        max = heightMap[y][x];
                    }

                }

            }
            for (int y = 0; y < numRows; y++) {
                for (int x = 0; x < numCols; x++) {
                    heightMap[y][x] = heightMap[y][x] - min;
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }

        return heightMap;
    }

    
    public void createGridHeightMap() {
        double[][] heightMap = this.readHeightMap();
        numRows = heightMap.length;
        numCols = heightMap[0].length;

        vertices = new ArrayList<>();
        for (int verZ = 0; verZ < numRows + 1; verZ++) {
            for (int verX = 0; verX < numCols + 1; verX++) {
                vertices.add(new Vertex(verX - (this.numCols / 2), heightMap[Math.min(verZ, numRows - 1)][Math.min(verX, numCols - 1)], verZ - (this.numRows / 2)));
            }
        }

        for (int i = 0; i < vertices.size(); i++) {
            vertices.get(i).getData();
        }
        System.out.println("the size：" + vertices.size());
    }

    public void isTextured() {
        gl2.glDisable(GL2.GL_TEXTURE_2D);
        gl2.glEnable(GL2.GL_TEXTURE_2D);
        try {
            texture = TextureIO.newTexture(new File("./src/res/desert-tile.jpg"), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // bind the texture
        texture.bind(gl2);
        // set clamping parameters
        texture.setTexParameteri(gl2, GL2.GL_TEXTURE_WRAP_S, clampS ? GL2.GL_CLAMP : GL2.GL_REPEAT);
        texture.setTexParameteri(gl2, GL2.GL_TEXTURE_WRAP_T, clampT ? GL2.GL_CLAMP : GL2.GL_REPEAT);
        texture.setTexParameteri(gl2, GL2.GL_TEXTURE_MIN_FILTER, activeFiltering.getMinMode());
        texture.setTexParameteri(gl2, GL2.GL_TEXTURE_MAG_FILTER, activeFiltering.getMagMode());
        gl2.glMatrixMode(GL2.GL_TEXTURE);
        gl2.glLoadIdentity();
    }

    private enum Filtering {
        NEAREST(GL2.GL_NEAREST, GL2.GL_NEAREST), LINEAR(GL2.GL_LINEAR, GL2.GL_LINEAR), NEAREST_MIPMAP_LINEAR(
                GL2.GL_NEAREST, GL2.GL_NEAREST_MIPMAP_LINEAR), NEAREST_MIPMAP_NEAREST(GL2.GL_NEAREST,
                GL2.GL_NEAREST_MIPMAP_NEAREST), LINEAR_MIPMAP_LINEAR(GL2.GL_LINEAR,
                GL2.GL_LINEAR_MIPMAP_LINEAR), LINEAR_MIPMAP_NEAREST(GL2.GL_LINEAR,
                GL2.GL_LINEAR_MIPMAP_NEAREST);

        private Filtering(int mag, int min) {
            minMode = min;
            magMode = mag;
        }

        public int getMinMode() {
            return minMode;
        }

        public int getMagMode() {
            return magMode;
        }

        public Filtering nextMode() {
            switch (this) {
                case NEAREST:
                    return LINEAR;
                case LINEAR:
                    return NEAREST_MIPMAP_LINEAR;
                case NEAREST_MIPMAP_LINEAR:
                    return NEAREST_MIPMAP_NEAREST;
                case NEAREST_MIPMAP_NEAREST:
                    return LINEAR_MIPMAP_LINEAR;
                case LINEAR_MIPMAP_LINEAR:
                    return LINEAR_MIPMAP_NEAREST;
                default:
                    return NEAREST;
            }
        }

        private int minMode;
        private int magMode;
    }

    public OBJObject createCactus() throws IOException {
        objObject = new OBJObject();

        String cactusPath = "./src/res/Cactus.obj";
        parser = new ObjectParser();
        objObject = parser.load(new File(cactusPath));
        return objObject;
    }

    public OBJObject createHouse() throws IOException {
        objObject = new OBJObject();
        String cactusPath = "./src/res/WoodenCabinBlender.obj";
        parser = new ObjectParser();
        objObject = parser.load(new File(cactusPath));

        return objObject;
    }

    public OBJObject createBillboard() throws IOException {
        objObject = new OBJObject();
        String cactusPath = "./src/res/uploads_files_1836977_billboard.obj";
        parser = new ObjectParser();
        objObject = parser.load(new File(cactusPath));

        return objObject;
    }

    public void generateHouse(int amount,int size) { // amount: how many of it ; size : how small it will be
        gl2.glPushMatrix();
        OBJObject house;
        int positive = 1;
        for (int i = 0; i < amount; i++) {
            house = new OBJObject();
            try {
                positive *= -1;
                float x = randomNumber.nextInt(this.numRows / 2) * randomNumber.nextFloat() * positive;
                float z = randomNumber.nextInt(this.numCols / 2) * randomNumber.nextFloat() * positive;
                Face face = this.getFace(x, z);
                float y = (float) face.getHeight();
                house = this.createHouse();
                house.draw(gl2, size*34, x, y, z);//34
                // cactus.getNormalData();
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
        gl2.glPopMatrix();
    }

    public void generateBillBoard(int amount,int size) {// amount: how many of it ; size : how small it will be
        gl2.glPushMatrix();
        OBJObject billBoard;
        int positive = 1;
        for (int i = 0; i < amount; i++) {
            billBoard = new OBJObject();
            try {
                positive *= -1;
                float x = randomNumber.nextInt(this.numRows / 2) * randomNumber.nextFloat() * positive;
                float z = randomNumber.nextInt(this.numCols / 2) * randomNumber.nextFloat() * positive;
                Face face = this.getFace(x, z);
                float y = (float) face.getHeight();
                billBoard = this.createBillboard();
                billBoard.draw(gl2, size*5, x, y, z); //5
                // cactus.getNormalData();
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
        gl2.glPopMatrix();
    }

    public void generateCactus(int amount,int size) {// amount: how many of it ; size : how small it will be
        gl2.glPushMatrix();
        OBJObject cactus;
        int positive = 1;
        for (int i = 0; i < amount; i++) {
            cactus = new OBJObject();
            try {
                positive *= -1;
                float x = randomNumber.nextInt(this.numRows / 2) * randomNumber.nextFloat() * positive;
                float z = randomNumber.nextInt(this.numCols / 2) * randomNumber.nextFloat() * positive;
                Face face = this.getFace(x, z);
                float y = (float) face.getHeight();
                cactus = this.createCactus();
                cactus.draw(gl2, size*10, x, y, z);//10
                // cactus.getNormalData();
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
        gl2.glPopMatrix();
    }

    public void setUpLight() {
        // parameters for light 0
        float ambientLight[] = {0, 0, 0, 1};  // no ambient
        float diffuseLight[] = {1, 1, 1, 1};  // white light for diffuse
        float specularLight[] = {1, 1, 1, 1};  // white light for specular

        // setup the light 0 properties
        gl2.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, ambientLight, 0);
        gl2.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, diffuseLight, 0);
        gl2.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, specularLight, 0);

        // amount of global ambient light
        float globalAmbientLight[] = {0.2f, 0.2f, 0.2f, 1};
        gl2.glLightModelfv(GL2.GL_LIGHT_MODEL_AMBIENT, globalAmbientLight, 0);

        // enable lighting
        gl2.glEnable(GL2.GL_LIGHTING);

        // enable light 0
        gl2.glEnable(GL2.GL_LIGHT0);

        // normalize the normals
        gl2.glEnable(GL2.GL_NORMALIZE);
    }

    public void drawBackground() {
        this.setUpLight();
        dlground = gl2.glGenLists(0);
        gl2.glNewList(dlground, GL2.GL_COMPILE);
        int xId, zId;
        faces = new ArrayList<Face>();
        if (this.wireframeOn) {
            gl2.glPolygonMode(gl2.GL_FRONT_AND_BACK, gl2.GL_LINE);
        }
        gl2.glNormal3d(0, 1, 0);
        gl2.glScaled(10, 10, 10);
        
        for (int row = 0; row < numRows; row++) {//z 
            for (int col = 0; col < numCols; col++) { //x
                zId = ((row + 1) * (numCols + 1)) + col;
                xId = (row * (numCols + 1)) + col;
                this.isTextured();

                Face face1 = new Face(gl2);
                face1.addVertex(vertices.get(xId));
                face1.addVertex(vertices.get(zId));
                face1.addVertex(vertices.get(xId + 1));
                face1.addTextureCoordinates(new double[]{0, 1, 0});
                face1.addTextureCoordinates(new double[]{0, 0, 0});
                face1.addTextureCoordinates(new double[]{1, 1, 0});
                face1.draw(gl2, 1, 0, 0, 0);

                gl2.glBegin(GL2.GL_POLYGON);
                Face face2 = new Face(gl2);
                face2.addVertex(vertices.get(xId + 1));
                face2.addVertex(vertices.get(zId));
                face2.addVertex(vertices.get(zId + 1));
                face2.addTextureCoordinates(new double[]{1, 1, 0});
                face2.addTextureCoordinates(new double[]{0, 0, 0});
                face2.addTextureCoordinates(new double[]{1, 0, 0});

                face2.draw(gl2, 1, 0, 0, 0);

                this.faces.add(face1);
                this.faces.add(face2);
            }
        }
        gl2.glEnd();
        gl2.glFlush();

        gl2.glEndList();
    }

    public Face getFace(double x, double z) {
        double c = (int) (this.numCols / 2) + x; //find column
        double r = (int) (this.numRows / 2) + z;

        double cf = c - (int) c;
        double rf = r - (int) r;

        int fid = ((int) r * this.numCols * 2) + ((int) c) * 2;
        if (cf + rf > 1.0) {
            fid++;
        }//find the traingle 
        return this.faces.get(fid);
    }

    public void draw() {
        gl2.glCallList(this.dlground);
    }

}
