/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package voip;

import java.util.logging.Level;
import java.util.logging.Logger;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.headless.HeadlessMediaPlayer;

/**
 *
 * @author Abhi
 */
public class UDPVIDEO {
    public UDPVIDEO(){
        String a[]=new String[10];
        try {
            main(a);
        } catch (InterruptedException ex) {
            Logger.getLogger(UDPVIDEO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void main(String[] args) throws InterruptedException {
          new NativeDiscovery().discover();
          String media = "dshow://";
          String publicIP = "192.168.1.102";
          short publicPort = 23432;
          String options = formatRtpStream(publicIP, publicPort);
           System.out.println("Streaming '" + media + "' to '" + options + "'");
            String m[]={"file-catching=0:dshow-size=640*420,:live-caching=300"};
            MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory();
            HeadlessMediaPlayer mediaPlayer = mediaPlayerFactory.newHeadlessMediaPlayer();

            mediaPlayer.playMedia(media,options,"live-caching=0",":file-caching=0","network-caching=300",":dshow-size=848*480"," :sout-all"," :sout-keep");
            //mediaPlayer.playMedia(media, options, ":no-sout-rtp-sap", ":no-sout-standard-sap", ":sout-all", ":sout-keep");
              
            Thread.currentThread().join(); // Don't exit
    
          //String media = args[0];
    // :dshow-vdev= :dshow-adev= :dshow-size=640*420 :live-caching=300
    //:sout=#transcode{vcodec=h264,acodec=mpga,ab=128,channels=2,samplerate=44100}:udp{dst=192.168.1.103:12321} :sout-all :sout-keep
    
    }

    private static String formatRtpStream(String publicIP, short publicPort) {
         //To change body of generated methods, choose Tools | Templates.
          StringBuilder sb = new StringBuilder(200);
    
         sb.append(":sout=#transcode{vcodec=mp4v,fps=30,acodec=mpga,ab=128,channels=2,samplerate=44100}:udp{dst=");// :sout-all :sout-keep
    
    // :dshow-vdev= :dshow-adev= :dshow-size=640*480 :dshow-aspect-ratio=4\:3 :dshow-chroma= :dshow-fps=30 :no-dshow-config :no-dshow-tuner :dshow-tuner-channel=0 :dshow-tuner-frequency=0 :dshow-tuner-country=0 :dshow-tuner-standard=0 :dshow-tuner-input=0 :dshow-video-input=-1 :dshow-video-output=-1 :dshow-audio-input=-1 :dshow-audio-output=-1 :dshow-amtuner-mode=1 :dshow-audio-channels=0 :dshow-audio-samplerate=0 :dshow-audio-bitspersample=0 :live-caching=300
        sb.append(publicIP);
        sb.append(":");
        sb.append(publicPort);
        //sb.append(",mux=ts}");
        return sb.toString();
    }
    
}