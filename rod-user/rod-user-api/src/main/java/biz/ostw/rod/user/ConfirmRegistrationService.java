package biz.ostw.rod.user;

import java.util.UUID;

/**
 * @author mathter
 */
public interface ConfirmRegistrationService
{
    public ConfirmRegistration newInstance(User user);

    public void confirm( UUID uuid );

    public void remove( UUID uuid );
}
