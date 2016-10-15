package biz.ostw.rod.user;

import javax.ejb.Remote;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import biz.ostw.persistence.jpa.AbstractJPARepository;
import biz.ostw.rod.user.Role;
import biz.ostw.rod.user.User;
import biz.ostw.rod.user.UserRepository;

/**
 * @author mathter
 */
@Remote( UserRepository.class )
@Stateful
public class UserRepositoryImpl extends AbstractJPARepository implements UserRepository
{
    @PersistenceContext( name = "biz.ostw.rod.user" )
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return this.em;
    }

    @Override
    public User get( String login )
    {
        TypedQuery< User > query = this.em.createNamedQuery( "User_getByLogin", User.class );

        query.setParameter( "login", login );

        try
        {
            return query.getSingleResult();
        } catch ( NoResultException e )
        {
            return null;
        }
    }

    @Override
    public Role getRegisteredRole()
    {
        TypedQuery< Role > query = this.em.createNamedQuery( "Role_getByName", Role.class );

        query.setParameter( "name", "registered" );

        try
        {
            return query.getSingleResult();
        } catch ( NoResultException e )
        {
            return null;
        }
    }
}
