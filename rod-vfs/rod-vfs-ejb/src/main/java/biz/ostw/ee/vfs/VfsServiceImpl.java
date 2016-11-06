package biz.ostw.ee.vfs;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

/**
 * @author mathter
 */
@Remote( VfsService.class )
@Stateless
@Transactional( TxType.REQUIRED )
public class VfsServiceImpl implements VfsService
{
    @PersistenceContext( name = "biz.ostw.ee.vfs" )
    private EntityManager em;

    @Override
    public List< VfsPath > getByParent( VfsDir root )
    {
        TypedQuery< VfsPath > query;

        if ( root != null )
        {
            query = this.em.createNamedQuery( "VfsPath_getByParent", VfsPath.class );
            query.setParameter( "parent", root );
        } else
        {
            query = this.em.createNamedQuery( "VfsPath_getByRoot", VfsPath.class );
        }

        return query.getResultList();
    }

    @Override
    public VfsDir createDir( VfsDir root, String name ) throws VfsPathAlreadyExistsException
    {
        Date date = new Date();
        VfsDir vfsPath = new VfsDir();

        vfsPath.setParent( root );
        vfsPath.setName( name );
        vfsPath.setCreateDate( date );
        vfsPath.setModifyDate( date );

        return this.em.merge( vfsPath );
    }

    @Override
    public VfsFile createFile( VfsDir root, String name ) throws VfsPathAlreadyExistsException
    {
        Date date = new Date();
        VfsFile vfsPath = new VfsFile();

        vfsPath.setParent( root );
        vfsPath.setName( name );
        vfsPath.setCreateDate( date );
        vfsPath.setModifyDate( date );

        this.em.persist( vfsPath );

        VfsFileContent vfsFileContent = new VfsFileContent();

        vfsFileContent.setPath( vfsPath );
        this.em.persist( vfsFileContent );

        return vfsPath;
    }

    @Override
    public VfsPath rename( VfsPath path, String name ) throws VfsPathAlreadyExistsException
    {
        path.setName( name );

        return this.em.merge( path );
    }

    @Override
    public VfsPath move( VfsPath path, VfsDir newRoot ) throws VfsPathAlreadyExistsException
    {
        path.setParent( newRoot );

        return this.em.merge( path );
    }

    @Override
    public void remove( VfsPath path ) throws VfsDirectoryNotEmptyException, VfsPathDoNotExists, NullPointerException
    {
        if ( path == null )
        {
            throw new NullPointerException();
        }

        if ( path.getId() == 0 )
        {
            throw new VfsPathDoNotExists( path );
        }

        this.remove( path, false );
    }

    @Override
    public void remove( VfsPath path, boolean force ) throws VfsDirectoryNotEmptyException, VfsPathDoNotExists, NullPointerException
    {
        if ( path == null )
        {
            throw new NullPointerException();
        }

        if ( path.getId() == 0 )
        {
            throw new VfsPathDoNotExists( path );
        }

        if ( path instanceof VfsFile || force )
        {
            this.em.remove( this.em.merge( path ) );
        } else
        {
            List< VfsPath > childs = this.getByParent( (VfsDir) path );

            if ( childs.size() == 0 )
            {
                this.em.remove( this.em.merge( path ) );
            } else
            {
                throw new VfsDirectoryNotEmptyException( path );
            }
        }
    }
}
