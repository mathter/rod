package biz.ostw.rod.site.user.profile;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import biz.ostw.rod.user.User;
import biz.ostw.rod.user.UserRepository;

/**
 * @author mathter
 */
@ManagedBean
@ViewScoped
public class ConfirmationBean
{
    private User user;

    @EJB
    private UserRepository userRepository;

    public User getUser()
    {
        return user;
    }
}
