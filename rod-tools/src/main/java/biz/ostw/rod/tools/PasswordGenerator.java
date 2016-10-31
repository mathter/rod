package biz.ostw.rod.tools;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

public final class PasswordGenerator
{
    private static final Charset CHARSET = Charset.forName( "UTF-8" );

    public static String generate( String password ) throws NoSuchAlgorithmException
    {
        MessageDigest md = MessageDigest.getInstance( "MD5" );
        byte[] bytes = password.getBytes( CHARSET );
        bytes = md.digest( bytes );

        return Hex.encodeHexString( bytes ).toLowerCase();
    }

    private PasswordGenerator()
    {
    }

    public static void main( String[] args ) throws NoSuchAlgorithmException
    {
        System.out.println( PasswordGenerator.generate( "12345" ) );
    }
}
