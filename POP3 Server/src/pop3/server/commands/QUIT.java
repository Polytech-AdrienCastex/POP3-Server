
package pop3.server.commands;

import pop3.server.Command;
import pop3.server.CommandResult;
import pop3.server.State;

public class QUIT extends Command
{
    public QUIT(State nextState)
    {
        super("quit", nextState);
    }
    
    @Override
    public String Run(String[] parameters, CommandResult cmdResult)
    {
        cmdResult.setExecutedWell(true);
        cmdResult.setExit(true);
        
        return "+OK !!!!!!!!!!!!!!!!!! WHAHAHAHAHHAA";
    }
}
