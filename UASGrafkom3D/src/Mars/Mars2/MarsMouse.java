/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mars.Mars2;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.stage.Stage;

/**
 *
 * @author Taufik
 */
public class MarsMouse extends Application {
    private static final float WIDTH = 1024;
    private static final float HEIGHT = 720;
    
    private double anchorX, anchorY;
    private double anchorAngleX = 0;
    private double anchorAngleY = 0;
    private final DoubleProperty angleX = new SimpleDoubleProperty(0);
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        Camera camera = new PerspectiveCamera(true);
        camera.setNearClip(1);
        camera.setFarClip(10000);
        camera.translateZProperty().set(-1000);
        
        SmartGroup planet = new SmartGroup();
        planet.getChildren().add(komponenMars());
        
        Scene scene = new Scene(planet, WIDTH, HEIGHT, true);
        scene.setFill(Color.SILVER);
        scene.setCamera(camera);
        
        pengaturanMouse(planet, scene, primaryStage);
        
        primaryStage.setTitle("Mars");
        primaryStage.setScene(scene);
        primaryStage.show();
    
    }
    
    private Node komponenMars(){
        PhongMaterial materialMars = new PhongMaterial();
        materialMars.setDiffuseMap(new Image(getClass().getResourceAsStream("/resources/Mars/mars-d.jpg")));
        materialMars.setSpecularMap(new Image(getClass().getResourceAsStream("/resources/Mars/mars-s.jpg")));
        materialMars.setBumpMap(new Image(getClass().getResourceAsStream("/resources/Mars/mars-n.jpg")));
        
        Sphere mars = new Sphere (150);
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
