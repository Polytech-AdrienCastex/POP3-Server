
package pop3.server.states;
import pop3.server.Command;
import pop3.server.State;
import pop3.server.commands.DELE;
import pop3.server.commands.NOOP;
import pop3.server.commands.QUIT;
import pop3.server.commands.RETR;
import pop3.server.commands.RSET;


public class TraitementState extends State
{
    public TraitementState()
    {
        Initialize(new Command[]
        {
            new QUIT(true),
            new DELE(this),
            new NOOP(this),
            new RETR(this),
            new RSET(this)
        });
    }
}
