package biz.ostw.ee.vfs;

import java.io.Serializable;

/**
 * @author mathter
 */
public class VfsPathId implements Serializable
{
    private static final long serialVersionUID = -4058902084265921709L;

    private String name;

    private long parentId;

    public VfsPathId()
    {
    }

    public VfsPathId( long parentId, String name )
    {
        this.parentId = parentId;
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public long getParentId()
    {
        return parentId;
    }

    public void setParentId( long parentId )
    {
        this.parentId = parentId;
    }
}
