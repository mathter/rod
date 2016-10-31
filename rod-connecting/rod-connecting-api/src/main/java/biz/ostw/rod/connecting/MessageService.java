package biz.ostw.rod.connecting;

import javax.activation.MimeType;

/**
 * @author mathter
 */
public interface MessageService
{
    public void send( Channel channel, Object subject, Object content, MimeType mimeType ) throws ConnectingException;
}
