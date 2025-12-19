package es.deusto.sd.authenticus.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClienteSocket {

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String serverIP;
    private int serverPort;
    private static String DELIMITER = "#";

    public ClienteSocket(String servIP,int servPort) throws IOException{
        serverIP = servIP;
		serverPort = servPort;
        try {
			socket = new Socket(serverIP, serverPort);
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
			System.out.println("Se ha conectado correctamente al server: " + serverIP + ":" + serverPort);
		} catch (UnknownHostException e) {
			System.err.println("  Socket error: " + e.getMessage());
		} catch (EOFException e) {
			System.err.println("  EOF error: " + e.getMessage());
		} catch (IOException e) {
			System.err.println("  IO error: " + e.getMessage());
		}
    }

    
}
