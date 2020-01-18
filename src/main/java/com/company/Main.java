package com.company;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *      PORTS 
 * InformationsNetSupplier - 9000
 * 
 */
public class Main
{
    private static GameServersProducer GamesManager = new GameServersProducer();
    private static int INS_PORT = 9000;

    public static class InformationsNetSupplier
    {
        private int PORT;
        private ServerSocket serverSocket;
        /**
         * Constructor
         *
         * @param port numer portu Servera Informacyjnego
         */
        public InformationsNetSupplier(int port) throws IOException {
            this.PORT = port;
            serverSocket = new ServerSocket(PORT);
        }

        public void StartListening()
        {
            try
            {
                while (true)
                {
                    System.out.println("Czekam na klienta");
                    Socket socket = serverSocket.accept();

                    System.out.println("New Client");

                    new Thread(()->{
                        try
                        {
                            handleUser(socket);
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }).start();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            finally
            {
                try
                {
                    serverSocket.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        public void handleUser(Socket socket) throws IOException
        {
            String clientUserName;
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            OutputStream output = socket.getOutputStream();
            clientUserName = input.readLine();
            System.out.println(clientUserName);
        }
    }

    public static void main(String[] args)
    {
        InformationsNetSupplier NetSupplier;
        try
        {
            NetSupplier = new InformationsNetSupplier(INS_PORT);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return;
        }
        Thread NetSupplierThread = new Thread(NetSupplier::StartListening);
        NetSupplierThread.start();

    }

}
