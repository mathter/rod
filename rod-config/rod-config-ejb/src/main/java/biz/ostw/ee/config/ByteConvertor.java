package biz.ostw.ee.config;

/**
 * @author mathter
 */
public class ByteConvertor implements Convertor< Byte >
{
    @Override
    public Byte from( CharSequence value )
    {
        return value != null ? Byte.decode( value.toString() ) : null;
    }

    @Override
    public String from( Byte value )
    {
        return value.toString();
    }

    @Override
    public Type< Byte > getType()
    {
        return new Type< Byte >( "byte", Byte.class );
    }
}
