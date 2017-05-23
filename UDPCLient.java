/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package voip;


import com.sun.jna.NativeLibrary;
import java.awt.Canvas;
import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.CanvasVideoSurface;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;
import static voip.UDPVIDEO.main;

/**
 *
 * @author Abhi
 */
public class UDPCLient {
    public UDPCLient(){
        String a[]=new String[10];
        try {
            main(a);
        } catch (Exception ex) {
            Logger.getLogger(UDPVIDEO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private static final String NATIVE_LIBRARY_SEARCH_PATH = "C:\\Program Files (x86)\\VideoLAN\\VLC";

    public static void main(String[] args) {
       NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), NATIVE_LIBRARY_SEARCH_PATH);
    MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory();
    EmbeddedMediaPlayer mediaPlayer = mediaPlayerFactory.newEmbeddedMediaPlayer();

    Canvas canvas = new Canvas();
    canvas.setBackground(Color.black);
    CanvasVideoSurface videoSurface = mediaPlayerFactory.newVideoSurface(canvas);
    mediaPlayer.setVideoSurface(videoSurface);

    JFrame f = new JFrame();
    f.add(canvas);
    f.setSize(600, 600);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.setVisible(true);
    String publicServer = "udp://@:23433";
    System.out.println("Capturing from '" + publicServer + "'");
    //f.setTitle("Capturing from Public Server 'rtp://" + publicIP + ":" + publicPort + "'");
    mediaPlayer.playMedia(publicServer);
    
    }
          
}