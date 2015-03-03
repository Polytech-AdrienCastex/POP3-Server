
package pop3.server.states;
import pop3.server.Command;
import pop3.server.State;
import pop3.server.commands.APOP;
import pop3.server.commands.QUIT;


public class AuthorizationState extends State
{
    public AuthorizationState()
    {
        Initialize(new Command[]
        {
            new APOP(new TraitementState()),
            new QUIT()
        });
    }
}
