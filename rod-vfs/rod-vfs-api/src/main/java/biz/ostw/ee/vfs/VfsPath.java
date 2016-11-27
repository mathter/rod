package biz.ostw.ee.vfs;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
@IdClass( VfsPathId.class )
public class VfsPath implements Serializable
{
    private static final long serialVersionUID = -9102263499943206894L;

    @Id
    @Column( name = "id" )
    @SequenceGenerator( name = "vfs_paths_gen", sequenceName = "vfs_paths_seq", allocationSize = 1, initialValue = 1 )
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "vfs_paths_gen" )
    protected long id;

    @Id
    @Column( name = "name" )
    protected String name;

    @Column( name = "create_date", nullable = false )
    @Temporal( TemporalType.TIMESTAMP )
    private Date createDate;

    @Column( name = "modify_date", nullable = false )
    @Temporal( TemporalType.TIMESTAMP )
    private Date modifyDate;

    @ManyToOne( fetch = FetchType.EAGER )
    @JoinColumn( name = "parent_id" )
    private FakeVfsPath parent;

    @Column( name = "owner_id", nullable = true )
    private Long ownerId;

    @Column( name = "group_id", nullable = true )
    private Long groupId;

    private VfsAccess access;

    @Enumerated( EnumType.ORDINAL )
    @Column( name = "type_id", nullable = false )
    private VFS_TYPE type;

    public long getId()
    {
        return id;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public Date getCreateDate()
    {
        return this.createDate;
    }

    public void setCreateDate( Date date )
    {
        this.createDate = date;
    }

    public Date getModifyDate()
    {
        return this.modifyDate;
    }

    public void setModifyDate( Date date )
    {
        this.modifyDate = date;
    }

    public VfsPath getParent()
    {
        return this.parent != null ? this.parent.getPath() : null;
    }

    public void setParent( VfsPath parent )
    {
        this.parent = parent != null ? new FakeVfsPath( parent ) : null;
    }

    public String getPath()
    {
        return ( this.parent != null ? this.parent.getPath() : "" ) + "/" + this.name;
    }

    public VFS_TYPE getType()
    {
        return type;
    }

    public void setType( VFS_TYPE type )
    {
        if ( type == null )
        {
            throw new NullPointerException();
        }

        this.type = type;
    }

    public Long getOwnerId()
    {
        return ownerId;
    }

    public void setOwnerId( Long ownerId )
    {
        this.ownerId = ownerId;
    }

    public Long getGroupId()
    {
        return groupId;
    }

    public void setGroupId( Long groupId )
    {
        this.groupId = groupId;
    }

    public VfsAccess getAccess()
    {
        return this.access;
    }

    public void setAccess( VfsAccess access )
    {
        this.access = access;
    }

    @Override
    public int hashCode()
    {
        return (int) this.id;
    }

    @Override
    public boolean equals( Object obj )
    {
        return this == obj || ( this.getClass().equals( obj.getClass() ) && this.id == ( (VfsPath) obj ).id );
    }

    @Override
    public String toString()
    {
        return this.getPath();
    }
}
