package es.deusto.sd.authenticus.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import es.deusto.sd.authenticus.service.UserService;
public class ServidorSocket  {
    
    private static final int puerto = 5000;
    private static final String DELIMITER = "#";
    private UserService userService; //utilizamos este atributo para los mensajes de login

    
   
    public ServidorSocket() throws IOException{
        ServerSocket serverSocket=new ServerSocket(puerto);
        System.out.println("Server en el socket 5000");//abre puerto 5000 y se queda esperandoa cliente

        while (true) { // siempre activo, para recibir mas un cliente 
            Socket cliente=null;

            try {
                cliente = serverSocket.accept();//cliente se conecta
                System.out.println("Cliente conectado");

                //recibe informacion de cliente
                DataInputStream in = new DataInputStream(cliente.getInputStream());
                DataOutputStream out = new DataOutputStream(cliente.getOutputStream());

                while (true) { //siempre activo para recibir mas de un mensaje
                    String mensaje = in.readUTF();//lee el mensaje de cliente
                    System.out.println("Cliente dice: " + mensaje);

                    String[] partes = mensaje.split(DELIMITER);//mensaje del cliente
                    String tipo = partes[0];

                    if (tipo.equals("PING")) {
                        out.writeUTF("OK#PONG");

                    } 
                    else if (tipo.equals("REGISTER")) {
                        if (partes.length != 6) {
                            out.writeUTF("ERROR#Formato REGISTER incorrecto");
                        } else {
                            String nombre = partes[2];
                            String email = partes[3];
                            String password = partes[4];
                            out.writeUTF("OK#Usuario registrado: " + nombre);
                        }
                    }else if (tipo.equals("LOGIN")) {

                        if (partes.length != 3) {
                            out.writeUTF("ERROR#Formato LOGIN incorrecto");
                        } else {
                            String usuario = partes[1];
                            String password = partes[2];

                            if (usuario.equals("admin") && password.equals("admin")) {
                                out.writeUTF("OK#Login correcto");
                            } else {
                                out.writeUTF("ERROR#Credenciales incorrectas");
                            }
                        }

                    } else if (tipo.equals("LOGOUT")) {
                        out.writeUTF("OK#Logout correcto");

                    } else {
                        out.writeUTF("ERROR#Comando desconocido");
                    }
                }
            } catch (IOException e) {
                System.out.println("Cliente desconectado");
            } finally {
                cliente.close();
            }
        }
    }
}


    


/*public static void main(String[] args) {
    try {
        new ServidorSocket(); // ejecuta el constructor que inicia el servidor
    } catch (IOException e) {
        System.err.println("Error iniciando servidor: " + e.getMessage());
    }
}}*/
