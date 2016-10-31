package biz.ostw.rod.connecting;

import javax.activation.MimeType;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import biz.ostw.ee.config.ConfigService;

/**
 * @author mathter
 */
@Remote( MessageService.class )
@Stateless
public class MessageServiceImpl implements MessageService
{
    private static Logger LOG = LoggerFactory.getLogger( MessageServiceImpl.class );

    private static final String FROM_EMAIL = "biz.oswt.rod.connecting.from.email";

    @Resource( mappedName = "java:/mail/rod" )
    private Session mailSession;

    @EJB
    private ConfigService configService;

    @Override
    public void send( Channel channel, Object subject, Object content, MimeType mimeType ) throws ConnectingException
    {
        if ( channel == null )
        {
            throw new NullPointerException( "address can't be null!" );
        }

        try
        {
            String from = this.configService.getString( FROM_EMAIL );

            MimeMessage m = new MimeMessage( this.mailSession );
            m.setRecipients( Message.RecipientType.TO, String.valueOf( channel.getValue() ) );
            m.setSender( new InternetAddress( from ) );
            m.setContent( content, mimeType.toString() );
            m.setSubject( String.valueOf( subject ) );

            Transport.send( m );
        } catch ( Exception e )
        {
            LOG.error( "Can't send message to='" + channel + ", subject='" + subject + "' content=" + content, e );
            throw new ConnectingException( e );
        }
    }
}
