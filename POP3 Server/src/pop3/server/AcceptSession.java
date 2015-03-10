
package pop3.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import pop3.server.states.AuthorizationState;

public class AcceptSession implements Runnable
{
    public AcceptSession(String serverName, ServerSocket serverSocket)
    {
        this.serverName = serverName;
        this.serverSocket = serverSocket;
    }

    private String serverName;
    private ServerSocket serverSocket;

    @Override
    public void run()
    {
        try
        {
            List<Thread> sessions = new ArrayList<>();

            while(true)
            { // Each client connected via TCP
                Socket client = serverSocket.accept();

                Session s = new Session(client, new AuthorizationState());
                Thread t = new Thread(s); // Run the client session
                sessions.add(t);
                t.start();
            }
        }
        catch (IOException ex)
        {
            System.err.println("[ERROR] " + ex.getMessage());
        }
        
        System.err.println("[/!\\] " + this.serverName + " server stopped!");
    }
}
