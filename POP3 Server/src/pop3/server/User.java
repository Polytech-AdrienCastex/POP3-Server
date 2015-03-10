
package pop3.server;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class User
{
    private static final String USER_FOLDER = "emails_base";
    
    public User(String userName, String password)
    {
        this.userName = userName;
        this.userFolder = getFolder(userName, password);
        
        markedMsgId = new ArrayList<>();
    }
    
    private final String userName;
    private final File userFolder;
    
    private List<Integer> markedMsgId;

    public static boolean exists(String userName)
    {
        userName = userName.toLowerCase() + "@";
        
        File f = new File(USER_FOLDER);
        for(String dir : f.list())
            if(dir.startsWith(userName))
                return true;
        return false;
    }
    
    public String getUserName()
    {
        return userName;
    }
    
    public String[] getNullMarkedMessageList()
    {
        String[] msgs = userFolder.list();
        List<String> msgsNotMarked = new ArrayList<>();
        
        for(int i = 0; i < msgs.length; i++)
            if(!isMarked(i))
                msgsNotMarked.add(msgs[i]);
            else
                msgsNotMarked.add(null);
        
        String[] returnMsgs = new String[msgsNotMarked.size()];
        msgsNotMarked.toArray(returnMsgs);
        return returnMsgs;
    }
    public String[] getNotMarkedMessageList()
    {
        String[] msgs = userFolder.list();
        List<String> msgsNotMarked = new ArrayList<>();
        
        for(int i = 0; i < msgs.length; i++)
            if(!isMarked(i))
                msgsNotMarked.add(msgs[i]);
        
        String[] returnMsgs = new String[msgsNotMarked.size()];
        msgsNotMarked.toArray(returnMsgs);
        return returnMsgs;
    }
    public String[] getMessageList()
    {
        return userFolder.list();
    }
    
    public boolean isMarked(String id)
    {
        try
        {
            return isMarked(Integer.parseInt(id));
        }
        catch (NumberFormatException ex)
        {
            return false;
        }
    }
    public boolean isMarked(int id)
    {
        return markedMsgId.contains(id);
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
        return getNotMarkedMessageList().length;
    }
    public int countMessageTotalLength()
    {
        int totalLength = 0;
        
        for(String f : getNotMarkedMessageList())
            totalLength += new File(getMessageFullPath(f)).length();
        
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
        id--;
        if(id >= 0)
        {
            try
            {
                String[] msgs = getNullMarkedMessageList();
                
                if(id < msgs.length && msgs[id] != null)
                {
                    Path p = Paths.get(getMessageFullPath(msgs[id]));
                    byte[] arr = Files.readAllBytes(p);
                    return new String(arr, "UTF-8");
                }
            }
            catch (Exception ex)
            { }
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
        id--;
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
        id--;
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
    
    private String getMessageFullPath(String messageName)
    {
        return userFolder.getPath() + "/" + messageName;
    }
    
    public static File getFolder(String userName, String password)
    {
        return new File(USER_FOLDER + "/" + userName.toLowerCase() + "@" + password);
    }
}
