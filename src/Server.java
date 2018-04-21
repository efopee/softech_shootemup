import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Server extends Network {

	private ServerSocket serverSocket = null;
	private Socket clientSocket = null;
	private ObjectOutputStream out = null;
	private ObjectInputStream in = null;

	Server(Control c) {
		super(c);
	}

	private class ReceiverThread implements Runnable {

		public void run() {
			try {
				//System.out.println("Waiting for Client" + serverSocket.getLocalPort());
				clientSocket = serverSocket.accept();
				ctrl.startGame();
				//System.out.println("Just connected to " + clientSocket.getRemoteSocketAddress());
			} catch (IOException e) {
				System.err.println("Accept failed.");
				disconnect();
				return;
			}


			try {
				out = new ObjectOutputStream(clientSocket.getOutputStream());
				in = new ObjectInputStream(clientSocket.getInputStream());
				out.flush();
			} catch (IOException e) {
				System.err.println("Error while getting streams.");
				disconnect();
				return;
			}

			try {
				while (true) {
					//receive
					/*
					 * TODO
					 * I don't know what should happen here
					 * but it should call
					 * 
					 * 		void recvedKeystroke(SerialKeystroke keystroke);
					 * 
					 * function of ctrl member object with the received SerialKeystroke object.
					 *
					 */
				}
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
				System.err.println("Client disconnected!");
			} finally {
				disconnect();
			}
		}
	}

	@Override
	void connect(String ip) {
		disconnect();
		try {
			serverSocket = new ServerSocket(10007);

			Thread rec = new Thread(new ReceiverThread());
			rec.start();
		} catch (IOException e) {
			System.err.println("Could not listen on port: 10007.");
		}
	}


	@Override
	void disconnect() {
		try {
			if (out != null)
				out.close();
			if (in != null)
				in.close();
			if (clientSocket != null)
				clientSocket.close();
			if (serverSocket != null)
				serverSocket.close();
		} catch (IOException ex) {
			Logger.getLogger(Server.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}

	@Override
	void send(SerialGameState gamestate) {
		if (out == null)
			return;
		//System.out.println();
		try {
			out.writeObject(gamestate);
			out.flush();
		} catch (IOException ex) {
			System.err.println("Send error.");
		}
	}

}
