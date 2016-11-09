package biz.ostw.ee.vfs;

/**
 * @author mathter
 */
public interface UserIdService
{
    public int getUserId( String login );

    public int getGroupId( String groupName );
}
