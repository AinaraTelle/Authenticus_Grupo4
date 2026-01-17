package es.deusto.sd.authenticus.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

public class ClienteSocketProcesamiento {

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public ClienteSocketProcesamiento(String serverIP, int serverPort) throws IOException {
        socket = new Socket(serverIP, serverPort);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        System.out.println("Conectado al servidor de procesamiento: " + serverIP + ":" + serverPort);
    }

    public String enviarArchivo(File archivo, String tipoAnalisis) throws IOException {
        String mensaje = tipoAnalisis + "#" + archivo.getName();
        out.writeUTF(mensaje);

        // enviar bytes
        byte[] buffer = new byte[(int) archivo.length()];
        try (FileInputStream fis = new FileInputStream(archivo)) {
            fis.read(buffer);
        }
        out.writeLong(buffer.length);
        out.write(buffer);

        // leer resultado
        String resultado = in.readUTF();
        System.out.println("Resultado recibido: " + resultado);
        return resultado;
    }

    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
    }
}
