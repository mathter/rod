package biz.ostw.rod.jsf.validator;

import java.security.Principal;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import biz.ostw.rod.user.User;
import biz.ostw.rod.user.UserRepository;

/**
 * @author mathter
 */
@ManagedBean
@ViewScoped
public class UserExistsValidator implements Validator
{
    @EJB
    private UserRepository userRepository;

    @Override
    public void validate( FacesContext context, UIComponent component, Object value ) throws ValidatorException
    {
        Principal principal = context.getExternalContext().getUserPrincipal();
        User user = userRepository.get( (String) value );

        if ( ( principal == null && user != null ) || ( principal != null && !principal.getName().equals( user.getLogin() ) ) )
        {
            Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
            ResourceBundle messages = ResourceBundle.getBundle( "biz.ostw.rod.site.messages", locale );

            String message = String.format( messages.getString( "fragment.registrationInfo.authDataPanel.login.error.userAlreadyExists" ),
                value );

            throw new ValidatorException( new FacesMessage( FacesMessage.SEVERITY_ERROR, message, "" ) );
        }
    }
}
