
package pop3.server.commands;

import pop3.server.Command;
import pop3.server.CommandResult;
import pop3.server.State;
import pop3.server.User;

public class DELE extends Command
{
    public DELE(State nextState)
    {
        super("dele", nextState);
    }
    
    @Override
    public String Run(String[] parameters, CommandResult cmdResult)
    {
        cmdResult.setExecutedWell(false);
        
        if(parameters.length == 1)
        {
            if(cmdResult.getUser().messageExists(parameters[0]))
            {
                if(cmdResult.getUser().markMessage(parameters[0]))
                {
                    cmdResult.setExecutedWell(true);
                    return "+OK message " + parameters[0].trim() + " deleted";
                }
                else
                    return "-ERR message " + parameters[0].trim() + " already deleted";
            }
        }
        
        return "-ERR numéro de message invalide";
    }
}
