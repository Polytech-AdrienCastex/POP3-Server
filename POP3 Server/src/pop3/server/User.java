
package pop3.server;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class User
{
    public User(String userName, String password)
    {
        this.userName = userName;
        this.userFolder = getFolder(userName, password);
        
        markedMsgId = new ArrayList<>();
    }
    
    private final String userName;
    private final File userFolder;
    
    private List<Integer> markedMsgId;

    public String getUserName()
    {
        return userName;
    }
    
    public String[] getMessageList()
    {
        return userFolder.list();
    }
    
    public boolean exists()
    {
        return userFolder.exists();
    }
    
    public boolean canAccess()
    {
        return exists() && userFolder.canRead();
    }
    
    public void create()
    {
        if(!exists())
            userFolder.mkdir();
    }
    
    public int countMessages()
    {
        return getMessageList().length;
    }
    public int countMessageTotalLength()
    {
        int totalLength = 0;
        
        for(String f : getMessageList())
            totalLength += new File(f).length();
        
        return totalLength;
    }
    
    public String readMessage(String id)
    {
        try
        {
            return readMessage(Integer.parseInt(id));
        }
        catch (NumberFormatException ex)
        {
            return null;
        }
    }
    public String readMessage(int id)
    {
        if(id >= 0)
        {
            try
            {
                String[] msgs = getMessageList();
                
                if(id < msgs.length)
                    return new String(Files.readAllBytes(Paths.get(msgs[id])));
            }
            catch (Exception ex)
            {
                return null;
            }
        }
        
        return null;
    }
    
    public void cleanMessages()
    {
        String[] msgs = getMessageList();
        for(int id : markedMsgId)
            new File(msgs[id]).delete();
    }
    
    public boolean messageExists(String id)
    {
        try
        {
            return messageExists(Integer.parseInt(id));
        }
        catch (NumberFormatException ex)
        {
            return false;
        }
    }
    public boolean messageExists(int id)
    {
        return 0 <= id && id < getMessageList().length;
    }
    
    public boolean markMessage(String id)
    {
        try
        {
            return markMessage(Integer.parseInt(id));
        }
        catch (NumberFormatException ex)
        {
            return false;
        }
    }
    public boolean markMessage(int id)
    {
        if(messageExists(id) && !markedMsgId.contains(id))
        {
            markedMsgId.add(id);
            return true;
        }
        else
            return false;
    }
    
    public void clearMarks()
    {
        markedMsgId.clear();
    }
    
    public static File getFolder(String userName, String password)
    {
        return new File("emails_base/" + userName.toLowerCase() + ".at." + password);
    }
}
