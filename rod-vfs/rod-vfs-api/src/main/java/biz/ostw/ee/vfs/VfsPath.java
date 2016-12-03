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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

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
    @Column( name = "parent_id" )
    // @SequenceGenerator( name = "vfs_paths_gen", sequenceName = "vfs_paths_seq", allocationSize = 1, initialValue = 1
    // )
    // @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "vfs_paths_gen" )
    protected Long parent_id;

    @Id
    @Column( name = "name" )
    protected String name;

    @Column( name = "id", insertable = false, updatable = false, unique = true )
    protected long id;

    @Column( name = "create_date", nullable = false )
    @Temporal( TemporalType.TIMESTAMP )
    private Date createDate;

    @Column( name = "modify_date", nullable = false )
    @Temporal( TemporalType.TIMESTAMP )
    private Date modifyDate;

    @Column( name = "owner_id", nullable = true )
    private Long ownerId;

    @Column( name = "group_id", nullable = true )
    private Long groupId;

    private VfsAccess access;

    @Enumerated( EnumType.ORDINAL )
    @Column( name = "type_id", nullable = false )
    private VFS_TYPE type;

    @Transient
    private String path;

    public long getId()
    {
        return id;
    }

    public void setId( Long id )
    {
        this.id = id;
    }

    public Long getParent_id()
    {
        return parent_id;
    }

    public void setParent_id( Long parent_id )
    {
        this.parent_id = parent_id;
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

    public String getPath()
    {
        return this.path;
    }

    protected void setPath( String path )
    {
        this.path = path;
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
