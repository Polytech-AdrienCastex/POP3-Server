
package pop3.server;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.ServerSocket;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Stream;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

public class POP3Server
{
    public static void main(String[] args)
    {
        final int unsecuredPort = 1024;
        final int securedPort = 8000;
        
        try
        {
            // Start the unsecured server
            ServerSocket unsecuredServerSocket = new ServerSocket(unsecuredPort);
            AcceptSession uas = new AcceptSession("unsecured", unsecuredServerSocket);
            new Thread(uas).start();
            
            // Start the secured server
            SSLServerSocketFactory sslfactory = (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();
            
            ServerSocket securedServerSocket = sslfactory.createServerSocket(securedPort);
            
            ((SSLServerSocket)securedServerSocket).setEnabledCipherSuites(
                    Arrays.stream(sslfactory.getSupportedCipherSuites())
                            .filter(s -> s.contains("anon"))
                            .toArray(size -> new String[size])
            );
            
            AcceptSession sas = new AcceptSession("secured", securedServerSocket);
            new Thread(sas).start();
            
            while(true);
        }
        catch (IOException ex)
        {
            System.err.println("[ERROR] " + ex.getMessage());
        }
    }
}
