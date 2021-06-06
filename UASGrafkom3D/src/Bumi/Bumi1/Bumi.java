/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bumi.Bumi1;

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
public class Bumi extends Application{
    private static final float WIDTH = 1024;
    private static final float HEIGHT = 720;
    
    @Override
    public void start (Stage primaryStage) throws Exception {
        Camera camera = new PerspectiveCamera(true);
        camera.setNearClip(1);
        camera.setFarClip(1000);
        camera.translateZProperty().set(-1000);
        
        SmartGroup dunia = new SmartGroup();
        dunia.getChildren().add(permulaanDunia());
                
        Scene scene = new Scene(dunia, WIDTH, HEIGHT, true);
        scene.setFill(Color.SILVER);
        scene.setCamera(camera);
       
        primaryStage.setTitle("Dasaran Bumi");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private Node permulaanDunia(){
        PhongMaterial materialBumi = new PhongMaterial();
        materialBumi.setDiffuseMap(new Image(getClass().getResourceAsStream("/resources/Bumi/bumi-d.jpg")));
        Sphere bumi = new Sphere(150);
        bumi.setMaterial(materialBumi);
        return bumi;
    }    
    
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
