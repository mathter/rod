package biz.ostw.ee.vfs;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table( name = "vfs_file_contents" )
@Access( AccessType.FIELD )
@NamedQueries(
{
    @NamedQuery( name = "VfsFileContent_removeById", query = "delete from VfsFileContent where id = :id" )
} )
public class VfsFileContent implements Serializable
{
    private static final long serialVersionUID = -4992903649621898395L;
    
    @Id
    @OneToOne( fetch = FetchType.EAGER )
    @JoinColumn( name = "id" )
    private VfsFile path;

    public VfsFile getPath()
    {
        return path;
    }

    public void setPath( VfsFile path )
    {
        this.path = path;
    }
}
