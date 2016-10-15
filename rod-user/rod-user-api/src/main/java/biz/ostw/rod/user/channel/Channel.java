package biz.ostw.rod.user.channel;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import biz.ostw.persistence.SystemId;
import biz.ostw.rod.user.User;

/**
 * @author mathter
 */
@Entity
@Table( name = "user_channels", indexes =
{
    @Index( unique = true, columnList = "user_id, user_channel_type_id" )
} )
@Access( AccessType.FIELD )
@NamedQueries(
{
    @NamedQuery( name = "Channel_getByUserAndChannelType",
        query = "select c from Channel c where c.user = :user and c.channelType = :channelType" ),
    @NamedQuery( name = "Channel_getByUser", query = "select c from Channel c where c.user = :user" ), @NamedQuery(
        name = "Channel_deleteByUserAndChannelType", query = "delete from Channel c where c.user = :user and c.channelType = :channelType" )
} )
public class Channel implements SystemId< Long >, Serializable
{
    private static final long serialVersionUID = -1136445027433051656L;

    @Id
    @Column( name = "id" )
    @SequenceGenerator( name = "user_channels_gen", sequenceName = "user_channels_seq", initialValue = 1, allocationSize = 1 )
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "user_channels_gen" )
    private long systemId;

    @ManyToOne( fetch = FetchType.EAGER )
    @JoinColumn( name = "user_channel_type_id", nullable = false )
    @Fetch( FetchMode.JOIN )
    private ChannelType channelType;

    @OneToOne( fetch = FetchType.EAGER )
    @JoinColumn( name = "user_id", nullable = false )
    @Fetch( FetchMode.JOIN )
    private User user;

    @Column( name = "value", length = 64, nullable = false )
    private String value;

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

    public ChannelType getChannelType()
    {
        return this.channelType;
    }

    public void setChannelType( ChannelType channelType )
    {
        this.channelType = channelType;
    }

    public String getValue()
    {
        return this.value;
    }

    public void setValue( String value )
    {
        this.value = value;
    }

    public User getUser()
    {
        return this.user;
    }

    public void setUser( User user )
    {
        this.user = user;
    }

    @Override
    public int hashCode()
    {
        return (int) this.systemId;
    }

    @Override
    public boolean equals( Object obj )
    {
        return this == obj || ( obj instanceof Channel && this.systemId == ( (Channel) obj ).getSystemId() );
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append( "Channel [systemId=" );
        builder.append( systemId );
        builder.append( ", channelType=" );
        builder.append( channelType );
        builder.append( ", user=" );
        builder.append( user );
        builder.append( ", value=" );
        builder.append( value );
        builder.append( "]" );
        return builder.toString();
    }
}
