package voip_final;

import java.io.*;
import java.net.*;
import javax.sound.sampled.*;

public class VUServer {
boolean stopaudioCapture=false;
ByteArrayOutputStream byteOutputStream;
AudioFormat adFormat;
TargetDataLine targetDataLine;
AudioInputStream InputStream;
String ip;
SourceDataLine sourceLine;

private AudioFormat getAudioFormat() {
    float sampleRate =  16000.0F;
    int sampleInbits = 16;
    int channels = 1;
    boolean signed = true;
    boolean bigEndian = false;
    return new AudioFormat(sampleRate, sampleInbits, channels, signed, bigEndian);
}

public static void main(String args[]) {
    new VUServer().runVOIP();
              
}

public void runVOIP() {
    try {
        DatagramSocket Socket = new DatagramSocket(43527);
        byte[] receiveData = new byte[10000];
        boolean first=true;
        while (true) {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            Socket.receive(receivePacket);
            if(receivePacket!=null && first==true)
            captureAudio();
            first=false;
                
            System.out.println("RECEIVED: " + receivePacket.getAddress().getHostAddress() + " " + receivePacket.getPort());
            ip=receivePacket.getAddress().getHostAddress();
            try {
                byte audioData[] = receivePacket.getData();
                InputStream byteInputStream = new ByteArrayInputStream(audioData);
                AudioFormat adFormat = getAudioFormat();
                InputStream = new AudioInputStream(byteInputStream, adFormat, audioData.length / adFormat.getFrameSize());
                DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, adFormat);
                sourceLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
                sourceLine.open(adFormat);
                sourceLine.start();
                Thread playThread = new Thread(new PlayThread());
                playThread.start();
            } catch (Exception e) {
                System.out.println(e);
                System.exit(0);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}

class PlayThread extends Thread {

    
    byte tempBuffer[] = new byte[10000];

    public void run() {
        try {
            int cnt;
            while ((cnt = InputStream.read(tempBuffer, 0, tempBuffer.length)) != -1) {
                if (cnt > 0) {
                     System.out.println(tempBuffer[0]);
                    sourceLine.write(tempBuffer, 0, cnt);
                                   }
            }
            //  sourceLine.drain();
            // sourceLine.close();
        } catch (Exception e) {
            System.out.println(e);
            System.exit(0);
        }
    }
}
TargetDataLine targetDataLine2;
private void captureAudio() {
    try {
        adFormat = getAudioFormat();
        DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, adFormat);
        targetDataLine2 = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
        targetDataLine2.open(adFormat);
        targetDataLine2.start();
        Thread captureThread = new Thread(new CaptureThread());
        captureThread.start();
    } catch (Exception e) {
        StackTraceElement stackEle[] = e.getStackTrace();
        for (StackTraceElement val : stackEle) {
            System.out.println(val);
        }
        System.exit(0);
    }
}


class CaptureThread extends Thread {

    byte tempBuffer[] = new byte[10000];

    public void run() {
       
        byteOutputStream = new ByteArrayOutputStream();
        stopaudioCapture = false;
        try {
            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress IPAddress = InetAddress.getByName(ip);
            while (!stopaudioCapture) {
                int cnt = targetDataLine2.read(tempBuffer, 0, tempBuffer.length);
                if (cnt > 0) {
              
                    DatagramPacket sendPacket = new DatagramPacket(tempBuffer, tempBuffer.length, IPAddress, 43523);
                    clientSocket.send(sendPacket);
                    System.out.println("SENTTTTT");
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




}