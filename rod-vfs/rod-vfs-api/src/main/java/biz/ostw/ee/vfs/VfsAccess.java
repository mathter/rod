package biz.ostw.ee.vfs;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

/**
 * @author mathter
 */
@Embeddable
@Access( AccessType.FIELD )
public class VfsAccess implements Serializable
{
    private static final long serialVersionUID = -5777860122335374953L;

    private short access;

    public boolean ownerRead()
    {
        return VfsAccessUtil.ownerRead( this.access );
    }

    public boolean ownerWrite()
    {
        return VfsAccessUtil.ownerWrite( this.access );
    }

    public boolean groupRead()
    {
        return VfsAccessUtil.groupRead( this.access );
    }

    public boolean groupWrite()
    {
        return VfsAccessUtil.groupWrite( this.access );
    }

    public boolean otherRead()
    {
        return VfsAccessUtil.otherRead( this.access );
    }

    public boolean otherWrite()
    {
        return VfsAccessUtil.otherWrite( this.access );
    }
}
