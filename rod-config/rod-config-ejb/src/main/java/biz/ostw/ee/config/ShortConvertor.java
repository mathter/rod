package biz.ostw.ee.config;

/**
 * @author mathter
 */
public class ShortConvertor implements Convertor< Short >
{
    @Override
    public Short from( CharSequence value )
    {
        return value != null ? Short.decode( value.toString() ) : null;
    }

    @Override
    public CharSequence from( Short value )
    {
        return value.toString();
    }

    @Override
    public Type< Short > getType()
    {
        return new Type< Short >( "short", Short.class );
    }
}
