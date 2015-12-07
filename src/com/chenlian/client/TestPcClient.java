package com.chenlian.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class TestPcClient {

	Socket socket = null;
	InetAddress serverAddr = null;
	BufferedOutputStream out = null;
	BufferedInputStream in = null;
	
	public TestPcClient(){
		create();
		start();
	}
	
	public void create(){
		try {
			Runtime.getRuntime().exec(
					"adb shell am broadcast -a NotifyServiceStop");
			Thread.sleep(3000);
			Runtime.getRuntime().exec("adb forward tcp:12580 tcp:10086");
			Thread.sleep(3000);
			Runtime.getRuntime().exec(
					"adb shell am broadcast -a NotifyServiceStart");
			Thread.sleep(3000);
		} catch (IOException e3) {
			e3.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//连接应用
	public boolean start(){
		try {
			serverAddr = InetAddress.getByName("127.0.0.1");
			socket = new Socket(serverAddr, 12580);
			
			out = new BufferedOutputStream(socket.getOutputStream());
			in = new BufferedInputStream(socket.getInputStream());
			
			if(socket.isConnected()){
				System.out.println("Connected!");
				return true;
			}else{
				System.out.println("Failed to connect!");
				return false;
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	//重启
	public void reboot(){
		try {
			Runtime.getRuntime().exec("adb reboot");
			System.out.println("Successed to reboot");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//关机
	public void shutdown(){
		try {
			Runtime.getRuntime().exec("adb shutdown");
			System.out.println("Successed to shutdown");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//拨号
	public void call(String number){
		try {
			Runtime.getRuntime().exec("adb shell am start -a android.intent.action.CALL -d tel:" + number);
			System.out.println("Successed to call");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//发送短信
	public void sendSMS(String number){
		try {
			StringBuffer str = new StringBuffer("*SMS*");
			str.append(number);
			out.write(str.toString().getBytes());
			out.flush();
			System.out.println(number + " finish sending the data");
			if(in != null){
				String strFormsocket = readFromSocket(in);
				System.out.println("the data sent by server is:\r\n"
						+ strFormsocket);
			}
			System.out.println("=============================================");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//上网
	public void netWork(String local){
		try {
			StringBuffer str = new StringBuffer("#location#");
			str.append(local);
			out.write(str.toString().getBytes());
			out.flush();
			System.out.println(local + " finish sending the data");
			if(in != null){
				String strFormsocket = readFromSocket(in);
				System.out.println("the data sent by server is:\r\n"
						+ strFormsocket);
			}
			System.out.println("=============================================");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void close(){
		if (socket != null) {
			try {
				out.write("exit".getBytes());
				out.flush();
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("socket.close()");
		}
	}
	
	/* 从InputStream流中读数据 */
	public static String readFromSocket(InputStream in) {
		int MAX_BUFFER_BYTES = 4000;
		String msg = "";
		byte[] tempbuffer = new byte[MAX_BUFFER_BYTES];
		try {
			int numReadedBytes = in.read(tempbuffer, 0, tempbuffer.length);
			msg = new String(tempbuffer, 0, numReadedBytes, "utf-8");

			tempbuffer = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Log.v(Service139.TAG, "msg=" + msg);
		return msg;
	}
}
