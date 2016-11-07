package biz.ostw.ee.vfs;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @author mathter
 */
@Entity
@Table( name = "vfs_paths" )
@Access( AccessType.FIELD )
@Cacheable
@Cache( usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "vfs" )
public class VfsPathFake implements Serializable
{
    private static final long serialVersionUID = 8537134610938684905L;

    @Id
    @Column( name = "id", insertable = false, updatable = false )
    private long id;

    private VfsDir path;

    public VfsPathFake()
    {
    }

    public VfsPathFake( VfsDir dir )
    {
        this.id = dir.id;
        this.path = dir;
    }

    public long getId()
    {
        return id;
    }

    public void setId( long id )
    {
        this.id = id;
    }

    public VfsDir getPath()
    {
        return path;
    }

    public void setPath( VfsDir path )
    {
        this.path = path;
    }
}
