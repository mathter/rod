package biz.ostw.ee.config;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * @author mathter
 */
@Entity
@Table( name = "config_records" )
@Access( AccessType.FIELD )
@NamedQueries(
{
    @NamedQuery( name = "ConfigRecord_getByKey", query = "select o from ConfigRecord o where o.key = :key" )
} )
@Cacheable
@Cache( usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "quick" )
public class ConfigRecord implements Serializable
{
    private static final long serialVersionUID = 9180261621146706289L;

    @Id
    @Column( name = "key", nullable = false, unique = true )
    private String key;

    @Column( name = "value", nullable = true )
    private String value;

    @JoinColumn( name = "config_type_id", nullable = false, referencedColumnName = "id" )
    @ManyToOne( fetch = FetchType.EAGER )
    @Cache( usage = CacheConcurrencyStrategy.READ_ONLY, region = "slow" )
    @Fetch( FetchMode.JOIN )
    private ConfigType configType;

    public String getKey()
    {
        return key;
    }

    public void setKey( String key )
    {
        this.key = key;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue( String value )
    {
        this.value = value;
    }

    public ConfigType getConfigType()
    {
        return configType;
    }

    public void setConfigType( ConfigType configType )
    {
        this.configType = configType;
    }

    @Override
    public int hashCode()
    {
        return this.key.hashCode();
    }

    @Override
    public boolean equals( Object obj )
    {
        return obj instanceof ConfigRecord && this.key.equals( ( (ConfigRecord) obj ).key );
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append( "ConfigRecord [key=" );
        builder.append( key );
        builder.append( ", value=" );
        builder.append( value );
        builder.append( ", configType=" );
        builder.append( configType );
        builder.append( "]" );
        return builder.toString();
    }
}
