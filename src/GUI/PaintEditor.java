package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by user on 16.05.2017.
 */
public class PaintEditor extends JFrame {
    private int prevX,prevY;
    private Color color = Color.BLACK;
    private JButton jButton = new JButton("ColorChooser");

    public PaintEditor() {
        Container c = getContentPane();
        c.setLayout(new FlowLayout());
        c.add(jButton);

        jButton.addActionListener( new ButtonActionListener());
        addMouseListener(new PaintMouseAdapter());
        addMouseMotionListener(new PaintMouseMotionAdapter());

    }

    private class ButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            color = JColorChooser.showDialog(((Component) e.getSource()).getParent(),"Demo", color);

        }
    }

    private class PaintMouseAdapter extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent ev) {
            setPreviousCoordinates(ev.getX(),ev.getY());

        }
    }


    private class PaintMouseMotionAdapter extends MouseMotionAdapter {
        @Override
        public void mouseDragged(MouseEvent ev) {
           Graphics g = getGraphics();
           g.setColor(color);
           g.drawLine(prevX,prevY,ev.getX(),ev.getY());
           setPreviousCoordinates(ev.getX(),ev.getY());

        }

//        @Override
//        public void mouseMoved(MouseEvent e) {
//
//        }
    }
    private void setPreviousCoordinates(int aPrevX, int aPrevY) {
      prevX = aPrevX;
      prevY = aPrevY;

    }

    public static void main(String[] args) {
        PaintEditor pe = new PaintEditor();
        pe.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent ev) {
                super.windowClosing(ev);
                System.exit(0);
            }
        });
        pe.setBounds(200,100,300,200);
        pe.setTitle("MicroPaint");
        pe.setVisible(true);
    }


}
