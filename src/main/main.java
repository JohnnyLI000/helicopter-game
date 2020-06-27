package main;

/**
 * A demonstration class for projected shadows.
 *
 * @author Jaqueline Whalley
 */
import MovingObject.Car;
import MovingObject.CarBody;
import MovingObject.Wheel;
import MovingObject.Body;
import MovingObject.Helicopter;
import MovingObject.LandingSkids;
import MovingObject.Rotor;
import MovingObject.RotorMast;
import MovingObject.Tail;
import MovingObject.TailRotor;
import MovingObject.TreeNode;
import Terrain.Face;
import Terrain.Ground;
import Terrain.MaterialParse;
import Terrain.OBJObject;
import Terrain.ObjectParser;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.Animator;
import static jogamp.opengl.util.av.EGLMediaPlayerImpl.TextureType.GL;


public class main implements GLEventListener, KeyListener {

    // the camera object
    private Camera camera;

    //display list
    private int groundList;
    private int cactusList;
    private int houseList;
    private int billboardList;
    private int dlLight;
    
    private double[] shadowMatrix = new double[16];

    public static GLCanvas canvas;

    //background 
    private boolean animateLight = false;
    private boolean wireframeOn = false;
    Lighting lighting = new Lighting();
    Ground ground ;
    
    //helicopter
    private float xp = 0, yp = 2f, zp = 0, anglesDelta;
    private TreeNode heliRoot;
    private Body heliBody;
    private TailRotor tailRotor;
    private LandingSkids landingSkids;
    private Rotor rotor;
    private RotorMast rotorMast;
    private Tail tail;
    
    
    //car 
    private TreeNode carRoot;
    private CarBody carBody;
    private Wheel wheel1;
    private Wheel wheel2;
    private Wheel wheel3;
    private Wheel wheel4;
    
    //material
    Material matHeli = Material.Obsidian;
    Material blank = Material.blank;
    Material matCactus = Material.Jade;
    Material matCar = Material.Gold;
    

