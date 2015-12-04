package com.chenlian.client;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class ClientWindow extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField phoneNumber;
	private JTextField location;
	private JButton reboot, shutdown, call, sendMessage, network;
	private String[] btnName = { "重启手机", "关闭电源", "拨打号码", "发送短信", "浏览网页" };
	private TestPcClient client;
	private boolean isConnected = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientWindow frame = new ClientWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ClientWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 205, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		reboot = new JButton("重启手机");
		reboot.setBounds(0, 23, 88, 23);
		contentPane.add(reboot);

		shutdown = new JButton("关闭电源");
		shutdown.setBounds(98, 23, 89, 23);
		contentPane.add(shutdown);

		phoneNumber = new JTextField();
		phoneNumber.setBounds(0, 47, 185, 20);
		contentPane.add(phoneNumber);
		phoneNumber.setColumns(10);

		call = new JButton("拨打号码");
		call.setBounds(0, 69, 89, 23);
		contentPane.add(call);

		location = new JTextField();
		location.setBounds(0, 94, 185, 20);
		contentPane.add(location);
		location.setColumns(10);

		sendMessage = new JButton("发送短信");
		sendMessage.setBounds(98, 69, 89, 23);
		contentPane.add(sendMessage);

		network = new JButton("浏览网页");
		network.setBounds(0, 115, 187, 23);
		contentPane.add(network);

		reboot.addActionListener(this);
		shutdown.addActionListener(this);
		call.addActionListener(this);
		sendMessage.addActionListener(this);
		network.addActionListener(this);

		client = new TestPcClient();
		isConnected = client.start();
		
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				client.close();
			}
		});
	}

	public void actionPerformed(ActionEvent e) {
		String source = e.getActionCommand();

		if (source.equals(btnName[0])) {
			client.reboot();
		}

		if (source.equals(btnName[1])) {
			client.shutdown();
		}

		if (source.equals(btnName[2])) {
			String number = phoneNumber.getText();
			if (Tools.isMobileNO(number)) {
				client.call(number);
			} else {
				client.call("10086");
			}
		}

		if (source.equals(btnName[3])) {

		}

		if (source.equals(btnName[4])) {
			String location = network.getText();
			if (Tools.isUrl(location)) {
				client.netWork(location);
			} else {
				client.netWork("http://www.baidu.com");
			}
		}
	}
}
