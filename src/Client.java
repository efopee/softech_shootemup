
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class Client extends Network {
	
	private Socket socket = null;
	private ObjectOutputStream out = null;
	private ObjectInputStream in = null;

	Client(Control c) {
		super(c);
	}

	
	private class ReceiverThread implements Runnable {

		public void run() {
			//System.out.println("Waiting for gamestate...");
			try {
				while (true) {
					
					//System.out.println("Waiting for gamestate...");
					SerialGameState received = (SerialGameState) in.readObject();
					SerialKeystroke keystroke = ctrl.recvedGamestate(received);
					send(keystroke);
				}
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
				System.err.println("Server disconnected!");
				ctrl.showError(new String("Server disconnected!"));
			} finally {
				disconnect();
			}
		}
	}
	
	@Override
	void connect(String ip) {
		disconnect();
		try {
			socket = new Socket(ip, 10007);

			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			out.flush();

			Thread rec = new Thread(new ReceiverThread());
			rec.start();
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host");
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection. ");
			JOptionPane.showMessageDialog(null, "Cannot connect to server!");
		}
	}
	
	
	@Override
	void disconnect() {
		try {
			if (out != null)
				out.close();
			if (in != null)
				in.close();
			if (socket != null)
				socket.close();
		} catch (IOException ex) {
			System.err.println("Error while closing conn.");
		}
	}

	@Override
	void send(SerialKeystroke keystroke) {
		if (out == null)
			return;
		try {
			out.writeObject(keystroke);
			out.flush();
		} catch (IOException ex) {
			System.err.println("Send error.");
		}
	}
}
