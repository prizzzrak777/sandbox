package GUI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by user on 15.05.2017.
 */
public class MyDrawPanel extends JPanel {
    public void paintComponent(Graphics g){
        int red, green, blue;

//        g.setColor(Color.orange);
//        g.fillRect(20,50,100,100);

//        Image image = new ImageIcon("catzilla.jpg").getImage();
//        g.drawImage(image,3,4,this);

        g.fillRect(0,0,this.getWidth(),this.getHeight());

        red = (int) (Math.random()*255);
        green = (int) (Math.random()*255);
        blue = (int) (Math.random()*255);

        Color randomColor = new Color(red,green,blue);
        g.setColor(randomColor);
        g.fillOval(70,70,100,100);

        Graphics2D g2d = (Graphics2D) g;

        red = (int) (Math.random()*255);
        green = (int) (Math.random()*255);
        blue = (int) (Math.random()*255);

        Color startColor = new Color(red,green,blue);

        red = (int) (Math.random()*255);
        green = (int) (Math.random()*255);
        blue = (int) (Math.random()*255);

        Color endtColor = new Color(red,green,blue);

        GradientPaint gradient = new GradientPaint(70,70,startColor,150,150,endtColor);
        g2d.setPaint(gradient);
        g2d.fillOval(70,70,100,100);

    }
}
