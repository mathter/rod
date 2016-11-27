package biz.ostw.ee.vfs;

/**
 * @author mathter
 */
public interface VfsUserService
{
    public Long getUserId( String login );

    public Long getGroupId( String groupName );
}
