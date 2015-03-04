
package pop3.server.commands;

import pop3.server.Command;
import pop3.server.CommandResult;
import pop3.server.User;

public class QUIT extends Command
{
    public QUIT(boolean cleanMessages)
    {
        super("quit", null);
        
        this.cleanMessages = cleanMessages;
    }
    public QUIT()
    {
        super("quit", null);
        
        this.cleanMessages = false;
    }
    
    private boolean cleanMessages;
    
    @Override
    public String Run(String[] parameters, CommandResult cmdResult)
    {
        if(cleanMessages)
        {
            User user = cmdResult.getUser();
            if(user != null)
                user.cleanMessages();
        }
            
        cmdResult.setExecutedWell(true);
        
        return "+OK";
    }
}
