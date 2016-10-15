package biz.ostw.ee.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 * @author mathter
 */
@Named
@ApplicationScoped
class ConvertorService
{
    public static Type< Object > UNKNOWN = new Type< Object >( "unknown", Object.class );

    private final Map< Class< ? >, Convertor< ? > > convertorsByClass;

    private final Map< String, Convertor< ? > > convertorsByName;

    public Convertor< ? > getConvertor( String name ) throws NullPointerException, ConvertorNotFound
    {
        if ( name == null )
        {
            throw new NullPointerException( "name can't be null!" );
        }

        Convertor< ? > convertor;

        if ( ( convertor = this.convertorsByName.get( name ) ) != null )
        {
            return convertor;
        } else
        {
            throw new ConvertorNotFound( name );
        }
    }

    @SuppressWarnings( "unchecked" )
    public < T > Convertor< T > getConvertor( Class< T > clazz ) throws NullPointerException, ConvertorNotFound
    {
        if ( clazz == null )
        {
            throw new NullPointerException( "Class can't be null!" );
        }

        Convertor< ? > convertor;

        if ( ( convertor = this.convertorsByClass.get( clazz ) ) != null )
        {
            return (Convertor< T >) convertor;
        } else
        {
            throw new ConvertorNotFound( clazz );
        }
    }

    {
        Map< Class< ? >, Convertor< ? > > convertorsByClass = new HashMap<>();
        Map< String, Convertor< ? > > convertorsByName = new HashMap<>();

        @SuppressWarnings( "rawtypes" )
        ServiceLoader< Convertor > serviceLoader = ServiceLoader.load( Convertor.class );

        serviceLoader.forEach( c -> {
            convertorsByClass.put( c.getType().getClazz(), c );
            convertorsByName.put( c.getType().getName(), c );
        } );

        this.convertorsByClass = Collections.unmodifiableMap( convertorsByClass );
        this.convertorsByName = Collections.unmodifiableMap( convertorsByName );
    }
}
