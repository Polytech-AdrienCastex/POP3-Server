/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pop3.server.commands;

import pop3.server.Command;
import pop3.server.CommandResult;
import pop3.server.State;
import pop3.server.User;

/**
 *
 * @author p1309208
 */
public class PASS extends Command
{
    public PASS(State nextState)
    {
        super("pass", nextState);
    }
    
    @Override
    public String Run(String[] parameters, CommandResult cmdResult)
    {
        if(parameters.length == 1)
        {
            String passwordUser = parameters[0];
            String pseudoName = cmdResult.getUserName();
            if(pseudoName != null)
            {
                User user = new User(pseudoName, passwordUser);
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
                cmdResult.setExecutedWell(false);
                return "-ERR maildrop already locked";
            }            
            cmdResult.setExecutedWell(false);
            return "-ERR user name is not known yet";
        }
        
        cmdResult.setExecutedWell(false);
        return "-ERR invalid parameter";
    }
}
