
package pop3.server.states;
import pop3.server.Command;
import pop3.server.State;
import pop3.server.commands.APOP;
import pop3.server.commands.APOPs;
import pop3.server.commands.PASS;
import pop3.server.commands.QUIT;
import pop3.server.commands.USER;


public class AuthorizationState extends State
{
    public AuthorizationState()
    {
        TraitementState next = new TraitementState();
        
        Initialize(new Command[]
        {
            new APOPs(next),
            new PASS(next),
            new USER(this),
            new QUIT()
        });
    }
}
