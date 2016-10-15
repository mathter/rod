package biz.ostw.ee.config;

/**
 * @author mathter
 */
public class ConvertorNotFound extends RuntimeException
{
    private static final long serialVersionUID = 1250037053936162062L;

    private final String name;

    public ConvertorNotFound( Class< ? > clazz )
    {
        this.name = clazz.toString();
    }

    public ConvertorNotFound( String name )
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }
}
