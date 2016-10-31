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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import biz.ostw.persistence.jpa.AbstractJPARepository;
import biz.ostw.rod.user.channel.ChannelService;

/**
 * @author mathter
 */
@Remote( ConfirmRegistrationService.class )
@Stateless
public class ConfirmRegistrationServiceImpl extends AbstractJPARepository implements ConfirmRegistrationService
{
    private static final Logger LOG = LoggerFactory.getLogger( ConfirmRegistrationServiceImpl.class );

    @PersistenceContext( name = "biz.ostw.rod.user" )
    private EntityManager em;

    @EJB
    private UserRepository userRepository;

    @EJB
    private ChannelService channelService;

    @Override
    @Transactional( TxType.REQUIRED )
    public ConfirmRegistration newInstance( User user )
    {
        ConfirmRegistration entity = new ConfirmRegistration();

        entity.setUuid( UUID.randomUUID() );
        entity.setUser( user );
        entity.setDate( new Date() );

        entity = this.em.merge( entity );

        LOG.info( "Create confirm registration '{}' for '{}'", entity.getUuid(), user.getLogin() );

        return entity;
    }

    @Override
    @Transactional( TxType.REQUIRED )
    public User confirm( UUID uuid )
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

                LOG.info( "Registration is completed for '{}'", user.getLogin() );
                
                return user;
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
