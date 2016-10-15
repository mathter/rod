package biz.ostw.ee.config;

/**
 * @author mathter
 */
public class IntegerConvertor implements Convertor< Integer >
{
    @Override
    public Integer from( CharSequence value )
    {
        return value != null ? Integer.decode( value.toString() ) : null;
    }

    @Override
    public String from( Integer value )
    {
        return value.toString();
    }

    @Override
    public Type< Integer > getType()
    {
        return new Type< Integer >( "integer", Integer.class );
    }
}
