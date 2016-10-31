package biz.ostw.rod.user.channel;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import biz.ostw.persistence.jpa.AbstractJPARepository;
import biz.ostw.rod.user.User;
import biz.ostw.rod.user.channel.Channel;
import biz.ostw.rod.user.channel.ChannelService;
import biz.ostw.rod.user.channel.ChannelType;

/**
 * @author mathter
 */
@Remote( ChannelService.class )
@Stateless
public class ChannelServiceImpl extends AbstractJPARepository implements ChannelService
{
    @PersistenceContext( name = "biz.ostw.rod.user" )
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return this.em;
    }

    @Override
    public List< ChannelType > geChannelTypes()
    {
        TypedQuery< ChannelType > query = this.em.createNamedQuery( "ChannelType_getAll", ChannelType.class );

        return query.getResultList();
    }

    @Override
    public ChannelType getChannelType( String name )
    {
        TypedQuery< ChannelType > query = this.em.createNamedQuery( "ChannelType_getByName", ChannelType.class );

        query.setParameter( "name", name );

        try
        {
            return query.getSingleResult();
        } catch ( NoResultException e )
        {
            return null;
        }
    }

    @Override
    public Channel createChannel( User user, ChannelType channelType )
    {
        Channel channel;

        TypedQuery< Channel > query = this.em.createNamedQuery( "Channel_getByUserAndChannelType", Channel.class );

        query.setParameter( "user", user );
        query.setParameter( "channelType", channelType );

        try
        {
            channel = query.getSingleResult();
        } catch ( NoResultException e )
        {
            channel = new Channel();

            channel.setUser( user );
            channel.setChannelType( channelType );
        }

        return channel;
    }

    @Override
    public void removeChannel( User user, ChannelType channelType )
    {
        Query query = this.em.createNamedQuery( "Channel_deleteByUserAndChannelType" );

        query.executeUpdate();
    }

    @Override
    public List< Channel > get( User user )
    {
        if ( user != null )
        {
            TypedQuery< Channel > query = this.em.createNamedQuery( "Channel_getByUser", Channel.class );
            query.setParameter( "user", user );

            return query.getResultList();
        } else
        {
            return new ArrayList<>( 0 );
        }
    }

    @Override
    public Channel getEmailChannel( User user )
    {
        List< Channel > channels = this.get( user );
        ChannelType emailChannelType = this.getEmailChannelType();

        for ( Channel channel : channels )
        {
            if ( emailChannelType.equals( channel.getChannelType() ) )
            {
                return channel;
            }
        }

        return null;
    }

    @Override
    public ChannelType getEmailChannelType()
    {
        ChannelType channelType = this.getChannelType( CHANNEL_TYPE_EMAIL );

        if ( channelType == null )
        {
            channelType = new ChannelType();
            channelType.setName( CHANNEL_TYPE_EMAIL );

            channelType = this.put( channelType );
        }

        return channelType;
    }
}
