
package pop3.server;

import java.lang.management.ManagementFactory;
import java.util.Date;

public class CommandResult
{
    public CommandResult()
    {
        executedWell = false;
        user = null;
        securityMessage = null;
        
        this.cmdResultParent = null;
    }
    public CommandResult(CommandResult cmdResultParent)
    {
        executedWell = false;
        user = cmdResultParent.user;
        securityMessage = cmdResultParent.securityMessage;
        
        this.cmdResultParent = cmdResultParent;
    }
    
    //*********** PROPERTIES
    private final CommandResult cmdResultParent;
    
    private boolean executedWell;
    private User user;
    private String userName;
    private String securityMessage;
    //**********************
    
    public String getSecurityMessage()
    {
        if(securityMessage != null)
            return securityMessage;
        
        securityMessage = ManagementFactory.getRuntimeMXBean().getName();
        securityMessage = securityMessage.substring(0, securityMessage.indexOf("@")) + "." + new Date().getTime() + "@" + securityMessage.substring(securityMessage.indexOf("@") + 1);
        return securityMessage;
    }
    
    //*********** ACCESSORS
    public boolean isExecutedWell()
    {
        return executedWell;
    }
    public void setExecutedWell(boolean executedWell)
    {
        this.executedWell = executedWell;
        if(cmdResultParent != null)
            cmdResultParent.executedWell = executedWell;
    }
    
    public User getUser()
    {
        return user;
    }
    public void setUser(User user)
    {
        this.user = user;
        if(cmdResultParent != null)
            cmdResultParent.user = user;
    }
    
    public String getUserName()
    {
        return userName;
    }
    public void setUserName(String userName)
    {
        this.userName = userName;
        if(cmdResultParent != null)
            cmdResultParent.userName = userName;
    }
    //**********************
}
