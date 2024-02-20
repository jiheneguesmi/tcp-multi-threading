import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TCPClient {
    public static void main(String[] args) {
        int port = 9000;
        String host = "localhost"; // Adresse IP ou nom d'hôte du serveur
        
        try (Socket socket = new Socket(host, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.println("Connecté au serveur.");

            // Envoi de la chaîne au serveur
            out.println("bonjour");

            // Lecture de la réponse du serveur
            String reversedString = in.readLine();
            System.out.println("Chaine inversée reçue du serveur : " + reversedString);

        } catch (IOException e) {
            System.err.println("Erreur : " + e);
        }
    }
}
