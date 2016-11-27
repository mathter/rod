package biz.ostw.ee.vfs;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author mathter
 */
@Entity
@Access( AccessType.FIELD )
@Table( name = "vfs_paths" )
public class FakeVfsPath implements Serializable
{
    private static final long serialVersionUID = 560243161920135873L;

    @Id
    @Column( name = "id" )
    private long id;

    @OneToOne( fetch = FetchType.EAGER )
    @JoinColumn( name = "id", insertable = false, updatable = false )
    private VfsPath path;

    public FakeVfsPath()
    {
        this( null );
    }

    public FakeVfsPath( VfsPath path )
    {
        this.path = path;
        this.id = path.getId();
    }

    public VfsPath getPath()
    {
        return path;
    }

    public void setPath( VfsPath path )
    {
        this.path = path;
    }
}
