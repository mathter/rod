package biz.ostw.ee.vfs;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
public class VfsPath implements Serializable
{
    private static final long serialVersionUID = -9102263499943206894L;

    @EmbeddedId
    private VfsPathId id;

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

    @Column( name = "back_ref", insertable = false, updatable = false )
    private long backRef;

    @Transient
    private String path;

    public Long getParentId()
    {
        return this.id != null ? this.id.getParentId() : null;
    }

    public void setParentId( Long parentId )
    {
        if ( this.id != null )
        {
            this.id.setParentId( parentId );
        } else
        {
            this.id = new VfsPathId( parentId, null );
        }
    }

    public String getName()
    {
        return this.id != null ? this.id.getName() : null;
    }

    public void setName( String name )
    {
        if ( this.id != null )
        {
            this.id.setName( name );
        } else
        {
            this.id = new VfsPathId( 0, name );
        }
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

    public void setPath( String path )
    {
        this.path = path;
    }

    public long getBackRef()
    {
        return backRef;
    }

    public void setBackRef( long backRef )
    {
        this.backRef = backRef;
    }

    @Override
    public int hashCode()
    {
        return this.id.hashCode();
    }

    @Override
    public boolean equals( Object obj )
    {
        return this == obj || ( this.getClass().equals( obj.getClass() ) && this.id.equals( ( (VfsPath) obj ).id ) );
    }

    @Override
    public String toString()
    {
        return this.getPath();
    }
}
