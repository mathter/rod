package biz.ostw.rod.jsf.validator;

import java.security.Principal;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * @author mathter
 */
@ManagedBean
@ViewScoped
public class PasswordValidator implements Validator
{
    @Override
    public void validate( FacesContext context, UIComponent component, Object value ) throws ValidatorException
    {
        Principal principal = context.getExternalContext().getUserPrincipal();

        String password = (String) value;
        String repeatPassword = (String) component.getAttributes().get( "repeatPassword" );

        if ( principal == null )
        {
            if ( password == null || !password.equals( repeatPassword ) )
            {
                this.fireError();
            }
        } else
        {
            if ( password != null && !password.equals( repeatPassword ) )
            {
                this.fireError();
            }
        }
    }

    private void fireError()
    {
        Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
        ResourceBundle messages = ResourceBundle.getBundle( "biz.ostw.rod.site.messages", locale );

        String message = messages.getString( "fragment.registrationInfo.authDataPanel.password.error.passwordNotSame" );

        throw new ValidatorException( new FacesMessage( FacesMessage.SEVERITY_ERROR, message, "" ) );
    }
}
