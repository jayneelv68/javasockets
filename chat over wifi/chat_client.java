package finalmp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/**
 *
 * @author jayne
 */
public class chat_client extends javax.swing.JFrame {

    static Socket s;
     static DataInputStream din;
    static DataOutputStream dout;
    
    
    public chat_client() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        msg_area = new javax.swing.JScrollPane();
        mg_area = new javax.swing.JTextArea();
        msg_send = new javax.swing.JButton();
        msg_text = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        mg_area.setColumns(20);
        mg_area.setRows(5);
        msg_area.setViewportView(mg_area);

        msg_send.setText("Send");
        msg_send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                msg_sendActionPerformed(evt);
            }
        });

        msg_text.setText("Enter your text here.....");
        msg_text.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                msg_textActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 36)); // NOI18N
        jLabel1.setText("Client");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(msg_area)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(msg_text)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(msg_send)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(264, 264, 264)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(208, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(msg_area, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(msg_text)
                    .addComponent(msg_send, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }
    private void msg_sendActionPerformed(java.awt.event.ActionEvent evt) {
        
         try{
             
        String msgout="";
        msgout=msg_text.getText().trim();
        dout.writeUTF(msgout);
          
         }
        catch(Exception e)
        {
            
        }
    }
    private void msg_textActionPerformed(java.awt.event.ActionEvent evt) {
    }
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
      
    }
    public static void main(String args[]) {
       
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(chat_client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(chat_client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(chat_client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(chat_client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new chat_client().setVisible(true);
            }
        });
          try{
        s=new Socket("192.168.43.240",1201);
        din=new DataInputStream(s.getInputStream());
            dout=new DataOutputStream(s.getOutputStream());
            
            String msgin="";
            while(!msgin.equals("exit"))
            {
                msgin=din.readUTF();
                mg_area.setText(mg_area.getText().trim()+"\n Server:\t"+msgin);
                
            }
            
        }
        catch(Exception e)
        {
            
        }
        
        
        
    }

   
    private javax.swing.JLabel jLabel1;
    private static javax.swing.JTextArea mg_area;
    private javax.swing.JScrollPane msg_area;
    private javax.swing.JButton msg_send;
    private javax.swing.JTextField msg_text;
}
