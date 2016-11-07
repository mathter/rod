package biz.ostw.ee.vfs;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
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
@DiscriminatorColumn( name = "type_id", discriminatorType = DiscriminatorType.INTEGER )
@Inheritance( strategy = InheritanceType.SINGLE_TABLE )
@Cacheable
@Cache( usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "vfs" )
@NamedQueries( {

} )
@IdClass( VfsPathId.class )
public abstract class VfsPath implements Serializable
{
    private static final long serialVersionUID = -9102263499943206894L;

    @Column( name = "id" )
    protected long id;

    @Id
    @Column( name = "name", nullable = false )
    private String name;

    @Id
    @Column( name = "parent_id", insertable = false, updatable = false )
    private long parentId;

    @Column( name = "create_date", nullable = false )
    @Temporal( TemporalType.TIMESTAMP )
    private Date createDate;

    @Column( name = "modify_date", nullable = false )
    @Temporal( TemporalType.TIMESTAMP )
    private Date modifyDate;

    @ManyToOne( fetch = FetchType.EAGER )
    @JoinColumn( name = "parent_id", nullable = true )
    private VfsPathFake parent;

    @Column( name = "owner_id", nullable = true )
    private Long ownerId;

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

    public VfsDir getParent()
    {
        return this.parent.getPath();
    }

    public void setParent( VfsDir parent )
    {
        this.parent = parent != null ? new VfsPathFake( parent ) : null;
    }

    public String getPath()
    {
        return ( this.parent != null ? this.parent.getPath() : "" ) + "/" + this.name;
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
