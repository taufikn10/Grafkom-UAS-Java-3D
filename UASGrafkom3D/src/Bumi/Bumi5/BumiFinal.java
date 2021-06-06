/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bumi.Bumi5;


import Bumi.Bumi3.BumiBackground;
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
import javafx.scene.control.Slider;
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
public class BumiFinal extends Application{
    private static final float WIDTH = 1024;
    private static final float HEIGHT = 720;
    
    private double anchorX, anchorY;
    private double anchorAngleX = 0;
    private double anchorAngleY = 0;
    private final DoubleProperty angleX = new SimpleDoubleProperty(0);
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);
    
    private final PointLight pointLight = new PointLight();
    private final Sphere bumi = new Sphere (150);
    private final Sphere bulan = new Sphere(25);
    
    @Override
    public void start (Stage primaryStage) throws Exception {
        Camera camera = new PerspectiveCamera(true);
        camera.setNearClip(1);
        camera.setFarClip(10000);
        camera.translateZProperty().set(-1000);
        
        SmartGroup dunia = new SmartGroup();
        dunia.getChildren().add(permulaanDunia());
        dunia.getChildren().addAll(prepareLightSource());
        
        
        Slider slider= controlSlider();
        dunia.translateZProperty().bind(slider.valueProperty());
        
        Group root = new Group();
        root.getChildren().add(dunia);   
        root.getChildren().add(background());
        root.getChildren().add(slider);
        
        Scene scene = new Scene(root, WIDTH, HEIGHT, true);
        scene.setFill(Color.SILVER);
        scene.setCamera(camera);
        
        pengaturanMouse(dunia, scene, primaryStage);
        
        primaryStage.getIcons().add(new Image("/resources/Icon/bumiIcon.png"));
        primaryStage.setTitle("Bumi");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        bumiAnimasi();
        
        AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            pointLight.setRotate(pointLight.getRotate() + 0.5);
        }
        };
        timer.start();
    }
    
    private void bumiAnimasi(){
        AnimationTimer waktu = new AnimationTimer(){
            @Override
            public void handle (long now){
                bumi.rotateProperty().set(bumi.getRotate() + 1);
            }
        };
        waktu.start();
    }
    
  private Node[] prepareLightSource() {
    pointLight.setColor(Color.WHITE);
    pointLight.getTransforms().add(new Translate(0, -30, 350));
    pointLight.setRotationAxis(Rotate.Y_AXIS);

    PhongMaterial materialBulan = new PhongMaterial();
    materialBulan.setDiffuseMap(new Image(getClass().getResourceAsStream("/resources/Bulan/bulan-d.jpg")));
    materialBulan.setBumpMap(new Image(getClass().getResourceAsStream("/resources/Bulan/bulan-b.jpg")));
    bulan.getTransforms().setAll(pointLight.getTransforms());
    bulan.rotateProperty().bind(pointLight.rotateProperty());
    bulan.rotationAxisProperty().bind(pointLight.rotationAxisProperty());
    bulan.setMaterial(materialBulan);
    return new Node[]{pointLight, bulan};
  }
    
    
    private ImageView background(){
        Image backImage = new Image(BumiBackground.class.getResourceAsStream("/resources/Background/spacer.jpg"));
        ImageView iv = new ImageView(backImage);
        iv.setPreserveRatio(true);
        iv.getTransforms().add(new Translate(-backImage.getWidth()/2, -backImage.getHeight()/2, 720));
        return iv;
    }
    
    private Slider controlSlider(){
        Slider slider = new Slider();
        slider.setMax(400);
        slider.setMin(-350);
        slider.setPrefWidth(300d);
        slider.setLayoutX(-150);
        slider.setLayoutY(200);
        slider.setShowTickLabels(true);
        slider.setTranslateZ(5);
        slider.setStyle("-fx-base: black");
        return slider;
    }
    
    
    private Node permulaanDunia(){
        PhongMaterial materialBumi = new PhongMaterial();
        materialBumi.setDiffuseMap(new Image(getClass().getResourceAsStream("/resources/Bumi/bumi-d.jpg")));
        materialBumi.setSelfIlluminationMap(new Image(getClass().getResourceAsStream("/resources/Bumi/bumi-l.jpg")));
        materialBumi.setSpecularMap(new Image(getClass().getResourceAsStream("/resources/Bumi/bumi-s.jpg")));
        materialBumi.setBumpMap(new Image(getClass().getResourceAsStream("/resources/Bumi/bumi-n.jpg")));
        

        bumi.setRotationAxis(Rotate.Y_AXIS);
        bumi.setMaterial(materialBumi);
        return bumi;
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
