package biz.ostw.ee.config;

/**
 * @author mathter
 */
public class Type< T >
{
    private final String name;

    private final Class< T > clazz;

    public Type( String name, Class< T > clazz )
    {
        if ( name == null )
        {
            throw new NullPointerException( "Type name can't be null!" );
        }

        if ( clazz == null )
        {
            throw new NullPointerException( "Argument 'class' can't be null!" );
        }

        this.name = name;
        this.clazz = clazz;
    }

    public String getName()
    {
        return name;
    }

    public Class< T > getClazz()
    {
        return clazz;
    }

    @Override
    public int hashCode()
    {
        return this.name.hashCode();
    }

    @Override
    public boolean equals( Object obj )
    {
        return obj instanceof Type && ( (Type< ? >) obj ).name.equals( this.name );
    }

    @Override
    public String toString()
    {
        return this.name;
    }
}
