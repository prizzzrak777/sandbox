package MIDI;

import javax.sound.midi.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by user on 16.05.2017.
 */
public class BeatBox {
    JPanel mainPanel;
    ArrayList<JCheckBox> checkBoxList;
    Sequencer sequencer;
    Sequence sequence;
    Track track;
    JFrame theFrame;

    String[] instrumentNames = {"Bass Drum","Closed Hi-Hat",
            "Open Hi-Hat", "Acoustic Share","Crash Cymbal", "Hand Clap",
            "High Tom", "Hi Bongo", "Maracas", "Whistle", "Low Conga",
            "Cowbell","Vibraslap","Low-mid Tom","High Agogo","Open Hi Conga"
    };

    int[] instruments = {35,42,46,38,49,39,50,60,70,72,64,56,58,47,67,63};


    public static void main(String[] args) {
        new BeatBox().buildGUI();
    }

    private void buildGUI() {
        theFrame = new JFrame("Cyber BeatBox");
        theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BorderLayout layout = new BorderLayout();
        JPanel background = new JPanel(layout);
        background.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        checkBoxList = new ArrayList<JCheckBox>();
        Box buttonBox = new Box(BoxLayout.Y_AXIS);

        JButton start = new JButton("Start");
        start.addActionListener(new MyStartListener());
        buttonBox.add(start);

        JButton stop = new JButton("Stop");
        stop.addActionListener(new MyStopListener());
        buttonBox.add(stop);

        JButton upTempo = new JButton("Tempo Up");
        upTempo.addActionListener(new MyUpTempoListener());
        buttonBox.add(upTempo);

        JButton downTempo = new JButton("Tempo Down");
        downTempo.addActionListener(new MyDownTempoListener());
        buttonBox.add(downTempo);

        JButton serialize = new JButton("Serialize");
        serialize.addActionListener(new MySerializeListener());
        buttonBox.add(serialize);

        JButton deserialize = new JButton("Deserialize");
        deserialize.addActionListener(new MyDeserializeListener());
        buttonBox.add(deserialize);

        JCheckBox autoPlay = new JCheckBox("AutoPlay");
        autoPlay.setSelected(false);
        autoPlay.setName("autoPlay");
        buttonBox.add(autoPlay);

        Box nameBox = new Box(BoxLayout.Y_AXIS);
        for (int i = 0; i < 16; i++) {
            nameBox.add(new Label(instrumentNames[i]));
        }

        background.add(BorderLayout.EAST, buttonBox);
        background.add(BorderLayout.WEST, nameBox);

        theFrame.getContentPane().add(background);

        GridLayout grid = new GridLayout(16,16);
        grid.setVgap(1);
        grid.setHgap(2);
        mainPanel = new JPanel(grid);
        background.add(BorderLayout.CENTER, mainPanel);

        for (int i = 0; i < 256; i++) {
            JCheckBox c = new JCheckBox();
            c.setSelected(false);
            checkBoxList.add(c);
            mainPanel.add(c);

        }

        setUpMidi();

        theFrame.setBounds(50,50,300,300);
        theFrame.pack();
        theFrame.setVisible(true);

    }

