import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class EchoServerThread extends Thread {
	private EchoServer server;
	private Socket socket;
	private boolean closing = false;

	public EchoServerThread(EchoServer server, Socket clientSocket) {
		this.server = server;
		this.socket = clientSocket;
	}

	public void run() {
		System.out.println("Server thread running for client " +
				socket.getInetAddress().toString() +
				":" + socket.getPort());


		try (
			Socket socket = this.socket;
				
		    PrintWriter clientOut =
		        new PrintWriter(socket.getOutputStream(), true);
				
		    BufferedReader clientIn = new BufferedReader(
		        new InputStreamReader(socket.getInputStream()));
		) {
			String inputLine = "";
		    while (!closing) {
		    	inputLine = clientIn.readLine();
		    	if (inputLine == null) {
		    		System.out.println("Null input line");
		    		closing = true;
		    	} else {		    	
			    	System.out.println(" - message from " + socket.toString() + ":");
			    	System.out.println("\t" + inputLine);
			        
			    	System.out.println(" - answering: " + inputLine);
			    	clientOut.println(inputLine);
			        
			        if (inputLine.equals("quit"))
			            server.quit();
		    	}
		    }
		} catch (IOException e) {
			System.out.println("Error during EchoServerThread execution:");
			e.printStackTrace();
		}
	    
	    
	    System.out.println("Server thread closing for client " + 
	    		socket.getInetAddress().toString() +
				":" + socket.getPort());
	}

	public void close() {
		closing = true;		
	}
}
