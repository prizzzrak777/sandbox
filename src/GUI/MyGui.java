package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by user on 16.05.2017.
 */
public class MyGui implements ActionListener {

    JFrame frame;
    JButton button1;
    JButton button2;

    public static void main(String[] args) {
        MyGui gui = new MyGui();
        gui.go();
    }

    private void go() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        button1 = new JButton("Button1");
        button1.addActionListener(this);

        button2 = new JButton("Button2");
        button2.addActionListener(this);

        frame.add(BorderLayout.NORTH,button1);
        frame.add(BorderLayout.SOUTH,button2);

        frame.setSize(100,100);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if(event.getSource() == button1){
            JDialog dialog = new MyDialog("button1"); //new JDialog(frame, "button1");
            dialog.pack();
            dialog.setVisible(true);
        } else {
            JDialog dialog = new MyDialog("button2"); //new JDialog(frame, "button2");
            dialog.pack();
            dialog.setVisible(true);

        }

        }


}
