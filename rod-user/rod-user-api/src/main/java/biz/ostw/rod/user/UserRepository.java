package biz.ostw.rod.user;

import biz.ostw.persistence.Repository;

/**
 * @author mathter
 */
public interface UserRepository extends Repository
{
    public User get( String login );

    public Role getRegisteredRole();
}
