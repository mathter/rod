package biz.ostw.rod.site.user.profile;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.activation.MimeType;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import biz.ostw.ee.config.ConfigService;
import biz.ostw.ee.vfs.VfsService;
import biz.ostw.rod.connecting.MessageService;
import biz.ostw.rod.user.ConfirmRegistration;
import biz.ostw.rod.user.ConfirmRegistrationService;
import biz.ostw.rod.user.User;
import biz.ostw.rod.user.UserRepository;
import biz.ostw.rod.user.channel.Channel;
import biz.ostw.rod.user.channel.ChannelService;
import freemarker.cache.StringTemplateLoader;
import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

/**
 * @author mathter
 */
@Named
@Dependent
public class ProfileHelper
{
    private static final Logger LOG = LoggerFactory.getLogger( ProfileHelper.class );

    private static final String CONFIG_SUBJECT_TEMPLATE = "biz.ostw.rod.site.user.profile.confirmRequestSubjectTemplate";

    private static final String CONFIG_MESSAGE_TEMPLATE = "biz.ostw.rod.site.user.profile.confirmRequestMessageTemplate";

    @EJB
    private ChannelService channelService;

    @EJB
    private UserRepository userRepository;

    @EJB
    private MessageService messageService;

    @EJB
    private ConfigService configService;

    @EJB
    private VfsService vfsService;

    @EJB
    private ConfirmRegistrationService confirmRegistrationService;

    public void createProfile( User user )
    {
        ConfirmRegistration confirmRegistration = this.confirmRegistrationService.newInstance( user );

        this.sendConfirmRequest( user, confirmRegistration );
    }

    private void sendConfirmRequest( User user, ConfirmRegistration confirmRegistration )
    {
        try
        {
            Channel emailChannel = this.channelService.getEmailChannel( user );
            Locale browserLocale = FacesContext.getCurrentInstance().getViewRoot().getLocale();

            this.messageService.send( emailChannel, this.getMessageSubject( browserLocale ),
                this.getMessageText( browserLocale, user, confirmRegistration ), new MimeType( "text/html" ) );

        } catch ( Exception e )
        {
            LOG.error( "Can't send confirm request!", e );
        }
    }

    private String getMessageSubject( Locale locale )
        throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException
    {
        Configuration configuration = new Configuration( Configuration.VERSION_2_3_25 );

        configuration.setTemplateLoader( new TemplateLoader() );

        Template template = configuration.getTemplate( CONFIG_SUBJECT_TEMPLATE, locale );
        StringWriter writer = new StringWriter();

        template.process( null, writer );

        return writer.getBuffer().toString();
    }

    private String getMessageText( Locale locale, User user, ConfirmRegistration confirmRegistration )
        throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException
    {
        Configuration configuration = new Configuration( Configuration.VERSION_2_3_25 );

        configuration.setTemplateLoader( new TemplateLoader() );

        Template template = configuration.getTemplate( CONFIG_MESSAGE_TEMPLATE, locale );
        StringWriter writer = new StringWriter();

        Map< String, Object > bundle = new HashMap<>();

        bundle.put( "user", user );
        bundle.put( "uuid", confirmRegistration.getUuid() );

        template.process( bundle, writer );

        return writer.getBuffer().toString();
    }

    private class TemplateLoader extends StringTemplateLoader
    {
        @Override
        public Object findTemplateSource( String name )
        {
            String value = configService.getString( name );

            if ( value != null )
            {
                this.putTemplate( name, value );
            }

            return super.findTemplateSource( name );
        }
    }
}
