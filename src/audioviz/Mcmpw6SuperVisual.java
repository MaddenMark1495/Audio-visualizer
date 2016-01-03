/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audioviz;

import static java.lang.Integer.min;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.shape.Rectangle;
import java.util.Random;
import static javafx.scene.paint.Color.color;
/**
 *
 * @author markmadden
 */
public class Mcmpw6SuperVisual implements Visualizer {
    
    private String name = "Super Visual";
    
    private Integer numBands;
    private AnchorPane vizPane;
    
    private String vizPaneInitialStyle = "";
    
    private Double bandHeightPercentage = 1.3;
    //private Double minEllipseRadius = 10.0;  
    private Double rotatePhaseMultiplier = 300.0;
    
    private Double width = 0.0;
    private Double height = 0.0;
    private Double depth = 0.0;
    
    private Double bandWidth = 0.0;
    private Double bandHeight = 0.0;
    private Double bandDepth = 0.0;
    private Double halfBandHeight = 0.0;
    
    private Double startHue = 260.0;
    
    private Rectangle [] rectangles;
 
    public Mcmpw6SuperVisual(){
    }
    
    @Override
    public String getName(){
        return name;
    }
    
    @Override
    public void start(Integer numBands, AnchorPane vizPane) {
       end();
       
       vizPaneInitialStyle = vizPane.getStyle();
       
       this.numBands = numBands;
       this.vizPane = vizPane;
       
       height = vizPane.getHeight();
       width = vizPane.getWidth();
       
       Rectangle clip = new Rectangle(width,height);
       clip.setLayoutX(0);
       clip.setLayoutY(0);
       vizPane.setClip(clip);
       
       bandWidth = width / numBands;
       bandHeight = height * bandHeightPercentage;
       halfBandHeight = bandHeight / 2;
       rectangles = new Rectangle[numBands];
       
       for(int i =0; i < numBands; i++ ){
           Rectangle rectangle = new Rectangle();
           rectangle.setHeight(bandWidth / 2 + bandWidth * i);
           rectangle.setWidth(bandWidth /2);
          // rectangle.setArcHeight(20);
           //rectangle.setArcWidth(20);
           rectangle.setX(bandWidth * i);
           rectangle.setY(0);
           rectangle.setFill(Color.hsb(startHue, .5, .2, 1.0));
          
           vizPane.getChildren().add(rectangle);
           rectangles[i] = rectangle;
           
           
           
       }
       
    }

    @Override
    public void end() {
        if(rectangles!=null){
            for(int i=0;i<rectangles.length;i++){
                vizPane.getChildren().remove(rectangles[i]);
                
            }
            rectangles = null;
            vizPane.setClip(null);
            vizPane.setStyle(vizPaneInitialStyle);
        }
    }


    @Override
    public void update(double timestamp, double duration, float[] magnitudes, float[] phases) {
        if(rectangles == null){
            return;
        }
        Integer num = min(rectangles.length, magnitudes.length);
        Random rand = new Random();
     
        int r = rand.nextInt(255);
        int g = rand.nextInt(255);
        int b = rand.nextInt(255);
        int op = rand.nextInt(100);
        for(int i = 0; i< num; i++){
            rectangles[i].setHeight((60.0 + magnitudes[i]/60.0) * halfBandHeight);
            rectangles[i].setRotate(phases[i] * rotatePhaseMultiplier);
            rectangles[i].setStrokeWidth(op);
            rectangles[i].setFill(Color.rgb(r,g,b));
         
     }
    }
}
