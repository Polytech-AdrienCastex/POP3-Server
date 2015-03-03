

package pop3.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import pop3.server.states.AuthorizationState;

/**
 *
 * @author p1002239
 */
public class POP3Server
{
    public static void main(String[] args)
    {
        final int port = 1024;
        
        try
        {
            List<Thread> sessions = new ArrayList<>();
        
            // Start the server
            ServerSocket ss = new ServerSocket(port);
            while(true)
            { // Each client connected via TCP
                Socket client = ss.accept();

                Session s = new Session(client, new AuthorizationState());
                Thread t = new Thread(s); // Run the client session
                sessions.add(t);
                t.start();
            }
        }
        catch (IOException ex)
        {
            System.err.println("[ERROR] Server port " + port + " refused");
        }
    }
    
}
