package biz.ostw.ee.vfs;

/**
 * @author mathter
 */
public class VfsPathAlreadyExistsException extends VfsException
{
    private static final long serialVersionUID = 2606463105582642868L;

    public VfsPathAlreadyExistsException( String path )
    {
        super( "Path '" + path + "' already exists!" );
    }
}
