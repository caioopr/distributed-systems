import java.util.Arrays;
import java.util.Scanner;
import java.util.Objects;

import java.io.OutputStream;
import java.io.InputStream;

import java.net.Socket;
import java.net.ServerSocket;

public class TCPServer {
	public static void main(String[] args) {
	try {
			ServerSocket ss = new ServerSocket(8080);
			
			byte[] buffer = new byte[1_000];
		
		
			ListenerTCPRunnable listener = new ListenerTCPRunnable(ss, buffer);
			new Thread(listener).start(); // this thread will print income messages from client
		
			Scanner scann = new Scanner(System.in);
		
			while(true){
		
				System.out.println("Enter a message:");
				String message = scann.nextLine();
				
	
				TCPServer.sendMessage(message, 3000);
			
		}
		
		
		} catch (Exception ex){
			System.out.println("Error creating socket");
		}
		
		
	}
	
	
	public static void sendMessage(String msg, int port){
		try{
			Socket sc = new Socket("localhost", port);
			OutputStream os = sc.getOutputStream();
			os.write(msg.getBytes()); //send msg using the socket
		} catch (Exception ex){
			System.out.println("Error sending message.");
		}
	}
}


class ListenerTCPRunnable implements Runnable{
	ServerSocket ss;
	byte[] buffer;
	
	public ListenerTCPRunnable(ServerSocket ss, byte[] buffer){
		this.ss = ss;
		this.buffer = buffer;
	}
	

	
	@Override
	public void run(){
		
		try{
			while(true){
				Socket sc = ss.accept();
				InputStream is = sc.getInputStream();
				is.read(buffer);
				String msg = new String(buffer);
				System.out.println(msg);
			}
		}catch(Exception ex){
			System.out.println("Error receiving packet");
		}
	}
}








