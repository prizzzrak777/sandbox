package MIDI;

import javax.sound.midi.*;

/**
 * Created by user on 16.05.2017.
 */
public class MiniMusicPlayer2 implements ControllerEventListener {

    public static void main(String[] args) {
        MiniMusicPlayer2 mini = new MiniMusicPlayer2();
        mini.go();
    }

    private void go() {
        try{
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();

            int[] eventsIWant = {127};
            sequencer.addControllerEventListener(this,eventsIWant);

            Sequence seq = new Sequence(Sequence.PPQ,4);
            Track track = seq.createTrack();

            for(int i=0;i<128;i++){
                track.add(makeEvent(144,1,i,100,i));
                track.add(makeEvent(176,1,127,0,i));
                track.add(makeEvent(128,1,i,100,i+2));
            }

            sequencer.setSequence(seq);
            sequencer.setTempoInBPM(220);
            sequencer.start();

        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
    }

    private MidiEvent makeEvent(int comd, int chan, int one, int two , int tick) {
        MidiEvent event = null;
        try{
            ShortMessage a = new ShortMessage();
            a.setMessage(comd,chan,one,two);
            event = new MidiEvent(a,tick);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
        return event;
    }

    @Override
    public void controlChange(ShortMessage event) {
       System.out.println("ля");
    }
}
