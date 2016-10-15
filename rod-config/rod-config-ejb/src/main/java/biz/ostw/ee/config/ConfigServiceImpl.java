package biz.ostw.ee.config;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import biz.ostw.persistence.jpa.AbstractJPARepository;

/**
 * @author mathter
 */
@Remote( ConfigService.class )
@Stateless
public class ConfigServiceImpl extends AbstractJPARepository implements ConfigService
{
    @PersistenceContext( name = "biz.ostw.ee.config" )
    private EntityManager em;

    @Inject
    private ConvertorService convertorService;

    @Override
    public < T > T get( Object key, Class< T > clazz, T defaultValue )
    {
        if ( clazz != null )
        {
            ConfigRecord configRecord;
            TypedQuery< ConfigRecord > query = this.em.createNamedQuery( "ConfigRecord_getByKey", ConfigRecord.class );
            query.setParameter( "key", key );

            try
            {
                configRecord = query.getSingleResult();
            } catch ( NoResultException e )
            {
                configRecord = null;
            }

            if ( configRecord != null )
            {
                Convertor< T > convertor = this.convertorService.getConvertor( clazz );
                String value = configRecord.getValue();

                return convertor.from( value );
            } else
            {
                return defaultValue;
            }
        } else
        {
            throw new NullPointerException( "Class can't be null!" );
        }
    }

    @Override
    public < T > T get( Object key, Class< T > clazz )
    {
        return this.get( key, clazz, null );
    }

    @Override
    public < T > T get( Object key )
    {
        return this.get( key, null );
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public < T > T get( Object key, T defaultValue )
    {
        if ( key != null )
        {
            ConfigRecord configRecord;
            configRecord = this.em.find( ConfigRecord.class, key );

            if ( configRecord != null )
            {
                Convertor< T > convertor = (Convertor< T >) this.convertorService.getConvertor( configRecord.getConfigType().getName() );
                String value = configRecord.getValue();

                return convertor.from( value );
            } else
            {
                return null;
            }
        } else
        {
            throw new NullPointerException( "Class can't be null!" );
        }
    }

    @Override
    public String getString( Object key )
    {
        return this.getString( key, null );
    }

    @Override
    public String getString( Object key, String defaultValue )
    {
        return this.get( key, String.class, defaultValue );
    }

    @Override
    public Byte getByte( Object key )
    {
        return this.getByte( key, null );
    }

    @Override
    public Byte getByte( Object key, Byte defaultValue )
    {
        return this.get( key, Byte.class, defaultValue );
    }

    @Override
    public Short getShort( Object key )
    {
        return this.getShort( key, null );
    }

    @Override
    public Short getShort( Object key, Short defaultValue )
    {
        return this.get( key, Short.class, defaultValue );
    }

    @Override
    public Integer getInteger( Object key )
    {
        return this.getInteger( key, null );
    }

    @Override
    public Integer getInteger( Object key, Integer defaultValue )
    {
        return this.get( key, Integer.class, defaultValue );
    }

    @Override
    public Long getLong( Object key )
    {
        return this.getLong( key, null );
    }

    @Override
    public Long getLong( Object key, Long defaultValue )
    {
        return this.get( key, Long.class, defaultValue );
    }

    @Override
    public Float getFloat( Object key )
    {
        return this.getFloat( key, null );
    }

    @Override
    public Float getFloat( Object key, Float defaultValue )
    {
        return this.get( key, Float.class, defaultValue );
    }

    @Override
    public Double getDouble( Object key )
    {
        return this.getDouble( null, null );
    }

    @Override
    public Double getDouble( Object key, Double defaultValue )
    {
        return this.get( key, Double.class, defaultValue );
    }

    @Override
    protected EntityManager getEntityManager()
    {
        return this.em;
    }
}
