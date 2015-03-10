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
public class USER extends Command
{
    public USER(State nextState)
    {
        super("user", nextState);
    }
    
    @Override
    public String Run(String[] parameters, CommandResult cmdResult)
    {
        if(parameters.length == 1)
        {
            String pseudoUser = parameters[0];
            if(User.exists(pseudoUser))
            {
                cmdResult.setUserName(pseudoUser);
                cmdResult.setExecutedWell(true);
                return "+OK " + pseudoUser + " is a real hoopy frood";
            }
            
            cmdResult.setExecutedWell(false);
            return "-ERR sorry, no mailbox for " + pseudoUser + " here" ;
        }
        
        cmdResult.setExecutedWell(false);
        return "-ERR please indicate your user name";
    }
}
