package Server;
import java.net.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.concurrent.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Server extends JFrame {
      ServerSocket server;
      Socket socket;
      BufferedReader br;
      PrintWriter out;

      private JLabel head = new JLabel("Server Area");
private JTextArea messageArea = new JTextArea(); 
private JTextField messageInput = new JTextField();
private Font font = new Font("Roboto", Font.PLAIN, 20);

   public Server() {
        try {
            server = new ServerSocket(1234);
            System.out.println("Server is ready to accept connection");
            socket = server.accept();
            System.out.println("Client connected");
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            createGUI();
            handleEvents();
            startReading();

           // startWriting();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void handleEvents() {
            messageInput.addKeyListener(new KeyListener(){

               @Override
                public void keyTyped(KeyEvent e) {
                }

                @Override
                public void keyPressed(KeyEvent e) {
                    
                    }
                @Override
                public void keyReleased(KeyEvent e) {
                    if (e.getKeyCode() == 10) {
                        String content = messageInput.getText();
                    
                        messageArea.append("Me: " + content + "\n");
                        out.println(content);
                        out.flush();
                        messageInput.setText("");
                        messageInput.requestFocus();
                    }
                }
            });
        }
        private void createGUI() {
        this.setTitle("Server Messenger");
        this.setSize(500, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        head.setFont(font);
        messageArea.setFont(font);
        messageInput.setFont(font);
        messageArea.setEditable(false);
        head.setHorizontalAlignment(SwingConstants.CENTER);
        head.setBorder(BorderFactory.createEmptyBorder(20, 20  , 20, 20));
        messageArea.setEditable(false);
        messageInput.setHorizontalAlignment(SwingConstants.CENTER);
        this.setLayout(new BorderLayout());
        this.add(head, BorderLayout.NORTH);
        JScrollPane scroll = new JScrollPane(messageArea);
        this.add(scroll, BorderLayout.CENTER);
        this.add(messageInput, BorderLayout.SOUTH);
        this.setVisible(true);
        }
       public void startReading(){
          Runnable r1 = () -> {
              System.out.println("Reader started...");
              try {
                  while (true) {
                      String msg = br.readLine();
                      if (msg.equals("exit")) {
                       System.out.println("Client terminated the chat");
                        JOptionPane.showMessageDialog(this, "Client terminated the chat", "Chat Ended", JOptionPane.INFORMATION_MESSAGE);
                        messageInput.setEnabled(false);
                          socket.close();
                          break;
                      }
                     // System.out.println("Client: " + msg);
                     messageArea.append("Client: " + msg + "\n");
                  }
              } catch (Exception e) {
                  e.printStackTrace();
              }
          };
            new Thread(r1).start();
       }
         public void startWriting(){
            Runnable r2 = () -> {
                System.out.println("Writer started...");
                try {
                    while (true) {
                        BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                        String content = br1.readLine();
                        out.println(content);
                        out.flush();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
            new Thread(r2).start();
         }
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        new Server();
    }
}
