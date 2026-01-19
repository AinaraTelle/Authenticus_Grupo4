package es.deusto.sd.authenticus.external;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ProccesSocketServer {
    private int port;

    public ProccesSocketServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);

        while (true) {
            Socket socket = serverSocket.accept(); 
            new Thread(() -> handleClient(socket)).start(); 
        }
    }

    private void handleClient(Socket socket) {
        try (DataInputStream in = new DataInputStream(socket.getInputStream());
             DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {

            //Aqui lee el mensaje
            String mensaje = in.readUTF(); 
            System.out.println("Recibido: " + mensaje);

            
            String[] partes = mensaje.split("#");//Separamos el mensaje del cliente donde basicamente separamos por id e tipo de Analisis
            String tipo = partes[0];
            Long idArchivo = Long.parseLong(partes[1]);

            
            int tiempo = 1000 + (int)(Math.random() * 2000);// Se dice que hay que simular entre 1 y 3 segundos de espera
            Thread.sleep(tiempo);

            
            double resultado = Math.random();// El resultado aleatorio de 0 a 1

            
            out.writeUTF(String.valueOf(resultado));// Envia el resultado que espera el cliente
            System.out.println("Resultado enviado: " + resultado);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    public static void main(String[] args) throws IOException {
        int port = 5000; 
        ProccesSocketServer server = new ProccesSocketServer(port);
        server.start();
    }
}

