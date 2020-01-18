package com.company;
import com.company.config.Config;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class GameServer
{
    private String name;
    private int listeningPort;
    private int maxNumberOfPlayers;
    private int NumberOfPlayers;
    DatagramSocket datagramSocket;

    public GameServer(String name, int selectedPort, int maxNumberOfPlayers) throws SocketException {
        this.listeningPort = selectedPort;
        this.maxNumberOfPlayers = maxNumberOfPlayers;
        this.name = name;
        this.NumberOfPlayers = 0;
        datagramSocket = new DatagramSocket(listeningPort);
    }

    public void start()
    {
        byte[] byteResponse = "OK".getBytes(StandardCharsets.UTF_8);

        while (true)
        {
            DatagramPacket reclievedPacket
                    = new DatagramPacket( new byte[Config.BUFFER_SIZE], Config.BUFFER_SIZE);
            try
            {
                datagramSocket.receive(reclievedPacket);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            int length = reclievedPacket.getLength();
            String message = null;

            message = new String(reclievedPacket.getData(), 0, length, StandardCharsets.UTF_8);

            // Port i host który wysłał nam zapytanie
            InetAddress address = reclievedPacket.getAddress();
            int port = reclievedPacket.getPort();
           // System.out.flush();
            System.out.println("Datagram_in: " + message);
           // Thread.sleep(1000); //To oczekiwanie nie jest potrzebne dla
            // obsługi gniazda

           /* DatagramPacket response
                    = new DatagramPacket(
                    byteResponse, byteResponse.length, address, port);

            datagramSocket.send(response);
            */
        }
    }
    public String getName()
    {
        return name;
    }

    public int getMaxNumberOfPlayers()
    {
        return maxNumberOfPlayers;
    }
}