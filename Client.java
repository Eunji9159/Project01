package kr.co.tcp;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Graphics;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.io.*;
import java.net.*;


public class Client extends JFrame implements ActionListener{
   
   JPanel p1,p2,cardPane, p3;
   JButton startBtn, endBtn, c, r, p;
   CardLayout cl;
   JLabel label;
   static BufferedWriter bw=null;
   private TextArea msgView=new TextArea();
   

   public Client() {
      
            setTitle("가위바위보 게임(ver0.0.1)");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            cardPane = new JPanel(); //
            cl = new CardLayout();
            cardPane.setLayout(cl);
            
            final ImageIcon icon = new ImageIcon("backimg.PNG");
            p1 = new JPanel(){
               public void paint(Graphics g) {
                  g.drawImage(icon.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
                  super.paintComponents(g);
               }   
            };
            cardPane.add(p1);
            
            p2 = new JPanel();
            p2.setLayout(null);
            msgView = new TextArea("게임시작! 선택하고싶은 버튼을 누르세요! \n");
            msgView.setBounds(5,5,375,235);
            msgView.setEditable(false);
            p2.add(msgView);
            
            c = new JButton("Scissors");
            c.setBounds(80, 240, 70, 30);
            c.addActionListener(this);
            p2.add(c);
            
            r = new JButton("Rock");
            r.setBounds(160, 240, 70, 30);
            r.addActionListener(this);
            p2.add(r);
            
            p = new JButton("Paper");
            p.setBounds(240, 240, 70, 30);
            p.addActionListener(this);
            p2.add(p);
            cardPane.add(p2);
            
            JPanel buttonPane = new JPanel(); // 전체 버튼 패널
            JButton startBtn = new JButton("게임시작");
            startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               String str = e.getActionCommand();
               if(str.equals("게임시작")){
                  cl.next(cardPane); 
                  
               }else if(str.equals("처음으로")){
                  cl.previous(cardPane);
               }   
            }
            });
            JButton endBtn = new JButton("처음으로");
            endBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               String str = e.getActionCommand();
               if(str.equals("게임시작")){
                  cl.next(cardPane); 
                  
               }else if(str.equals("처음으로")){
                  cl.previous(cardPane);
               }   
            }
            });
            buttonPane.add(startBtn);
            buttonPane.add(endBtn);
            
            add(buttonPane, BorderLayout.SOUTH);
            add(cardPane, BorderLayout.CENTER);
            setBounds(400,100,400,400);
            setVisible(true);
         }
   
   
   @Override
   public void actionPerformed(ActionEvent e) {
      
      String msg = e.getActionCommand();
      if(msg.equals("Scissors")){
         msgView.append("가위를 선택했습니다. \n");
      }else if(msg.equals("Rock")){
         msgView.append("바위를 선택했습니다. \n");
      }else if(msg.equals("Paper")){
         msgView.append("보를 선택했습니다. \n");
      }
      
      
      try {
         bw.write(msg);
         bw.newLine();
         bw.flush();
      } catch (IOException ae) {
         ae.printStackTrace();
      }
      
   
   }
   
   

   public static void main(String[] args) {
      
      Client cl=new Client();
      
      Socket sock=null;
      InputStream is=null;
      OutputStream os=null;
      
      InputStreamReader isr=null;
      OutputStreamWriter osw=null;
      
      BufferedReader br=null;
      
      try {
         sock=new Socket("localhost",5000);
         is=sock.getInputStream();
         os=sock.getOutputStream();
         
         isr=new InputStreamReader(is);
         osw=new OutputStreamWriter(os);
         
         br=new BufferedReader(isr);
         bw=new BufferedWriter(osw);
         
         while(true){
            String msg=br.readLine();
            System.out.println(msg);
         }
         
      } catch (UnknownHostException e) {
         e.printStackTrace();
         
      } catch (IOException e) {
         e.printStackTrace();
         
      } finally{
         try {
            if(br!=null){br.close();}
            if(isr!=null){isr.close();}
            if(is!=null){is.close();}
            
         } catch (IOException e) {
            e.printStackTrace();
         }
      }

   }
}


