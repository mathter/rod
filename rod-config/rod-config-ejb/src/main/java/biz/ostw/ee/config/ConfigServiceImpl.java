package biz.ostw.ee.config;

import java.util.Optional;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

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
    @Transactional( value = TxType.SUPPORTS )
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
    @Transactional( value = TxType.SUPPORTS )
    public < T > T get( Object key, Class< T > clazz )
    {
        return this.get( key, clazz, null );
    }

    @Override
    @Transactional( value = TxType.SUPPORTS )
    public < T > T get( Object key )
    {
        return this.get( key, null );
    }

    @SuppressWarnings( "unchecked" )
    @Override
    @Transactional( value = TxType.SUPPORTS )
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
    @Transactional( value = TxType.REQUIRED )
    public < T > void put( Object key, T value ) throws NullPointerException, IllegalArgumentException
    {
        this.put( key, value, value != null ? value.getClass() : null );
    }

    @SuppressWarnings( "unchecked" )
    @Override
    @Transactional( value = TxType.REQUIRED )
    public < T > void put( Object key, T value, Class< ? extends T > clazz ) throws NullPointerException, IllegalArgumentException
    {
        if ( key == null )
        {
            throw new NullPointerException( "key can't be null!" );
        }

        ConfigRecord configRecord;
        Convertor< T > convertor = null;

        if ( clazz != null )
        {
            convertor = (Convertor< T >) this.convertorService.getConvertor( clazz );
        }

        if ( convertor == null )
        {
            if ( value != null )
            {
                convertor = (Convertor< T >) this.convertorService.getConvertor( value.getClass() );
            } else
            {
                configRecord = this.em.find( ConfigRecord.class, key );
                convertor = (Convertor< T >) this.convertorService.getConvertor( configRecord.getConfigType().getName() );
            }
        }

        TypedQuery< ConfigType > query = this.em.createNamedQuery( "ConfigType_getByName", ConfigType.class );

        query.setParameter( "name", convertor.getType().getName() );
        Optional< ConfigType > optional = query.getResultList().stream().findFirst();

        if ( optional.isPresent() )
        {

            configRecord = new ConfigRecord();
            configRecord.setKey( key.toString() );
            configRecord.setValue( convertor.from( value ) );
            configRecord.setConfigType( optional.get() );

            this.em.merge( configRecord );
        } else
        {
            throw new ConvertorNotFound( convertor.getType().getName() );
        }
    }

    @Override
    @Transactional( value = TxType.SUPPORTS )
    public String getString( Object key )
    {
        return this.getString( key, null );
    }

    @Override
    @Transactional( value = TxType.SUPPORTS )
    public String getString( Object key, String defaultValue )
    {
        return this.get( key, String.class, defaultValue );
    }

    @Override
    @Transactional( value = TxType.SUPPORTS )
    public Byte getByte( Object key )
    {
        return this.getByte( key, null );
    }

    @Override
    @Transactional( value = TxType.SUPPORTS )
    public Byte getByte( Object key, Byte defaultValue )
    {
        return this.get( key, Byte.class, defaultValue );
    }

    @Override
    @Transactional( value = TxType.SUPPORTS )
    public Short getShort( Object key )
    {
        return this.getShort( key, null );
    }

    @Override
    @Transactional( value = TxType.SUPPORTS )
    public Short getShort( Object key, Short defaultValue )
    {
        return this.get( key, Short.class, defaultValue );
    }

    @Override
    @Transactional( value = TxType.SUPPORTS )
    public Integer getInteger( Object key )
    {
        return this.getInteger( key, null );
    }

    @Override
    @Transactional( value = TxType.SUPPORTS )
    public Integer getInteger( Object key, Integer defaultValue )
    {
        return this.get( key, Integer.class, defaultValue );
    }

    @Override
    @Transactional( value = TxType.SUPPORTS )
    public Long getLong( Object key )
    {
        return this.getLong( key, null );
    }

    @Override
    @Transactional( value = TxType.SUPPORTS )
    public Long getLong( Object key, Long defaultValue )
    {
        return this.get( key, Long.class, defaultValue );
    }

    @Override
    @Transactional( value = TxType.SUPPORTS )
    public Float getFloat( Object key )
    {
        return this.getFloat( key, null );
    }

    @Override
    @Transactional( value = TxType.SUPPORTS )
    public Float getFloat( Object key, Float defaultValue )
    {
        return this.get( key, Float.class, defaultValue );
    }

    @Override
    @Transactional( value = TxType.SUPPORTS )
    public Double getDouble( Object key )
    {
        return this.getDouble( null, null );
    }

    @Override
    @Transactional( value = TxType.SUPPORTS )
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
