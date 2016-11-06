package biz.ostw.ee.vfs;

/**
 * @author mathter
 */
public class VfsException extends RuntimeException
{
    private static final long serialVersionUID = 2321628942208798869L;

    public VfsException()
    {
        super();
    }

    public VfsException( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace )
    {
        super( message, cause, enableSuppression, writableStackTrace );
    }

    public VfsException( String message, Throwable cause )
    {
        super( message, cause );
    }

    public VfsException( String message )
    {
        super( message );
    }

    public VfsException( Throwable cause )
    {
        super( cause );
    }
}
