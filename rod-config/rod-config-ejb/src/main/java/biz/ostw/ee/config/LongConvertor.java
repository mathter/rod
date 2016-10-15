package biz.ostw.ee.config;

/**
 * @author mathter
 */
public class LongConvertor implements Convertor< Long >
{
    @Override
    public Long from( CharSequence value )
    {
        return value != null ? Long.decode( value.toString() ) : null;
    }

    @Override
    public CharSequence from( Long value )
    {
        return value.toString();
    }

    @Override
    public Type< Long > getType()
    {
        return new Type< Long >( "long", Long.class );
    }
}
