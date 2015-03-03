
package pop3.server;

public class CommandResult
{
    public CommandResult()
    {
        executedWell = false;
        user = null;
        
        this.cmdResultParent = null;
    }
    public CommandResult(CommandResult cmdResultParent)
    {
        executedWell = false;
        user = cmdResultParent.user;
        
        this.cmdResultParent = cmdResultParent;
    }
    
    //*********** PROPERTIES
    private final CommandResult cmdResultParent;
    
    private boolean executedWell;
    private User user;
    //**********************
    
    
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
    //**********************
}
