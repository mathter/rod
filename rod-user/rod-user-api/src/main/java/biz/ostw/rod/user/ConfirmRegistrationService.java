package biz.ostw.rod.user;

import java.util.UUID;

/**
 * @author mathter
 */
public interface ConfirmRegistrationService
{
    public ConfirmRegistration newInstance(User user);

    public User confirm( UUID uuid );

    public void remove( UUID uuid );
}
