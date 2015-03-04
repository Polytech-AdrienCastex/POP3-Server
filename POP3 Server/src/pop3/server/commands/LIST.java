
package pop3.server.commands;

import pop3.server.Command;
import pop3.server.CommandResult;
import pop3.server.State;

public class LIST extends Command
{
    
    public LIST(State nextState)
    {
        super("list", nextState);
    }

    @Override
    public String Run(String[] parameters, CommandResult cmdResult)
    {
        if(parameters.length >= 1)
        {
            String mesNumber = parameters[0];
            if(cmdResult.getUser().messageExists(mesNumber) && !cmdResult.getUser().isMarked(mesNumber))
            {
                int mesLength = cmdResult.getUser().readMessage(parameters[0]).length();
                cmdResult.setExecutedWell(true);
                return "+OK " + mesNumber + " " + mesLength;
            }
            else
            {
                cmdResult.setExecutedWell(false);
                int nbMes = cmdResult.getUser().countMessages();
                if(nbMes > 0)
                    return "-ERR no such message, only " + nbMes + " message" + (nbMes > 1 ? "s": "") + " in maildrop";
                else
                    return "-ERR no such message, maildrop is empty";
            }
        }
        else
        {
            StringBuilder sb = new StringBuilder("+OK ");
            int nbMes = cmdResult.getUser().countMessages();
            
            if(nbMes > 0)
            {
                sb.append(nbMes + " message" + (nbMes > 1 ? "s": ""));
                sb.append(" (" + cmdResult.getUser().countMessageTotalLength() + " octets)");
            }
            else
                sb.append("0 message");

            for(int curId = 0; curId < nbMes; curId++)
            {
                String msg = cmdResult.getUser().readMessage(curId);
                if(msg != null)
                {
                    sb.append("\r\n" + curId + " " + msg.length());
                }
            }

            sb.append("\r\n.");
            cmdResult.setExecutedWell(true);
            return sb.toString();
        }
    }
}
