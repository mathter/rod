package biz.ostw.rod.user;

import java.util.Date;
import java.util.UUID;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import biz.ostw.persistence.jpa.AbstractJPARepository;
import biz.ostw.rod.connecting.MessageService;
import biz.ostw.rod.user.channel.Channel;
import biz.ostw.rod.user.channel.ChannelService;

/**
 * @author mathter
 */
@Remote( ConfirmRegistrationService.class )
@Stateless
public class ConfirmRegistrationImpl extends AbstractJPARepository implements ConfirmRegistrationService
{
    @PersistenceContext( name = "biz.ostw.rod.user" )
    private EntityManager em;

    @EJB
    private UserRepository userRepository;

    @EJB
    private ChannelService channelService;

    @EJB
    private MessageService messageService;

    @Override
    @Transactional( TxType.REQUIRED )
    public ConfirmRegistration newInstance( User user )
    {
        ConfirmRegistration entity = new ConfirmRegistration();

        entity.setUuid( UUID.randomUUID() );
        entity.setUser( user );
        entity.setDate( new Date() );

        entity = this.em.merge( entity );
        
        Channel emailChannel = this.channelService.getEmailChannel( user );

        this.messageService.send( emailChannel, "Registration", "Content" );

        return entity;
    }

    @Override
    @Transactional( TxType.REQUIRED )
    public void confirm( UUID uuid )
    {
        ConfirmRegistration confirmRegistration = this.get( ConfirmRegistration.class, uuid );

        if ( confirmRegistration != null )
        {
            User user = confirmRegistration.getUser();

            if ( user != null )
            {
                confirmRegistration.setComplete( true );
                user.getAccessInfo().setRegistered( true );

                this.put( confirmRegistration );
                user = this.put( user );
            } else
            {
                throw new IllegalStateException( "There is no user for '" + uuid + "'!" );
            }
        } else
        {
            throw new IllegalStateException( "There is no entry for '" + uuid + "'!" );
        }
    }

    @Override
    @Transactional( TxType.REQUIRED )
    public void remove( UUID uuid )
    {
        Query query = this.em.createNamedQuery( "ConfirmRegistration_remove" );
        query.setParameter( "uuid", uuid );

        query.executeUpdate();
    }

    @Override
    protected EntityManager getEntityManager()
    {
        return this.em;
    }
}
