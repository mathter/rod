package biz.ostw.rod.user.channel;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import biz.ostw.persistence.SystemId;

/**
 * @author mathter
 */
@Entity
@Table( name = "user_channel_types" )
@Access( AccessType.FIELD )
@NamedQueries(
{
    @NamedQuery( name = "ChannelType_getAll", query = "select ct from ChannelType ct order by ct.name asc" ),
    @NamedQuery( name = "ChannelType_getByName", query = "select ct from ChannelType ct where ct.name = :name" )
} )
public class ChannelType implements SystemId< Long >, Serializable, biz.ostw.rod.connecting.ChannelType
{
    private static final long serialVersionUID = 3054636380231718197L;

    @Id
    @Column( name = "id" )
    @SequenceGenerator( name = "user_channel_types_gen", sequenceName = "user_channel_types_seq", initialValue = 1, allocationSize = 1 )
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "user_channel_types_gen" )
    private long systemId;

    @Column( name = "name", length = 64, nullable = false )
    private String name;

    @Override
    public Long getSystemId()
    {
        return this.systemId;
    }

    @Override
    public void setSystemId( Long id )
    {
        this.systemId = id;
    }

    @Override
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
        return (int) this.systemId;
    }

    @Override
    public boolean equals( Object obj )
    {
        return this == obj || ( obj instanceof ChannelType && this.systemId == ( (ChannelType) obj ).getSystemId() );
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append( "ChannelType [systemId=" );
        builder.append( systemId );
        builder.append( ", name=" );
        builder.append( name );
        builder.append( "]" );
        return builder.toString();
    }
}
