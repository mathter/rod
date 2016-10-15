package biz.ostw.ee.config;

/**
 * @author mathter
 */
public class DoubleConvertor implements Convertor< Double >
{
    @Override
    public Double from( CharSequence value )
    {
        return value != null ? Double.parseDouble( value.toString() ) : null;
    }

    @Override
    public String from( Double value )
    {
        return value.toString();
    }

    @Override
    public Type< Double > getType()
    {
        return new Type< Double >( "double", Double.class );
    }
}
