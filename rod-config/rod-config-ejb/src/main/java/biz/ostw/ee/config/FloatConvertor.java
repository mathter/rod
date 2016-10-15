package biz.ostw.ee.config;

/**
 * @author mathter
 */
public class FloatConvertor implements Convertor< Float >
{
    @Override
    public Float from( CharSequence value )
    {
        return value != null ? Float.parseFloat( value.toString() ) : null;
    }

    @Override
    public String from( Float value )
    {
        return value.toString();
    }

    @Override
    public Type< Float > getType()
    {
        return new Type< Float >( "float", Float.class );
    }
}
