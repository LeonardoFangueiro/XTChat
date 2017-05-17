
import java.io.*;
import java.net.*;
import java.util.HashSet;
import java.util.Scanner;

public class Server {
    public Scanner input;
    private static final int PORT = 6666;
    private static HashSet<String> names = new HashSet<>();
    private static HashSet<PrintWriter> writers = new HashSet<>();
    
    public static void main(String[] args) throws Exception {
        System.out.println("XTChatServer 1.0 Iniciado!");
        System.out.println("Created by Leonardo Fangueiro!");
        System.out.println("------------------------------");
        System.out.println("Waiting for Clients!");
        ServerSocket listener = new ServerSocket(PORT);
        try {
            while (true) {
                new Handler(listener.accept()).start();
            }
        } finally {
            System.out.println("Houve Disconnect");
            listener.close();
        }
    }
    
    private static class Handler extends Thread {
        private String name;
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {

                // Cria Input e Output do socket
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // Escolha de username
                out.println("Conectado com Sucesso!");
                out.println("Introduza o seu Nickname :");
                this.name = this.in.readLine();
                out.println("Bem-vindo " + this.name);
                System.out.println(this.name + " ligou-se ao Servidor!" );
                writers.stream().forEach((PrintWriter writer) -> {
                    writer.println(this.name + " ligou-se ao Servidor!" );
                });
                
                writers.add(out);

                // Aceita mensagems dos users e faz broadcast para os Writers
                while (true) {
                    String input = this.in.readLine();
                    if (input == null) return;
                    System.out.println("<"+ this.name + "> " + input );
                    writers.stream().forEach((writer) -> {
                        writer.println("<"+ this.name + "> " + input );
                    });
                }
            } 
            catch (IOException e) { System.out.println(e); }
            finally {
                // Este cliente(Thread) está a finalizar, remove o usuario da lsita de nicks e de writers e fecha o socket
                if (this.name != null) names.remove(this.name);
                if (out != null) writers.remove(out);
                writers.stream().forEach((writer) -> {
                    writer.println(this.name + " desconectou-se do servidor!\n" );
                });
                try { socket.close(); }
                catch (IOException e) { System.out.println(e); }
            }
        }
    }
}
