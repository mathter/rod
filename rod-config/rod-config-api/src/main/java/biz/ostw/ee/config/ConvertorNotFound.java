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
        super( "Convertor for class " + clazz + " not found!" );
        this.name = clazz.toString();
    }

    public ConvertorNotFound( String name )
    {
        super( "Convertor for type '" + name + "' not found!" );
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }
}
