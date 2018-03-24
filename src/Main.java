
public class Main {
	
	static void showUsage(String[] args) {
		System.out.println("server usage: Main s <serverport>");
		System.out.println("client usage: Main c <serverip> <serverport>");
	}
	
	public static void main(String[] args) {
		if (args.length < 1) {
			showUsage(args);
			return;
		}
		
		if (args[0].equals("s")) {
			// server
			
			if (args.length < 2) {
				showUsage(args);
				return;
			}
			
			int port = Integer.parseInt(args[1]);
			
			try (
				EchoServer server = new EchoServer(port);
			) {
				server.start();
			} catch (Exception e) {
				System.out.println("Error creating EchoServer:");
				e.printStackTrace();
			}
		} else if (args[0].equals("c")) {
			// client
			
			if (args.length <3) {
				showUsage(args);
				return;
			}
			
			String serverIp = args[1];
			int serverPort = Integer.parseInt(args[2]);
			
			try (
				Client client = new Client(serverIp, serverPort);
			) {
				client.start();
			} catch (Exception e1) {
				System.out.println("Error creating Client:");
				e1.printStackTrace();
			}
		}
	}
	
}
