
package pop3.server.commands;

import pop3.server.Command;
import pop3.server.CommandResult;
import pop3.server.State;

public class RSET extends Command
{
    public RSET(State nextState)
    {
        super("rset", nextState);
    }
    
    @Override
    public String Run(String[] parameters, CommandResult cmdResult)
    {
        cmdResult.getUser().clearMarks();
        
        cmdResult.setExecutedWell(true);
        
        int nbmsg = cmdResult.getUser().countMessages();
        if(nbmsg > 0)
            return "+OK maildrop has " + nbmsg + " message" + (nbmsg > 1 ? "s" : "") + " (" + cmdResult.getUser().countMessageTotalLength() + " octets)";
        else
            return "+OK maildrop is empty";
    }
}
