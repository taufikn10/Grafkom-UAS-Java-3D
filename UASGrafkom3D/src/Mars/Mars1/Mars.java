/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mars.Mars1;

import javafx.application.Application;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
public class Mars extends Application {
    private static final float WIDTH = 1024;
    private static final float HEIGHT = 720;
    
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        Camera camera = new PerspectiveCamera(true);
        camera.setNearClip(1);
        camera.setFarClip(1000);
        camera.translateZProperty().set(-1000);
        
        SmartGroup planet = new SmartGroup();
        planet.getChildren().add(komponenMars());
        
        Scene scene = new Scene(planet, WIDTH, HEIGHT, true);
        scene.setFill(Color.SILVER);
        scene.setCamera(camera);
        
        primaryStage.setTitle("Mars");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private Node komponenMars(){
        PhongMaterial materialMars = new PhongMaterial();
        materialMars.setDiffuseMap(new Image(getClass().getResourceAsStream("/resources/Mars/mars-d.jpg")));
        Sphere mars = new Sphere (150);
        mars.setMaterial(materialMars);
        return mars;
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
