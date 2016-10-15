package biz.ostw.rod.user.channel;

import java.util.List;

import biz.ostw.persistence.Repository;
import biz.ostw.rod.user.User;

/**
 * @author mathter
 */
public interface ChannelService extends Repository
{
    public static final String CHANNEL_TYPE_EMAIL = "email";

    public List< ChannelType > geChannelTypes();

    public ChannelType getChannelType( String name );

    public Channel createChannel( User user, ChannelType channelType );

    public void removeChannel( User user, ChannelType channelType );

    public List< Channel > get( User user );

    public ChannelType getEmailChannelType();
}
