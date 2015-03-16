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
import java.lang.management.ManagementFactory;
import java.net.Socket;
import java.security.Timestamp;
import java.util.Date;

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
        
        sessionID = s_sessionID++;
    }
    
    private final int sessionID;
    private static int s_sessionID = 1;
    
    private final Socket socket;
    
    private final BufferedReader ibs;
    private final BufferedWriter obs;
    
    private State currentState;
    
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
    
    @Override
    public void run()
    {
        char[] buffer = new char[2500];
        CommandResult sessionResult = new CommandResult();
        
        try
        {
            obs.write("+OK server ready <" + sessionResult.getSecurityMessage() + ">\r\n");
            obs.flush();
            System.out.println("[" + sessionID + "] Session started");
            
            while(true)
            {
                // Receive a string
                String str = "";
                do
                {
                    int len = ibs.read(buffer);
                    if(len <= 0) // Client closed
                        break;
                    str += String.valueOf(buffer, 0, len);
                } while (!str.endsWith("\r\n"));

                // Clear the start and the end of the received string
                int nb = 0;
                while(str.startsWith(" ", nb))
                    nb++;
                str = str.substring(nb, str.length() - 2 - nb);
                System.out.println("[" + sessionID + ":INPUT] \"" + str + "\"");

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

                String msg = currentState.Run(cmd, parameters, sessionResult);
                System.out.println("[MSG] \"" + msg + "\"");
                obs.write(msg + "\r\n");
                obs.flush();
                currentState = currentState.getNewState();
                
                if(currentState == null)
                    break; // Exit the session
            }
        }
        catch (IOException ex)
        { // Timeout
            System.out.println("[" + sessionID + "] Timeout");
        }
        
        System.out.println("[" + sessionID + "] Closing");
        close();
    }
}
