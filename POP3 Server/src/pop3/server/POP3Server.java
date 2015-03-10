
package pop3.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;
import pop3.server.states.AuthorizationState;

public class POP3Server
{
    public static void main(String[] args)
    {
        final int unsecuredPort = 1024;
        final int securedPort = 1025;
        
        try
        {
            // Start the unsecured server
            ServerSocket unsecuredServerSocket = new ServerSocket(unsecuredPort);
            AcceptSession uas = new AcceptSession("unsecured", unsecuredServerSocket);
            uas.run();
            
            /*
            // Start the secured server
            ServerSocket securedServerSocket = new ServerSocket(securedPort);
            AcceptSession sas = new AcceptSession("secured", securedServerSocket);
            sas.run();
            */
            
            while(true);
        }
        catch (IOException ex)
        {
            System.err.println("[ERROR] " + ex.getMessage());
        }
    }
}
