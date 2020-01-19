package com.company;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

/**
 *      PORTS 
 * InformationsNetSupplier - 9000
 * 
 */
public class Main
{
    private static GameServersProducer GamesManager = new GameServersProducer();
    private static int INS_PORT = 9000;
    private static String key = "SEFCNwdjBj";

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
                    Socket socket = serverSocket.accept();

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
        {   // INITIALIZE CONNECTION
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();
            String header;
            String json;
            String[] lines;
            String keyToCheck;

            while(true)
            {
                lines = reveive(is).split("[\\r\\n]+");
                keyToCheck = lines[0];
                if(!keyToCheck.equals(Main.key) || lines.length != 3)
                {
                    break;
                }
                header = lines[1];
                json = lines[2];
                if (header.equals("CONNECT")) {
                    System.out.println("New client: " + json);
                    sendResponse(os, "OK");
                }
                else if (header.equals("GET_GAME_SERVERS")) {
                    System.out.println("New client: " + json);
                    sendResponse(os, "blabla");
                }

            }
            socket.close();
        }
        /**
         * Method reads received data from Client.
         * @param inputStream inputStream to client
         * @return received String
         * @throws IOException
         *
         * https://stackoverflow.com/questions/14824491/can-i-communicate-between-java-and-c-sharp-using-just-sockets
         * As a hopefully interesting aside,
         * Java does have an unsigned number primitive,
         * but it's 16 bit, not 8. Our old favourite char is an unsigned 16-bit number after all.
         * This is why Character.toChars(int) returns an array of chars,
         * due to the whole UTF-16 shenanigans stuff.
         */
        String reveive(InputStream inputStream) throws IOException {
            byte[] lenBytes = new byte[4];
            inputStream.read(lenBytes, 0, 4);
            int len = (((lenBytes[3] & 0xff) << 24) | ((lenBytes[2] & 0xff) << 16) |
                    ((lenBytes[1] & 0xff) << 8) | (lenBytes[0] & 0xff));
            byte[] receivedBytes = new byte[len];
            inputStream.read(receivedBytes, 0, len);
           return new String(receivedBytes, 0, len);
        }
        /**
         *  Method sends response to Client.
         * @param os
         * @param response
         * @throws IOException
         */
        void sendResponse(OutputStream os, String response) throws IOException {
            byte[] toSendBytes = response.getBytes();
            int toSendLen = toSendBytes.length;
            byte[] toSendLenBytes = new byte[4];
            toSendLenBytes[0] = (byte)(toSendLen & 0xff);
            toSendLenBytes[1] = (byte)((toSendLen >> 8) & 0xff);
            toSendLenBytes[2] = (byte)((toSendLen >> 16) & 0xff);
            toSendLenBytes[3] = (byte)((toSendLen >> 24) & 0xff);
            os.write(toSendLenBytes);
            os.write(toSendBytes);
        }
    }
    public static void main(String[] args)
    {
        InformationsNetSupplier NetSupplier;
        GameServersProducer gameServersProducer;
        try
        {
            NetSupplier = new InformationsNetSupplier(INS_PORT);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return;
        }
        Thread NetSupplierThread = new Thread(() -> NetSupplier.StartListening());
        NetSupplierThread.start();
        gameServersProducer = new GameServersProducer();
        try
        {
            gameServersProducer.addServer("Hummel",0,2);
            gameServersProducer.addServer("Marian",0,2);
            gameServersProducer.addServer("Lambert",0,2);
        }
        catch (SocketException e)
        {
            e.printStackTrace();
        }
        System.out.println(gameServersProducer.getGameServersListJson());

    }

}
