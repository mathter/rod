package biz.ostw.ee.vfs;

/**
 * @author mathter
 */
public class VfsPathDoNotExists extends VfsException
{
    private static final long serialVersionUID = -7929411297184060158L;

    public VfsPathDoNotExists( String message )
    {
        super( "Path '" + message + "' do not exixts!" );
    }

    public VfsPathDoNotExists( VfsPath path )
    {
        super( "Path '" + ( path != null ? path.getPath() : null ) + "' do not exixts!" );
    }
}
