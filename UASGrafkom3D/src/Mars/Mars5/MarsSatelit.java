/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mars.Mars5;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.AmbientLight;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

/**
 *
 * @author Taufik
 */
public class MarsSatelit extends Application {
    private static final float WIDTH = 1024;
    private static final float HEIGHT = 720;
    
    private double anchorX, anchorY;
    private double anchorAngleX = 0;
    private double anchorAngleY = 0;
    private final DoubleProperty angleX = new SimpleDoubleProperty(0);
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);
    
    private final PointLight pointLight = new PointLight();
    private final PointLight pointLight2 = new PointLight();
    private final Sphere mars = new Sphere (150);
    private final Sphere phobos = new Sphere(20);
    private final Sphere deimos = new Sphere(10);
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        Camera camera = new PerspectiveCamera(true);
        camera.setNearClip(1);
        camera.setFarClip(10000);
        camera.translateZProperty().set(-1000);
        
        SmartGroup planet = new SmartGroup();
        planet.getChildren().add(komponenMars());
        planet.getChildren().addAll(prepareLightSource());
        planet.getChildren().addAll(prepareLightSource2());
        planet.getChildren().add(new AmbientLight(Color.CHOCOLATE));
        
        Group group = new Group();
        group.getChildren().add(planet);   
        group.getChildren().add(background());
        
        Scene scene = new Scene(group, WIDTH, HEIGHT, true);
        scene.setFill(Color.SILVER);
        scene.setCamera(camera);
        
        pengaturanMouse(planet, scene, primaryStage);
        
        primaryStage.setTitle("Mars");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        marsAnimasi();
        
        AnimationTimer phobosTimer= new AnimationTimer() {
            @Override
            public void handle(long now) {
                pointLight.setRotate(pointLight.getRotate() + 0.5);
            }
        };
        phobosTimer.start();
        
        AnimationTimer deimosTimer= new AnimationTimer() {
            @Override
            public void handle(long now) {
                pointLight2.setRotate(pointLight2.getRotate() + 0.2);
            }
        };
        deimosTimer.start();
    }
    
    private Node[] prepareLightSource() {
        pointLight.setColor(Color.WHITE);
        pointLight.getTransforms().add(new Translate(0, -20, 200));
        pointLight.setRotationAxis(Rotate.Y_AXIS);

        PhongMaterial materialBulan = new PhongMaterial();
        materialBulan.setDiffuseMap(new Image(getClass().getResourceAsStream("/resources/Satelit/phobos-d.png")));
        materialBulan.setBumpMap(new Image(getClass().getResourceAsStream("/resources/Satelit/phobos-s.jpg")));
        phobos.getTransforms().setAll(pointLight.getTransforms());
        phobos.rotateProperty().bind(pointLight.rotateProperty());
        phobos.rotationAxisProperty().bind(pointLight.rotationAxisProperty());
        phobos.setMaterial(materialBulan);
        return new Node[]{pointLight, phobos};
    }
    
    private Node[] prepareLightSource2() {
        pointLight2.setColor(Color.WHITE);
        pointLight2.getTransforms().add(new Translate(0, -70, 350));
        pointLight2.setRotationAxis(Rotate.Y_AXIS);

        PhongMaterial materialBulan2 = new PhongMaterial();
        materialBulan2.setDiffuseMap(new Image(getClass().getResourceAsStream("/resources/Satelit/deimos-d.png")));
        materialBulan2.setBumpMap(new Image(getClass().getResourceAsStream("/resources/Satelit/deimos-s.jpg")));
        deimos.getTransforms().setAll(pointLight2.getTransforms());
        deimos.rotateProperty().bind(pointLight2.rotateProperty());
        deimos.rotationAxisProperty().bind(pointLight2.rotationAxisProperty());
        deimos.setMaterial(materialBulan2);
        return new Node[]{pointLight2, deimos};
    }
    
    
    private void marsAnimasi(){
        AnimationTimer waktu = new AnimationTimer(){
            @Override
            public void handle (long now){
                mars.rotateProperty().set(mars.getRotate() + 0.2);
            }
        };
        waktu.start();
    }
    
    
    private ImageView background(){
        Image backImage = new Image(MarsSatelit.class.getResourceAsStream("/resources/Background/space.jpg"));
        ImageView iv = new ImageView(backImage);
        iv.setPreserveRatio(true);
        iv.getTransforms().add(new Translate(-backImage.getWidth()/2, -backImage.getHeight()/2, 720));
        return iv;
    }
    
    private Node komponenMars(){
        PhongMaterial materialMars = new PhongMaterial();
        materialMars.setDiffuseMap(new Image(getClass().getResourceAsStream("/resources/Mars/mars-d.jpg")));
        materialMars.setSpecularMap(new Image(getClass().getResourceAsStream("/resources/Mars/mars-s.jpg")));
        materialMars.setBumpMap(new Image(getClass().getResourceAsStream("/resources/Mars/mars-n.jpg")));
        
        
        mars.setRotationAxis(Rotate.Y_AXIS);
        mars.setMaterial(materialMars);
        return mars;
    }
    
    private void pengaturanMouse(SmartGroup group, Scene scene, Stage stage) {
        Rotate xRotate;
        Rotate yRotate;
        group.getTransforms().addAll(
            xRotate = new Rotate(0, Rotate.X_AXIS),
            yRotate = new Rotate(0, Rotate.Y_AXIS)
        );
        xRotate.angleProperty().bind(angleX);
        yRotate.angleProperty().bind(angleY);

        scene.setOnMousePressed(event -> {
            anchorX = event.getSceneX();
            anchorY = event.getSceneY();
            anchorAngleX = angleX.get();
            anchorAngleY = angleY.get();
        });

        scene.setOnMouseDragged(event -> {
            angleX.set(anchorAngleX - (anchorY - event.getSceneY()));
            angleY.set(anchorAngleY + anchorX - event.getSceneX());
        });

        stage.addEventHandler(ScrollEvent.SCROLL, event -> {
            double delta = event.getDeltaY();
            group.translateZProperty().set(group.getTranslateZ() + delta);
        });
  }
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    class SmartGroup extends Group {
        Rotate r;
        Transform t = new Rotate();
        
        void rotateByX(int ang) {
            r = new Rotate(ang, Rotate.X_AXIS);
            t = t.createConcatenation(r);
            this.getTransforms().clear();
            this.getTransforms().addAll(t);
        }
        
        void rotateByY(int ang) {
            r = new Rotate(ang, Rotate.Y_AXIS);
            t = t.createConcatenation(r);
            this.getTransforms().clear();
            this.getTransforms().addAll(t);
        }
    }
}
