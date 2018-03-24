import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client implements AutoCloseable {
	private Socket serverSocket;
	
	public Client(String serverIp, int serverPort) throws UnknownHostException, IOException {
		serverSocket = new Socket(serverIp, serverPort);
		System.out.println("Connected to " + serverSocket.toString());
	}

	public void start() {
		try (
			PrintWriter serverOut = new PrintWriter(serverSocket.getOutputStream(), true);

		    BufferedReader serverIn = new BufferedReader(
		        new InputStreamReader(serverSocket.getInputStream()));
		) {
			String input = "";
			while (!input.equals("quit")) {
				System.out.print(" - talk to server: ");
				
				input = System.console().readLine();
				
				// send message
				serverOut.println(input);
				
				// wait for response
				String serverInput = serverIn.readLine();
				if (serverInput == null) {
					System.out.println(" - received null, closing connection");
					break;
				} else {
					System.out.println(" - answer from server: " + serverInput);
				}
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void close() throws Exception {
		serverSocket.close();
	}
}
