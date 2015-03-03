
package pop3.server.states;
import pop3.server.Command;
import pop3.server.State;
import pop3.server.commands.APOP;


public class AuthorizationState extends State
{
    public AuthorizationState()
    {
        super(new Command[]
        {
            new APOP(new TraitementState())
        });
    }
}
