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
    private static final char PATH_SEPARATOR_CHAR = '/';

    private static final String PATH_SEPARATOR_STRING = "" + PATH_SEPARATOR_CHAR;

    @PersistenceContext( name = "biz.ostw.ee.vfs" )
    private EntityManager em;

    @Override
    public char getPathSeparator()
    {
        return PATH_SEPARATOR_CHAR;
    }

    @Override
    public VfsPath getByPath( String path )
    {
        if ( path == null )
        {
            throw new NullPointerException();
        }

        if ( path.equals( PATH_SEPARATOR_CHAR ) )
        {
            return null;
        }

        if ( !path.startsWith( PATH_SEPARATOR_STRING ) )
        {
            throw new IllegalArgumentException( "Path is not absolute! Path is '" + path + "'" );
        }

        String[] pathParts = path.split( "" + PATH_SEPARATOR_CHAR );

        return null;
    }

    @Override
    public List< VfsPath > getByParent( VfsDir parent )
    {
        TypedQuery< VfsPath > query;

        if ( parent != null )
        {
            query = this.em.createNamedQuery( "VfsPath_getByParent", VfsPath.class );
            query.setParameter( "parent", parent );
        } else
        {
            query = this.em.createNamedQuery( "VfsPath_getByRoot", VfsPath.class );
        }

        return query.getResultList();
    }

    @Override
    public VfsDir createDir( VfsDir parent, String name ) throws VfsPathAlreadyExistsException, NullPointerException
    {
        if ( parent == null )
        {
            throw new NullPointerException();
        }

        Date date = new Date();
        VfsDir vfsPath = new VfsDir();

        vfsPath.setParent( parent );
        vfsPath.setName( name );
        vfsPath.setCreateDate( date );
        vfsPath.setModifyDate( date );

        return this.em.merge( vfsPath );
    }

    @Override
    public VfsFile createFile( VfsDir parent, String name ) throws VfsPathAlreadyExistsException, NullPointerException
    {
        if ( parent == null )
        {
            throw new NullPointerException();
        }

        Date date = new Date();
        VfsFile vfsPath = new VfsFile();

        vfsPath.setParent( parent );
        vfsPath.setName( name );
        vfsPath.setCreateDate( date );
        vfsPath.setModifyDate( date );

        this.em.persist( vfsPath );

        VfsFileContent vfsFileContent = new VfsFileContent();

        vfsFileContent.setId( vfsPath.getId() );
        this.em.persist( vfsFileContent );

        return vfsPath;
    }

    @Override
    public VfsPath rename( VfsPath path, String name ) throws VfsPathAlreadyExistsException, NullPointerException
    {
        if ( path == null || name == null )
        {
            throw new NullPointerException();
        }

        path.setName( name );

        return this.em.merge( path );
    }

    @Override
    public VfsPath move( VfsPath path, VfsDir newRoot ) throws VfsPathAlreadyExistsException, NullPointerException
    {
        if ( path == null || newRoot == null )
        {
            throw new NullPointerException();
        }

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