    private void setUpMidi() {
        try {
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequence = new Sequence(Sequence.PPQ,4);
            track = sequence.createTrack();
            sequencer.setTempoInBPM(120);

        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
    }

    public void buildTrackAndStart(){
      int[] trackList = new int[16];
      sequence.deleteTrack(track);
      track = sequence.createTrack();

        for (int i = 0; i <16 ; i++) {
            trackList = new int[16];
            int key = instruments[i];

            for (int j = 0; j <16 ; j++) {
                JCheckBox jc = (JCheckBox) checkBoxList.get(j+(16*i));
                if(jc.isSelected()){
                    trackList[j]=key;
                } else {
                    trackList[j] = 0;
                }

            }
            makeTracks(trackList);
            track.add(makeEvents(176,1,127,0,16));
        }

        track.add(makeEvents(192,9,1,0,15));
        try{
            sequencer.setSequence(sequence);
            sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
            sequencer.start();
            sequencer.setTempoInBPM(120);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }


    }

    private MidiEvent makeEvents(int comd, int chan, int one, int two, int tick) {
        MidiEvent event = null;
        try{
            ShortMessage a = new ShortMessage();
            a.setMessage(comd,chan,one,two);
            event = new MidiEvent(a,tick);

        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        } return event;
    }

    private void makeTracks(int[] list) {
        for (int i = 0; i < 16 ; i++) {
            int key = list[i];

            if(key!=0){
                track.add(makeEvents(144,9,key,100,i));
                track.add(makeEvents(128,9,key,100,i+1));

            }
        }
    }

    private class MyStartListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            buildTrackAndStart();

        }
    }

    private class MyStopListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            sequencer.stop();
        }
    }

    private class MyUpTempoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            float tempoFactor = sequencer.getTempoFactor();
            sequencer.setTempoFactor((float)(tempoFactor * 1.03));
        }
    }

    private class MyDownTempoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            float tempoFactor = sequencer.getTempoFactor();
            sequencer.setTempoFactor((float)(tempoFactor * 0.97));
        }
    }


    private class MySerializeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean[] checkboxState = new boolean[256];

            for (int i = 0; i < 256; i++) {
                JCheckBox check = (JCheckBox) checkBoxList.get(i);
                if (check.isSelected()) {
                    checkboxState[i] = true;
                }
            }
             try{



                 JFileChooser fileSave = new JFileChooser(".");

                 FileFilter fileFilter = new FileNameExtensionFilter("BeatBox  serializable file (.ser)", "ser", "SER");
                 fileSave.setFileFilter(fileFilter);

                 fileSave.showSaveDialog(theFrame);
                 File fileOut = fileSave.getSelectedFile();
                 FileOutputStream fileStream = new FileOutputStream(fileOut);

                // FileOutputStream fileStream = new FileOutputStream(new File("Checkbox.ser"));

                 ObjectOutputStream os = new ObjectOutputStream(fileStream);
                 os.writeObject(checkboxState);

             } catch (FileNotFoundException e1) {
                 e1.printStackTrace();
             } catch (IOException e1) {
                 e1.printStackTrace();
             }


        }
    }

    private class MyDeserializeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean[] checkboxState = null;
            try{
                JFileChooser fileSave = new JFileChooser(".");

                FileFilter fileFilter = new FileNameExtensionFilter("BeatBox  serializable file (.ser)", "ser", "SER");
                fileSave.setFileFilter(fileFilter);


                fileSave.showOpenDialog(theFrame);
                File fileIN = fileSave.getSelectedFile();

                FileInputStream fileIS = new FileInputStream(fileIN);
                //FileInputStream fileIS = new FileInputStream(new File("Checkbox.ser"));
                ObjectInputStream is = new ObjectInputStream(fileIS);
                checkboxState = (boolean[]) is.readObject();

            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }

            for (int i = 0; i < 256; i++) {
                JCheckBox check = (JCheckBox) checkBoxList.get(i);
                if(checkboxState[i]){
                    check.setSelected(true);
                } else {
                    check.setSelected(false);
                }

            }
            sequencer.stop();


           // System.out.println(findComponentByName(theFrame,"autoPlay",JComponent.class).getName());

           JCheckBox autoPlay =  findComponentByName(theFrame,"autoPlay", JCheckBox.class); //Auto cast on 3d argument
            if((autoPlay != null) && (autoPlay.isSelected()))
               buildTrackAndStart();

        }
    }

    // First varint with Recursive iterator
    public static java.util.List<Component> getAllComponents(final Container c) {
        Component[] comps = c.getComponents();
        java.util.List<Component> compList = new ArrayList<Component>();
        for (Component comp : comps) {
            compList.add(comp);
            if (comp instanceof Container)
                compList.addAll(getAllComponents((Container) comp));
        }
        return compList;
    }

    // Second variant with Recursive Stream (since v 8)
    public static <T extends JComponent> java.util.List<T> findComponents(
            final Container container,
            final Class<T> componentType

    ) {
        return Stream.concat(
                Arrays.stream(container.getComponents())
                        .filter(componentType::isInstance)
                        .map(componentType::cast),
                Arrays.stream(container.getComponents())
                        .filter(Container.class::isInstance)
                        .map(Container.class::cast)
                        .flatMap(c -> findComponents(c, componentType).stream())
        ).collect(Collectors.toList());
    }

    public static <T extends JComponent> T findComponentByName(
            final Container container,
            final String componentName,
            final Class<T> componentType

    ) {
        return
                (T) findComponents(container,componentType)
                        .stream()
                        .filter(s->s.getName()!=null && s.getName().equals(componentName))
                       // .filter(s->s.toString().contains(componentName))
                        .findFirst()
                        //.orElse(null)
                        .get();
    }




}
