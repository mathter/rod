package biz.ostw.ee.vfs;

import java.io.Serializable;

/**
 * @author mathter
 */
public class VfsPathId implements Serializable
{
    private static final long serialVersionUID = -4058902084265921709L;

    private long parentId;

    private String name;

    public VfsPathId()
    {
    }

    public VfsPathId( long parentId, String name )
    {
        this.parentId = parentId;
        this.name = name;
    }
}
