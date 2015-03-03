
package pop3.server.commands;

import pop3.server.Command;
import pop3.server.CommandResult;
import pop3.server.State;
import pop3.server.User;

public class RETR extends Command
{
    public RETR(State nextState)
    {
        super("retr", nextState);
    }
    
    @Override
    public String Run(String[] parameters, CommandResult cmdResult)
    {
        if(parameters.length == 1)
        {
            String msg = cmdResult.getUser().readMessage(parameters[0]);

            if(msg != null)
            {
                cmdResult.setExecutedWell(true);
                return "+OK \r\n" + msg.length() + " octets\r\n" + msg + "\r\n.";
            }
        }
        
        cmdResult.setExecutedWell(false);
        return "-ERR ...";
    }
}
