package com.company;
import com.company.config.Config;
import com.company.model.Player;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class GameServer
{
    public String name;
    public int listeningPort;
    public int maxNumberOfPlayers;
    public int NumberOfPlayers;
    public DatagramSocket datagramSocket;
    //HashMap<InetAddress, Player> playersHashMap = new HashMap<InetAddress, Player>();

    public GameServer(String name, int selectedPort, int maxNumberOfPlayers) throws SocketException
    {
        this.maxNumberOfPlayers = maxNumberOfPlayers;
        this.name = name;
        this.NumberOfPlayers = 0;
        this.datagramSocket = new DatagramSocket(0);
        this.listeningPort = datagramSocket.getLocalPort();
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
    @Override
    protected void finalize(){
        datagramSocket.close();
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