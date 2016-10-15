package biz.ostw.rod.site.user.profile;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

/**
 * @author mathter
 */
@ManagedBean
@ViewScoped
public class RegistrationInfoBean implements Serializable
{
    private static final long serialVersionUID = -5179914402818357479L;

    @Inject
    private RegistrationInfo registrationInfo;

    public RegistrationInfo getRegistrationInfo()
    {
        return registrationInfo;
    }

    public void setRegistrationInfo( RegistrationInfo registrationInfo )
    {
        this.registrationInfo = registrationInfo;
    }
}
