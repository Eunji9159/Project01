package kr.co.tcp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Server extends Thread{

   static ArrayList<Socket> list=new ArrayList<>();
   static int idxnum;
   
   @Override
   public void run() {
      Socket sock = list.get(idxnum);
      InputStream is = null;
      BufferedReader br = null;
      
      try {
         is= sock.getInputStream();
         br=new BufferedReader(new InputStreamReader(is));
         
         while(true){
            
            String msg=null;
            msg=br.readLine();
            System.out.println("ser:"+ msg);
            
            for(int i=0; i<list.size(); i++){
               Socket temp = list.get(i);
               
               if(temp!=null){
                  BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(temp.getOutputStream()));
                  
                  //
                  
                  bw.write(msg);
                  bw.newLine();
                  bw.flush();
               }
            }
         }
         
      } catch (IOException e) {
         list.set(idxnum, null);
      }   
}
   
   
   
   
   
   public static void main(String[] args) {
      
      ServerSocket serv=null;
      
      try {
         serv=new ServerSocket(5000);
         System.out.println("Server Socket made it.");
         
         while(true){
            Socket sock=serv.accept();
            System.out.println("client connected.");
            
            Server client=new Server();
            client.start();
            list.add(sock);
            client.idxnum=list.size()-1;
            System.out.println("current Client num : "+ client.getName()); // 현재 접속중인 클라이언트 반환
         }
         
      } catch (IOException e) {
         
         try {
            if(serv!=null){serv.close();}
            
         } catch (IOException e1) {
            e1.printStackTrace();
         }
      }
      
      
   }
}