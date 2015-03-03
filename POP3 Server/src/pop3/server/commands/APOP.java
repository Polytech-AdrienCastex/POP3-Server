/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pop3.server.commands;

import java.io.File;
import pop3.server.Command;
import pop3.server.CommandResult;
import pop3.server.State;
import pop3.server.User;

/**
 *
 * @author p1002239
 */
public class APOP extends Command
{
    public APOP(State nextState)
    {
        super("apop", nextState);
    }
    
    @Override
    public String Run(String[] parameters, CommandResult cmdResult)
    {
        if(parameters.length == 2)
        {
            File userDirectory = User.getFolder(parameters[0], parameters[1]);
            
            if(userDirectory.exists() && userDirectory.canRead())
            {
                cmdResult.setUser(new User(parameters[0], userDirectory));
                cmdResult.setExecutedWell(true);
                
                return "+OK welcome";
            }
        }
        
        cmdResult.setExecutedWell(false);
        return "-ERR ...";
    }
}
