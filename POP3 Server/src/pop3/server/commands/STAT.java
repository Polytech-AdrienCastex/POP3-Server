
package pop3.server.commands;

import pop3.server.Command;
import pop3.server.CommandResult;
import pop3.server.State;

public class STAT extends Command
{
    public STAT(State nextState)
    {
        super("stat", nextState);
    }
    
    @Override
    public String Run(String[] parameters, CommandResult cmdResult)
    {
        cmdResult.setExecutedWell(true);
        return "+OK " + cmdResult.getUser().countMessages() + " " + cmdResult.getUser().countMessageTotalLength();
    }
}
