/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pop3.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.CharBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author p1002239
 */
public class Session implements Runnable
{
    public Session(Socket socket, State startState) throws IOException
    {
        this.socket = socket;
        socket.setSoTimeout(10000);
        
        ibs = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        obs = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        
        currentState = startState;
    }
    
    private Socket socket;
    
    private BufferedReader ibs;
    private BufferedWriter obs;
    
    public void close()
    {
        try
        {
            if(!socket.isClosed())
                socket.close();
        }
        catch (IOException ex)
        { }
    }
    
    private State currentState;
    
    @Override
    public void run()
    {
        char[] buffer = new char[2500];
        CommandResult sessionResult = new CommandResult();
        
        try
        {
            obs.write("+OK server ready\r\n");
            obs.flush();
            System.out.println("Session started");
            
            while(true)
            {
                // Receive a string
                String str = "";
                do
                {
                    int len = ibs.read(buffer);
                    System.out.println(len);
                    str += String.valueOf(buffer, 0, len);
                } while (!str.endsWith("\r\n"));

                // Clear the begining of the received string
                int nb = 0;
                while(str.startsWith(" "))
                    nb++;
                str = str.substring(nb);

                // Split the string
                int spaceIndex = str.indexOf(" ");
                String cmd;
                String[] parameters;
                if(spaceIndex == -1)
                { // No space
                    cmd = str;
                    parameters = new String[0];
                }
                else
                { // Space found
                    cmd = str.substring(0, spaceIndex);
                    parameters = str.substring(spaceIndex + 1).split(" ");
                }

                obs.write(currentState.Run(cmd, parameters, sessionResult) + "\r\n");
                obs.flush();
                currentState = currentState.getNewState();
                
                if(sessionResult.isExit())
                    break; // Exit the session
            }
        }
        catch (IOException ex)
        { // Timeout
        }
        
        System.out.println("Closing");
        close();
    }
}
