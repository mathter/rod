package biz.ostw.ee.config;

/**
 * @author mathter
 */
public class StringConvertor implements Convertor< String >
{
    @Override
    public String from( CharSequence value )
    {
        return value != null ? value.toString() : null;
    }

    @Override
    public String from( String value )
    {
        return value;
    }

    @Override
    public Type< String > getType()
    {
        return new Type< String >( "string", String.class );
    }
}
