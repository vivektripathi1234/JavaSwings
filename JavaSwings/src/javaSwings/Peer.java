/**
 * 
 */
package javaSwings;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * @author Vivek
 *
 */
public class Peer extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	ServerSocket listeningSock;
	
	private JFrame jf;
	private JLabel jl1;
	private JLabel jl2;
	private JTextField tf1;
	private JTextField tf2;
	private JButton btn1;
	
	/**
	 * 
	 */
	private void initializeUI() {
		jf = new JFrame();
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		jl1 = new JLabel("Address ");
		jl1.setBounds(20, 50, 85, 20);
		jf.getContentPane().add(jl1);
		
		jl2 = new JLabel("Port ");
		jl2.setBounds(20, 80, 50, 20);
		jf.getContentPane().add(jl2);
		
		tf1 = new JTextField();
		tf1.setBounds(100, 50, 150, 20);
		jf.getContentPane().add(tf1);
		
		tf2 = new JTextField();
		tf2.setBounds(100, 80, 70, 20);
		jf.getContentPane().add(tf2);
		
		btn1 = new JButton("connect");
		btn1.setBounds(20, 120, 85, 30);
		jf.getContentPane().add(btn1);
		
		jf.setSize(450, 300);
		jf.setLayout(null);
		jf.setVisible(true);
		
	}
	
	/**
	 * 
	 */
	private void peerWindow(){
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					listeningSock = new ServerSocket(1234);
					System.out.println("listening on "+listeningSock);
					
					Socket cSock = listeningSock.accept();
					JOptionPane.showMessageDialog(getOwner(), "connected to "+cSock);
					
					new ConnectionWindow(cSock);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
		
		btn1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String addr = tf1.getText();
				Integer prt = Integer.parseInt(tf2.getText());
				
				try {
					InetAddress ip = InetAddress.getByName(addr);
					
					Socket connectSocket = new Socket(ip, prt);
					JOptionPane.showMessageDialog(getOwner(), "connected to "+connectSocket);
					new ConnectionWindow(connectSocket);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
	}	

	/**
	 * @throws HeadlessException
	 */
	public Peer() throws HeadlessException {
		// TODO Auto-generated constructor stub
		initializeUI();
		peerWindow();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				new Peer();
			}
		});
	}

}
