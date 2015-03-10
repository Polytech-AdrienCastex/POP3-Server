
package pop3.server;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AlgoMD5
{
    public static String encode(String password)
    {
        byte[] uniqueKey = password.getBytes();
        byte[] hash = null;

        try
        {
            hash = MessageDigest.getInstance("MD5").digest(uniqueKey);
        }
        catch (NoSuchAlgorithmException ex)
        {
            System.err.println("No MD5 supported : " + ex.getMessage());
            return null;
        }

        StringBuilder hashString = new StringBuilder();
        for (int i = 0; i < hash.length; i++)
        {
            String hex = Integer.toHexString(hash[i]);
            if (hex.length() == 1)
            {
                hashString.append('0');
                hashString.append(hex.charAt(hex.length() - 1));
            }
            else
                hashString.append(hex.substring(hex.length() - 2));
        }
        return hashString.toString();
    }
}
