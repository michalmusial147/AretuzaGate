package com.company;
import com.company.config.Config;
import com.company.model.Player;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Main {

    public static void main(String[] args) throws Exception{

        //Otwarcie gniazda z okreslonym portem
        DatagramSocket datagramSocket = new DatagramSocket(Config.PORT);

        byte[] byteResponse = "OK".getBytes("utf8");

        while (true){

            DatagramPacket reclievedPacket
                    = new DatagramPacket( new byte[Config.BUFFER_SIZE], Config.BUFFER_SIZE);

            datagramSocket.receive(reclievedPacket);

            int length = reclievedPacket.getLength();
            String message =
                    new String(reclievedPacket.getData(), 0, length, "utf8");

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
}