import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class EchoServer implements AutoCloseable {

	private boolean quiting = false;
	private ServerSocket socket;
	private List<EchoServerThread> threads = new ArrayList<EchoServerThread>();
	
	public EchoServer(int port) throws IOException {
		socket = new ServerSocket(port);
		System.out.println("Created server on " + socket.toString());
	}

	public void start() {
		while (!quiting) {
			System.out.println("Listeing on " + socket.toString());
			try {
				Socket clientSocket = socket.accept();
				System.out.println("Client accepted: " + clientSocket.toString());
				
				EchoServerThread t = new EchoServerThread(this, clientSocket);
				threads.add(t);
				
				t.start();
			} catch (IOException e) {
				System.out.println("Error accepting client:");
				e.printStackTrace();
			}
		}
		
	    System.out.println("Closing server");
	}
	
	public void quit() {
		quiting = true;
	}

	@Override
	public void close() throws Exception {
		// close threads
		for (EchoServerThread t : threads) {
			System.out.println("Closing thread");
			t.close();
			try {
				t.join();
			} catch (InterruptedException e) {
				System.out.println("Error joining EchoServerThread:");
				e.printStackTrace();
			}
		}
	    
		// close socket
		socket.close();
	}
}
