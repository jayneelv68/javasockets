package voip_final;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.sound.sampled.*;

public class VUClient extends JFrame {

boolean stopaudioCapture = false;
ByteArrayOutputStream byteOutputStream;
AudioFormat adFormat;
TargetDataLine targetDataLine;
AudioInputStream InputStream;
SourceDataLine sourceLine;
Graphics g;
DatagramSocket serverSocket;
byte[] receiveData;

public static void main(String args[]) {
    new VUClient();
    
}

public VUClient() {
    final JButton capture = new JButton("Capture");
    final JButton stop = new JButton("Stop");
    //final JButton play = new JButton("Playback");

    capture.setEnabled(true);
    stop.setEnabled(false);
    //play.setEnabled(false);

    capture.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            capture.setEnabled(false);
            stop.setEnabled(true);
          //  play.setEnabled(false);
            captureAudio();
           
        }
    });
    getContentPane().add(capture);

    stop.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            capture.setEnabled(true);
            stop.setEnabled(false);
           // play.setEnabled(true);
            stopaudioCapture = true;
            targetDataLine.close();
        }
    });
    getContentPane().add(stop);

   /* play.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            playAudio();
        }
    });*/
    //getContentPane().add(play);

    getContentPane().setLayout(new FlowLayout());
    setTitle("Capture/Playback Demo");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(400, 100);
    getContentPane().setBackground(Color.white);
    setVisible(true);

    g = (Graphics) this.getGraphics();
}
AudioInputStream InputStream2;

private void captureAudio() {
    try {
        adFormat = getAudioFormat();
        DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, adFormat);
        targetDataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
        targetDataLine.open(adFormat);
        targetDataLine.start();

        Thread captureThread = new Thread(new CaptureThread());
        captureThread.start();
        Thread PlayThread = new Thread(new PlayThread());
        serverSocket = new DatagramSocket(43523);
        receiveData = new byte[10000];

        PlayThread.start();
        //System.out.println("LOLLL");
     
        
    } catch (Exception e) {
        StackTraceElement stackEle[] = e.getStackTrace();
        for (StackTraceElement val : stackEle) {
            System.out.println(val);
        }
        System.exit(0);
    }
}

private void playAudio() {
    try {
        byte audioData[] = byteOutputStream.toByteArray();
        InputStream byteInputStream = new ByteArrayInputStream(audioData);
        AudioFormat adFormat = getAudioFormat();
        InputStream = new AudioInputStream(byteInputStream, adFormat, audioData.length / adFormat.getFrameSize());
        DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, adFormat);
        sourceLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
        sourceLine.open(adFormat);
        sourceLine.start();
      
    } catch (Exception e) {
        System.out.println(e);
        System.exit(0);
    }
}

private AudioFormat getAudioFormat() {
    float sampleRate = 16000.0F;
   int sampleInbits = 16;    int channels = 1;    boolean signed = true;
    boolean bigEndian = false;
    return new AudioFormat(sampleRate, sampleInbits, channels, signed, bigEndian);
}

class CaptureThread extends Thread {

    byte tempBuffer[] = new byte[10000];

    public void run() {

        byteOutputStream = new ByteArrayOutputStream();
        stopaudioCapture = false;
        try {
            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress IPAddress = InetAddress.getByName("192.168.1.102");
            while (!stopaudioCapture) {
                int cnt = targetDataLine.read(tempBuffer, 0, tempBuffer.length);
                if (cnt > 0) {
                    DatagramPacket sendPacket = new DatagramPacket(tempBuffer, tempBuffer.length, IPAddress, 43527);
                    clientSocket.send(sendPacket);
                    byteOutputStream.write(tempBuffer, 0, cnt);
                }
            }
            byteOutputStream.close();
        } catch (Exception e) {
            System.out.println("CaptureThread::run()" + e);
            System.exit(0);
        }
    }
}
SourceDataLine sourceLine2;
        
/*public void runVOIP() {
    try {
        DatagramSocket serverSocket = new DatagramSocket(43527);
        byte[] receiveData = new byte[10000];
        
        while (true) {
        
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);
            System.out.println("RECEIVED: " + receivePacket.getAddress().getHostAddress() + " " + receivePacket.getPort());
            String ip=receivePacket.getAddress().getHostAddress();
         
            try {
                byte audioData[] = receivePacket.getData();
                InputStream byteInputStream = new ByteArrayInputStream(audioData);
                AudioFormat adFormat = getAudioFormat();
                InputStream2 = new AudioInputStream(byteInputStream, adFormat, audioData.length / adFormat.getFrameSize());
                DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, adFormat);
                sourceLine2 = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
               
                sourceLine2.open(adFormat);
              
                sourceLine2.start();
                Thread playThread = new Thread(new PlayThread());
        
                playThread.start();
            } catch (Exception e) {
                System.out.println(e);
                System.exit(0);
            }
           
        }
}
catch (Exception e) {
        e.printStackTrace();
    }
}
*/
class PlayThread extends Thread {
    
    
    
    public void run() {
        try {
        
            byte audioData[]=new byte[1000];
            while(true){
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);
            System.out.println("RECEIVED: " + receivePacket.getAddress().getHostAddress() + " " + receivePacket.getPort());
            String ip=receivePacket.getAddress().getHostAddress();
            
            try {
                 audioData = receivePacket.getData();
                InputStream byteInputStream = new ByteArrayInputStream(audioData);
                AudioFormat adFormat = getAudioFormat();
                InputStream2 = new AudioInputStream(byteInputStream, adFormat, audioData.length / adFormat.getFrameSize());
                DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, adFormat);
                sourceLine2 = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
               
                sourceLine2.open(adFormat);
              
                sourceLine2.start();
                
                Thread playThread = new Thread(new PlayThread());
        
                playThread.start();
            } catch (Exception e) {
                System.out.println(e);
                System.exit(0);
           
        }
}
        }
catch (Exception e) {
        e.printStackTrace();
    }
        

    }
}


}