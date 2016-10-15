package biz.ostw.ee.config;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @author mathter
 */
@Entity
@Table( name = "config_types" )
@Access( AccessType.FIELD )
@Cacheable
@Cache( usage = CacheConcurrencyStrategy.READ_ONLY, region = "slow" )
public class ConfigType implements Serializable
{
    private static final long serialVersionUID = -5052550855152619880L;

    @Id
    @Column( name = "id" )
    @SequenceGenerator( name = "config_types_gen", sequenceName = "config_types_seq", allocationSize = 1, initialValue = 1 )
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "config_types_gen" )
    private Long id;

    @Column( name = "name", nullable = false, unique = true )
    private String name;

    public Long getId()
    {
        return id;
    }

    public void setId( Long id )
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    @Override
    public int hashCode()
    {
        return (int) ( this.id != null ? this.id : 0 );
    }

    @Override
    public boolean equals( Object obj )
    {
        return obj instanceof ConfigType && this.id == ( (ConfigType) obj ).id;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append( "ConfigType [id=" );
        builder.append( id );
        builder.append( ", name=" );
        builder.append( name );
        builder.append( "]" );
        return builder.toString();
    }
}
