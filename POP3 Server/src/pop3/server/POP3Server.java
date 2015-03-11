
package pop3.server;

import java.io.IOException;
import java.net.ServerSocket;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

public class POP3Server
{
    public static void main(String[] args)
    {
        final int unsecuredPort = 1024;
        final int securedPort = 1024;
        
        try
        {/*
            // Start the unsecured server
            ServerSocket unsecuredServerSocket = new ServerSocket(unsecuredPort);
            AcceptSession uas = new AcceptSession("unsecured", unsecuredServerSocket);
            uas.run();
            */
            // Start the secured server
            System.setProperty("javax.net.ssl.trustStore", "myTrustStore");
            System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
            System.setProperty("javax.net.ssl.keyStoreType", "pkcs12");
            System.setProperty("javax.net.ssl.keyStore", "new.p12");
            System.setProperty("javax.net.ssl.keyStorePassword", "newpasswd");
            SSLServerSocketFactory sslfactory = (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();
            ServerSocket securedServerSocket = sslfactory.createServerSocket(securedPort);
            AcceptSession sas = new AcceptSession("secured", securedServerSocket);
            sas.run();
            
            
            while(true);
        }
        catch (IOException ex)
        {
            System.err.println("[ERROR] " + ex.getMessage());
        }
    }
}
