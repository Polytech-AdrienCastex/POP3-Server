
package pop3.server;

import java.io.File;

public class User
{
    public User(String userName, File userFolder)
    {
        this.userName = userName;
        this.userFolder = userFolder;
    }
    
    private final String userName;
    private final File userFolder;

    public String getUserName()
    {
        return userName;
    }
    
    public String[] getEMails()
    {
        return userFolder.list();
    }
    
    public static File getFolder(String userName, String password)
    {
        return new File(userName.toLowerCase() + ".pop3.account." + password);
    }
}
