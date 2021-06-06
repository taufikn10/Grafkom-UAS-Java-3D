/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TataSurya;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
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
public class TataSurya extends Application {
    private static final float WIDTH = 1280;
    private static final float HEIGHT = 720;
    
    private double anchorX, anchorY;
    private double anchorAngleX = 0;
    private double anchorAngleY = 0;
    private final DoubleProperty angleX = new SimpleDoubleProperty(0);
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);
    
    private final PointLight pointLight = new PointLight();    
    private final Sphere merkurius = new Sphere (5);
    private final Sphere sun = new Sphere (100);
    private final Sphere venus = new Sphere (7);
    private final Sphere bumi = new Sphere (8);
    private final Sphere mars = new Sphere (6);
    private final Sphere jupiter = new Sphere (30);
    private final Sphere saturn = new Sphere (20);
    private final Sphere uranus = new Sphere (15);
    private final Sphere neptunus = new Sphere (12);
    
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        Camera camera = new PerspectiveCamera(true);
        camera.setNearClip(1);
        camera.setFarClip(20000);
        camera.translateZProperty().set(-1000);
        
        SmartGroup tataSurya = new SmartGroup();
        tataSurya.getChildren().addAll(Matahari());
        tataSurya.getChildren().add(Merkurius());
        tataSurya.getChildren().add(Venus());
        tataSurya.getChildren().add(Bumi());
        tataSurya.getChildren().add(Mars());
        tataSurya.getChildren().add(Jupiter());
        tataSurya.getChildren().add(Saturnus());
        tataSurya.getChildren().add(Uranus());
        tataSurya.getChildren().add(Neptunus());
        
        Slider slider= controlSlider();
        tataSurya.translateZProperty().bind(slider.valueProperty());
        
        Group sistem = new Group();
        sistem.getChildren().add(tataSurya);   
        sistem.getChildren().add(background());
        sistem.getChildren().add(slider);
        
        Scene scene = new Scene(sistem, WIDTH, HEIGHT, true);
        scene.setFill(Color.SILVER);
        scene.setCamera(camera);
        
        pengaturanMouse(tataSurya, scene, primaryStage);
        
        primaryStage.getIcons().add(new Image("/resources/Icon/tataSuryaIcon.png"));
        primaryStage.setTitle("Tata Surya");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        sunAnimasi();
        merkuriusAnimasi();
        venusAnimasi();
        bumiAnimasi();
        marsAnimasi();
        jupiterAnimasi();
        saturnAnimasi();
        uranusAnimasi();
        neptunusAnimasi();
    }
    
    
    private void sunAnimasi(){
        AnimationTimer waktuS = new AnimationTimer(){
            @Override
            public void handle (long now){
               pointLight.rotateProperty().set(pointLight.getRotate() + 2);
            }
        };
        waktuS.start();
    }
    
    private void merkuriusAnimasi(){
        AnimationTimer waktuM = new AnimationTimer(){
            @Override
            public void handle (long now){
                merkurius.setRotate(merkurius.getRotate() + 1.7);
            }
        };
        waktuM.start();
    }
    
    private void venusAnimasi(){
        AnimationTimer waktuV = new AnimationTimer(){
            @Override
            public void handle (long now){
                venus.rotateProperty().set(venus.getRotate() + 1.5);
            }
        };
        waktuV.start();
    }
    
    private void bumiAnimasi(){
        AnimationTimer waktuB = new AnimationTimer(){
            @Override
            public void handle (long now){
                bumi.rotateProperty().set(bumi.getRotate() +1.2);
            }
        };
        waktuB.start();
    }
    
    private void marsAnimasi(){
        AnimationTimer waktu = new AnimationTimer(){
            @Override
            public void handle (long now){
                mars.rotateProperty().set(mars.getRotate() + 1);
            }
        };
        waktu.start();
    }
    
    private void jupiterAnimasi(){
        AnimationTimer waktuJ = new AnimationTimer(){
            @Override
            public void handle (long now){
                jupiter.setRotate(jupiter.getRotate() + 0.8);
            }
        };
        waktuJ.start();
    }
    
    private void saturnAnimasi(){
        AnimationTimer waktuS = new AnimationTimer(){
            @Override
            public void handle (long now){
                saturn.setRotate(saturn.getRotate() + 0.6);
            }
        };
        waktuS.start();
    }
    
    private void uranusAnimasi() {
        AnimationTimer waktuU = new AnimationTimer(){
            @Override
            public void handle (long now){
                uranus.setRotate(uranus.getRotate() + 0.4);
            }
        };
        waktuU.start();
    }
    
    private void neptunusAnimasi() {
        AnimationTimer waktuN = new AnimationTimer(){
            @Override
            public void handle (long now){
                neptunus.setRotate(neptunus.getRotate() + 0.2);
            }
        };
        waktuN.start();
    }
    
    private ImageView background(){
        Image backImage = new Image(TataSurya.class.getResourceAsStream("/resources/Background/space.jpg"));
        ImageView iv = new ImageView(backImage);
        iv.setPreserveRatio(true);
        iv.getTransforms().add(new Translate(-backImage.getWidth()/2, -backImage.getHeight()/2, 720));
        return iv;
    }

    private Node[] Matahari(){
        pointLight.setColor(Color.WHITE);
        pointLight.setRotationAxis(Rotate.Y_AXIS);
        PhongMaterial materialSun = new PhongMaterial();
        materialSun.setDiffuseColor(Color.valueOf("#FEF7A4"));
        materialSun.setDiffuseMap(new Image(getClass().getResourceAsStream("/resources/TataSurya/sun-m.jpg")));
        materialSun.setBumpMap(new Image(getClass().getResourceAsStream("/resources/TataSurya/pluto-b.jpg")));
        

        sun.getTransforms().setAll(pointLight.getTransforms());
        sun.rotateProperty().bind(pointLight.rotateProperty());
        sun.rotationAxisProperty().bind(pointLight.rotationAxisProperty()); 
        sun.setMaterial(materialSun);
        return new Node[]{pointLight, sun};
    }
    
    private Node Merkurius(){

        PhongMaterial materialMerkurius = new PhongMaterial();
        materialMerkurius.setDiffuseMap(new Image(getClass().getResourceAsStream("/resources/TataSurya/mercury-m.jpg")));
       
        
        merkurius.getTransforms().add(new Translate(0, 0, 130));
        merkurius.setRotationAxis(Rotate.Y_AXIS);
        merkurius.setMaterial(materialMerkurius);
        return merkurius;
    }
    
    
    
    private Node Venus(){
        PhongMaterial materialVenus = new PhongMaterial();
        materialVenus.setDiffuseMap(new Image(getClass().getResourceAsStream("/resources/TataSurya/venus-m.jpg")));
        materialVenus.setDiffuseMap(new Image(getClass().getResourceAsStream("/resources/TataSurya/venus-b.jpg")));
        
        venus.getTransforms().add(new Translate(0, 0, 150));
        venus.setRotationAxis(Rotate.Y_AXIS);
        venus.setMaterial(materialVenus);
        return venus;
    }
    
    
    private Node Bumi(){
        PhongMaterial materialBumi = new PhongMaterial();
        materialBumi.setDiffuseMap(new Image(getClass().getResourceAsStream("/resources/Bumi/bumi-d.jpg")));
        materialBumi.setSelfIlluminationMap(new Image(getClass().getResourceAsStream("/resources/Bumi/bumi-l.jpg")));
        materialBumi.setSpecularMap(new Image(getClass().getResourceAsStream("/resources/Bumi/bumi-s.jpg")));
        materialBumi.setBumpMap(new Image(getClass().getResourceAsStream("/resources/Bumi/bumi-n.jpg")));
        
        bumi.getTransforms().add(new Translate(0, 0, 180));
        bumi.setRotationAxis(Rotate.Y_AXIS);
        bumi.setMaterial(materialBumi);
        return bumi;
    }
    
        
    private Node Mars(){
        PhongMaterial materialMars = new PhongMaterial();
        materialMars.setDiffuseMap(new Image(getClass().getResourceAsStream("/resources/Mars/mars-d.jpg")));
        materialMars.setSpecularMap(new Image(getClass().getResourceAsStream("/resources/Mars/mars-s.jpg")));
        materialMars.setBumpMap(new Image(getClass().getResourceAsStream("/resources/Mars/mars-n.jpg")));
        
        mars.getTransforms().add(new Translate(0, 0, 210));
        mars.setRotationAxis(Rotate.Y_AXIS);
        mars.setMaterial(materialMars);
        return mars;
    }
    
    private Node Jupiter(){
        PhongMaterial materialMars = new PhongMaterial();
        materialMars.setDiffuseMap(new Image(getClass().getResourceAsStream("/resources/TataSurya/jupiter-m.jpg")));
        
        jupiter.getTransforms().add(new Translate(0, 0, 255));
        jupiter.setRotationAxis(Rotate.Y_AXIS);
        jupiter.setMaterial(materialMars);
        return jupiter;
    }
    
    private Node Saturnus(){
        PhongMaterial materialSaturn = new PhongMaterial();
        materialSaturn.setDiffuseMap(new Image(getClass().getResourceAsStream("/resources/TataSurya/saturn-m.jpg")));
        
        saturn.getTransforms().add(new Translate(0, 0, 310));
        saturn.setRotationAxis(Rotate.Y_AXIS);
        saturn.setMaterial(materialSaturn);
        return saturn;
    }
    
    
    private Node Uranus(){
        PhongMaterial materialSaturn = new PhongMaterial();
        materialSaturn.setDiffuseMap(new Image(getClass().getResourceAsStream("/resources/TataSurya/uranus-m.jpg")));
        
        uranus.getTransforms().add(new Translate(0, 0, 350));
        uranus.setRotationAxis(Rotate.Y_AXIS);
        uranus.setMaterial(materialSaturn);
        return uranus;
    }
    
    private Node Neptunus(){
        PhongMaterial materialSaturn = new PhongMaterial();
        materialSaturn.setDiffuseMap(new Image(getClass().getResourceAsStream("/resources/TataSurya/neptune-m.jpg")));
        
        neptunus.getTransforms().add(new Translate(0, 0, 400));
        neptunus.setRotationAxis(Rotate.Y_AXIS);
        neptunus.setMaterial(materialSaturn);
        return neptunus;
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
