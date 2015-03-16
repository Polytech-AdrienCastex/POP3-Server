
package pop3.server.commands;

import pop3.server.AlgoMD5;
import pop3.server.Command;
import pop3.server.CommandResult;
import pop3.server.State;
import pop3.server.User;

public class APOPs extends Command
{
    public APOPs(State nextState)
    {
        super("apop", nextState);
    }
    
    @Override
    public String Run(String[] parameters, CommandResult cmdResult)
    {
        if(parameters.length == 2)
        {
            if(User.exists(parameters[0], cmdResult.getSecurityMessage(), parameters[1]))
            {
                User user = new User(parameters[0], User.extractPassword(parameters[0], cmdResult.getSecurityMessage(), parameters[1]));

                if(user.canAccess())
                {
                    cmdResult.setUser(user);
                    cmdResult.setExecutedWell(true);

                    int nbmsg = user.countMessages();
                    if(nbmsg > 0)
                        return "+OK maildrop has " + nbmsg + " message" + (nbmsg > 1 ? "s" : "") + " (" + user.countMessageTotalLength() + " octets)";
                    else
                        return "+OK maildrop is empty";
                }
            }
        }
        
        cmdResult.setExecutedWell(false);
        return "-ERR permission non accordée";
    }
}
