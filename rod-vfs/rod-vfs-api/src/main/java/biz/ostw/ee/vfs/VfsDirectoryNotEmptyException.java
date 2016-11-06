package biz.ostw.ee.vfs;

/**
 * @author mathter
 */
public class VfsDirectoryNotEmptyException extends VfsException
{
    private static final long serialVersionUID = -7197616715881751550L;

    public VfsDirectoryNotEmptyException( String path )
    {
        super( "Directory '" + path + "' is not empty! Use force = true!" );
    }

    public VfsDirectoryNotEmptyException( VfsPath path )
    {
        super( "Directory '" + ( path != null ? path.getPath() : null ) + "' is not empty! Use force = true!" );
    }
}