    float[] carLocation = {0,0,0};
    public static void main(String[] args) {
        Frame frame = new Frame("Desert ");
        canvas = new GLCanvas();
        System.out.println("Key mapping:");
        System.out.println("L: Toggle animated light");
        System.out.println("Left  mouse button: Rotate scene");
        System.out.println("Right mouse button: Change camera distance");
        System.out.println("Mouse wheel       : Zoom");
        System.out.println("A/D: move left and right");
        System.out.println("W/S: move forward and back");
        System.out.println("up key / low key :Increase or decrease altitude");
        System.out.println("left key / right key : Stafe left or right");
        
        main app = new main();
        canvas.addGLEventListener(app);
        canvas.addKeyListener(app);

        frame.add(canvas);
        frame.setSize(640, 480);
        final Animator animator = new Animator(canvas);
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                // Run this on another thread than the AWT event queue to
                // make sure the call to Animator.stop() completes before
                // exiting
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        animator.stop();
                        System.exit(0);
                    }
                }).start();
            }
        });
        // Center frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        animator.start();
    }

    @Override
    public void init(GLAutoDrawable drawable) {

        GL2 gl2 = drawable.getGL().getGL2();
        // Enable VSync
        gl2.setSwapInterval(1);
        // Setup the drawing area and shading mode
        gl2.glClearColor(0.52f, 0.80f, 0.92f, 0.0f);

        gl2.glShadeModel(GL2.GL_SMOOTH);
        gl2.glEnable(GL2.GL_DEPTH_TEST);

        // create display list for the plane
        // create display list for the light source
        dlLight = gl2.glGenLists(1);
        gl2.glNewList(dlLight, GL2.GL_COMPILE);
        lighting.createLightSource();
        gl2.glEndList();

        camera = new Camera(canvas);
        camera.setDistance(12);
        camera.setFieldOfView(30);
        gl2.glPushMatrix();

        ground = new Ground(gl2, wireframeOn);
        groundList = gl2.glGenLists(2);
        gl2.glNewList(groundList, GL2.GL_COMPILE); //Terrain 
        ground.createGrid(12, 12, true);
        // ground.createGridHeightMap();
        ground.drawBackground();
        ground.draw();
        gl2.glEndList();

        cactusList = gl2.glGenLists(3);
        gl2.glNewList(cactusList, GL2.GL_COMPILE); //Terrain 
        ground.generateCactus(10, 10);
        gl2.glEndList();

        houseList = gl2.glGenLists(4);
        gl2.glNewList(houseList, GL2.GL_COMPILE); //Terrain 
        ground.generateHouse(2, 4);
        gl2.glEndList();

        billboardList = gl2.glGenLists(5);
        gl2.glNewList(billboardList, GL2.GL_COMPILE); //Terrain 
        ground.generateBillBoard(2, 3);
        gl2.glEndList();
        gl2.glPopMatrix();

        this.setupFog(gl2);
        
        // enable lighting
        gl2.glEnable(GL2.GL_LIGHTING);
        // enable light 0
        gl2.glEnable(GL2.GL_LIGHT0);
        // normalize the normals
        gl2.glEnable(GL2.GL_NORMALIZE);
        

    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        camera.newWindowSize(width, height);
    }

    @Override
    public void display(GLAutoDrawable drawable) {

        lighting.animateLight(animateLight);

        GL2 gl = drawable.getGL().getGL2();

        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

        camera.draw(gl);
        camera.updateLocaiton(anglesDelta, xp, yp, zp);
        this.drawCoordinateSystem(gl);

        gl.glPushMatrix();
        // position the light
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, lighting.getLightPosition(), 0);
        // draw the light source
        gl.glDisable(GL2.GL_LIGHTING);
        gl.glColor4d(1, 0.31, 0, 1);
        gl.glTranslated(lighting.getLightPosition()[0], lighting.getLightPosition()[1], lighting.getLightPosition()[2]);
        gl.glCallList(dlLight);
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glPopMatrix();

        // calculate and apply the shadow matrix - C style function
        calculateShadowMatrix(new double[]{lighting.getLightPosition()[0], lighting.getLightPosition()[1], lighting.getLightPosition()[2], lighting.getLightPosition()[3]},
                new double[]{0, 1, 0, 0.99},
                shadowMatrix);


        gl.glPushMatrix();
        gl.glRotatef(anglesDelta, 0, 1, 0);
        matHeli.draw(gl);
        this.createHelicopter();
        heliRoot = new Helicopter(gl);
        heliRoot.addChild(heliBody);
        heliRoot.draw(gl);
        gl.glPopMatrix();
        
        matCar.draw(gl);
         this.createCar();
         carLocation[2]+=0.01f;
        carRoot = carBody;
        carRoot.draw(gl);
        
        //clear the previous material 
        blank.draw(gl);
        gl.glTranslated(0, -3, 0);
        gl.glCallList(groundList);
        matCactus.draw(gl);
        gl.glCallList(cactusList);
        blank.draw(gl);
        gl.glCallList(houseList);
        gl.glCallList(billboardList);
    }

    public void createHelicopter() {
        heliBody = new Body(xp, yp, zp);
        tailRotor = new TailRotor(xp, yp, zp);
        landingSkids = new LandingSkids(xp, yp, zp);
        rotor = new Rotor(xp, yp, zp);
        rotorMast = new RotorMast(xp, yp, zp);
        tail = new Tail(xp, yp, zp);
        heliBody.addChild(tailRotor);
        heliBody.addChild(landingSkids);
        heliBody.addChild(rotor);
        heliBody.addChild(rotorMast);
        heliBody.addChild(tail);
    }

    public void createCar(){
        carBody = new CarBody(carLocation[0],carLocation[1],carLocation[2]);
        wheel1 = new Wheel(carBody.getLocation()[0]-0.8f,carBody.getLocation()[1],carBody.getLocation()[2]+.75f);
        wheel2 = new Wheel(carBody.getLocation()[0]+0.8f,carBody.getLocation()[1],carBody.getLocation()[2]+0.75f);
        wheel3 = new Wheel(carBody.getLocation()[0]-0.8f,carBody.getLocation()[1],carBody.getLocation()[2]-0.25f);
        wheel4 = new Wheel(carBody.getLocation()[0]+0.8f,carBody.getLocation()[1],carBody.getLocation()[2]-0.25f);
//        carBody.addChild(wheels);
        carBody.addChild(wheel1);  
        carBody.addChild(wheel2);
        carBody.addChild(wheel3);
        carBody.addChild(wheel4);
    }
    
    public void setupFog(GL2 gl2)
    {
        float density = 0.7f;
        float fogColor[] = new float[]{0.0f,0.0f,0.0f,1.0f};
        gl2.glEnable(GL2.GL_FOG);
        gl2.glFogfv(GL2.GL_FOG_COLOR,fogColor, 0);
        gl2.glFogf(GL2.GL_FOG_MODE, GL2.GL_LINEAR);
        gl2.glFogf(GL2.GL_FOG_DENSITY, density);
        gl2.glFogf(GL2.GL_FOG_START, 40f);
        gl2.glFogf(GL2.GL_FOG_END, 60f);
    }
    private void calculateShadowMatrix(double[] lightPos,
            double[] planeEquation,
            double[] shadowMatrix) {
        // d = L dot N
        double dot = planeEquation[0] * lightPos[0]
                + planeEquation[1] * lightPos[1]
                + planeEquation[2] * lightPos[2]
                + planeEquation[3] * lightPos[3];

        // Create the matrix. OpenGL uses column by column ordering
        shadowMatrix[0] = dot - lightPos[0] * planeEquation[0];
        shadowMatrix[4] = 0.0 - lightPos[0] * planeEquation[1];
        shadowMatrix[8] = 0.0 - lightPos[0] * planeEquation[2];
        shadowMatrix[12] = 0.0 - lightPos[0] * planeEquation[3];

        shadowMatrix[1] = 0.0 - lightPos[1] * planeEquation[0];
        shadowMatrix[5] = dot - lightPos[1] * planeEquation[1];
        shadowMatrix[9] = 0.0 - lightPos[1] * planeEquation[2];
        shadowMatrix[13] = 0.0 - lightPos[1] * planeEquation[3];

        shadowMatrix[2] = 0.0 - lightPos[2] * planeEquation[0];
        shadowMatrix[6] = 0.0 - lightPos[2] * planeEquation[1];
        shadowMatrix[10] = dot - lightPos[2] * planeEquation[2];
        shadowMatrix[14] = 0.0 - lightPos[2] * planeEquation[3];

        shadowMatrix[3] = 0.0 - lightPos[3] * planeEquation[0];
        shadowMatrix[7] = 0.0 - lightPos[3] * planeEquation[1];
        shadowMatrix[11] = 0.0 - lightPos[3] * planeEquation[2];
        shadowMatrix[15] = dot - lightPos[3] * planeEquation[3];
    }

    private void drawCoordinateSystem(GL2 gl) {
        double size = 1.5;
        gl.glLineWidth(2.0f);
        gl.glDisable(GL2.GL_LIGHTING);
        gl.glDisable(GL2.GL_TEXTURE_2D);
        gl.glBegin(GL2.GL_LINES);
        gl.glColor3d(1, 0, 0);
        gl.glVertex3d(0, 0, 0);
        gl.glVertex3d(size, 0, 0);
        gl.glColor3d(0, 1, 0);
        gl.glVertex3d(0, 0, 0);
        gl.glVertex3d(0, size, 0);
        gl.glColor3d(0, 0, 1);
        gl.glVertex3d(0, 0, 0);
        gl.glVertex3d(0, 0, size);
        gl.glEnd();
    }



    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_A: {
            }
            case KeyEvent.VK_D: {
            }
            case KeyEvent.VK_W: {
            }
            case KeyEvent.VK_S: {
            }
        }
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        switch (key) {
            case KeyEvent.VK_A: {
                xp -= 0.1f;
                break;
            }
            case KeyEvent.VK_D: {
                xp += 0.1f;
                break;
            }
            case KeyEvent.VK_W: {
                zp -= 0.1f;
                break;
            }
            case KeyEvent.VK_S: {
                zp += 0.1f;
                break;
            }
            case KeyEvent.VK_UP: {
                yp += 0.1f;
                break;
            }
            case KeyEvent.VK_DOWN: {
                if(yp>0.2){
                    yp -= 0.1f;
                }
                break;
            }
            case KeyEvent.VK_LEFT: {
                anglesDelta += 1;
                break;
            }
            case KeyEvent.VK_RIGHT: {
                anglesDelta -= 1;
                break;
            }
            case KeyEvent.VK_L: {
            animateLight = !animateLight;
                break;
            }

        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

}
