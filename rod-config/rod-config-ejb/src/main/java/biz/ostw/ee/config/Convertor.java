package biz.ostw.ee.config;

/**
 * @author mathter
 */
public interface Convertor< T >
{
    public T from( CharSequence value );

    public String from( T value );

    public Type< T > getType();
}
