package biz.ostw.rod.connecting;

/**
 * @author mathter
 */
public interface MessageService
{
    public void send( Channel channel, Object subject, Object content ) throws ConnectingException;
}
