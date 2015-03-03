/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pop3.server.commands;

import pop3.server.Command;
import pop3.server.State;

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
    public String Run(String[] parameters, Boolean outSuccessed)
    {
        outSuccessed = true;
        
        return "+OK !!!!!!!!!!!!!!!!!! WHAHAHAHAHHAA";
    }
}
