package biz.ostw.ee.vfs;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author mathter
 */
@Embeddable
public class VfsPathId implements Serializable
{
    private static final long serialVersionUID = -4058902084265921709L;

    @Column( name = "id" )
    private long id;

    @Column( name = "name" )
    private String name;

    public VfsPathId()
    {
    }

    public VfsPathId( long parentId, String name )
    {
        this.id = parentId;
        this.name = name;
    }

    public long getParentId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public void setParentId( long parentId )
    {
        this.id = parentId;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) ( id ^ ( id >>> 32 ) );
        result = prime * result + ( ( name == null ) ? 0 : name.hashCode() );
        return result;
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( getClass() != obj.getClass() )
            return false;
        VfsPathId other = (VfsPathId) obj;
        if ( id != other.id )
            return false;
        if ( name == null )
        {
            if ( other.name != null )
                return false;
        } else if ( !name.equals( other.name ) )
            return false;
        return true;
    }
}
