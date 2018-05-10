package com.mygdx.game.ClientServer;

public class TestClient
{
    public static void main(String[] args)
    {
        Client client = new Client();
        Client client2 = new Client();
        client.messaggio("SUCA MARCO MARI");
        client2.ricevi();
    }
}
