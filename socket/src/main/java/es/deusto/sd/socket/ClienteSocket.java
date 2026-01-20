package es.deusto.sd.socket;

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
    public String sendMessage(String message) {
        try {
            out.writeUTF(message);//Pasa el mensaje a la "puerta" de envio de mensajes del socket
            System.out.println(" Mensaje enviado: " + message);

            String response = in.readUTF();
            System.out.println(" Respuesta recibida: " + response);

            return response;

        } catch (IOException e) {
            System.err.println(" Error enviando mensaje: " + e.getMessage());
            return null;
        }
    }

    public void close() {
        try {
            if (in != null) in.close(); //Si detecta que in no esta null lo cierra, al igual con el resto
            if (out != null) out.close();
            if (socket != null) socket.close();
            System.out.println(" Conexión cerrada correctamente");
        } catch (IOException e) {
            System.err.println(" Error cerrando conexión: " + e.getMessage());
        }
    }

    
}
