package es.deusto.sd.authenticus.external;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.io.*;
import java.net.Socket;

public class ProccesSocketClient {
    private String ip;
    private int port;

    public ProccesSocketClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public String enviarAnalisis(String tipo, Long idArchivo) {
        // Abrimos el socket, enviamos, recibimos y cerramos (try-with-resources)
        try (Socket socket = new Socket(ip, port);
             DataOutputStream out = new DataOutputStream(socket.getOutputStream());
             DataInputStream in = new DataInputStream(socket.getInputStream())) {
            
            String mensaje = tipo + "#" + idArchivo;
            out.writeUTF(mensaje);
            
            // Recibimos el resultado (el número aleatorio entre 0 y 1)
            return in.readUTF(); 
            
        } catch (IOException e) {
            return "ERROR: " + e.getMessage();
        }
    }
}
