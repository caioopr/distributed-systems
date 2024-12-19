import java.util.Arrays;
import java.util.Scanner;
import java.util.Objects;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;

public class UDPServer {
	public static void main(String[] args) {
	try {
			DatagramSocket socket = new DatagramSocket(8080);
	
			byte[] buffer = new byte[1_000];
			DatagramPacket packet= new DatagramPacket(buffer, buffer.length);
		
			ListenerRunnable listener = new ListenerRunnable(socket, packet);
			new Thread(listener).start(); // thie thread will print income messages from client
		
			Scanner scann = new Scanner(System.in);
		
			while(true){
		
				System.out.println("Enter a message:");
				String message = scann.nextLine();
				
				if(Objects.equals(message, "finish")) break;
	
				UDPServer.sendMessage(socket, message, 3000);
			
		}
		

		socket.close();
		
		} catch (Exception ex){
			System.out.println("Error creating socket");
		}
		
		
	}
	
	
	public static void sendMessage(DatagramSocket socket, String msg, int port){
		try{
			InetAddress addr = InetAddress.getByName("localhost");
			DatagramPacket packet = new DatagramPacket(msg.getBytes(), msg.length(), addr, port);
			socket.send(packet);
		} catch (Exception ex){
			System.out.println("Error sending message.");
		}
	}
}


class ListenerRunnable implements Runnable{
	DatagramSocket socket;
	DatagramPacket packet;
	
	public ListenerRunnable(DatagramSocket socket, DatagramPacket packet){
		this.socket = socket;
		this.packet = packet;
	}
	
	@Override
	public void run(){
		
		try{
			while(true){
				socket.receive(packet);
				System.out.println( new String(packet.getData(), 0, packet.getLength()));
			}
		}catch(Exception ex){
			System.out.println("Error receiving packet");
		}
	}
}






