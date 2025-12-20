package es.deusto.sd.authenticus.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorSocket {
    
    private static final int puerto = 5000;
    private static final String DELIMITER = "#";

    public ServidorSocket()throws IOException{
        ServerSocket serverSocket=new ServerSocket(puerto);
        System.out.println("Server en el socket 5000");//abre puerto 5000 y se queda esperandoa cliente

        Socket cliente= serverSocket.accept();//cliente se conecta
        System.out.println("Cliente conectado");//SE BLOQUEA HASTA QUE CLIENTE ENTRE EN LOCALHOST 5000

        //recibir info del cliente
        DataInputStream in = new DataInputStream(cliente.getInputStream());
        DataOutputStream out = new DataOutputStream(cliente.getOutputStream());

        //caso de como recibir mensajes
        String mensaje = in.readUTF();//recibe mensaje de cliente
        System.out.println("Cliente dice: " + mensaje);

        out.writeUTF("OK"+DELIMITER+"Mensaje recibido");//responde a mensaje de cliente

        //cierra conexiones
        cliente.close();
        serverSocket.close();


    }
}
