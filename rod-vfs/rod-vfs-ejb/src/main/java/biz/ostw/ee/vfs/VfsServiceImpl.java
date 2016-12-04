package biz.ostw.ee.vfs;

import java.util.ArrayDeque;
import java.util.Date;
import java.util.Deque;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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

        String[] pathParts = path.split( PATH_SEPARATOR_STRING );

        if ( pathParts.length == 0 )
        {
            pathParts = new String[]
            {
                ""
            };
        }

        VfsPath vfsPath = null;

        for ( String pathPart : pathParts )
        {
            long backRef = vfsPath != null ? vfsPath.getBackRef() : 0;
            vfsPath = this.em.find( VfsPath.class, new VfsPathId( backRef, pathPart ) );

            if ( vfsPath == null )
            {
                break;
            }
        }

        if ( vfsPath != null )
        {
            vfsPath.setPath( path );
        }

        return vfsPath;
    }

    @Override
    public List< VfsPath > getByParent( VfsPath parent )
    {
        CriteriaBuilder cb = this.em.getCriteriaBuilder();
        CriteriaQuery< VfsPath > criteriaQuery = cb.createQuery( VfsPath.class );
        Root< VfsPath > root = criteriaQuery.from( VfsPath.class );

        criteriaQuery.select( root );

        if ( parent != null )
        {
            criteriaQuery.where( cb.equal( root.get( VfsPath_.id ).get( VfsPathId_.id ), 0 ) );
        } else
        {
            criteriaQuery.where( cb.equal( root.get( VfsPath_.id ).get( VfsPathId_.id ), 0L ) );
        }

        TypedQuery< VfsPath > query = em.createQuery( criteriaQuery );

        return setPath( parent, query.getResultList() );
    }

    private static VfsPath setPath( VfsPath parent, VfsPath path )
    {
        final String parentPath = parent != null ? parent.getPath().intern() : PATH_SEPARATOR_STRING;

        path.setPath( parentPath + ( parentPath.equals( PATH_SEPARATOR_STRING ) ? "" : PATH_SEPARATOR_CHAR ) + path.getName() );

        return path;
    }

    private static List< VfsPath > setPath( VfsPath parent, List< VfsPath > paths )
    {
        final String parentPath = parent != null ? parent.getPath().intern() : PATH_SEPARATOR_STRING;

        paths.stream().forEach(
            e -> e.setPath( parentPath + ( parentPath.equals( PATH_SEPARATOR_STRING ) ? "" : PATH_SEPARATOR_CHAR ) + e.getName() ) );

        return paths;
    }

    @Override
    public VfsPath createDir( VfsPath parent, String name ) throws VfsPathAlreadyExistsException, NullPointerException
    {
        if ( parent == null )
        {
            throw new NullPointerException();
        }

        if ( parent.getType() != VFS_TYPE.DIRECTORY )
        {
            throw new IllegalArgumentException( "parent is not directory! parent has type " + parent.getType() );
        }

        Date date = new Date();
        VfsPath vfsPath = new VfsPath();

        vfsPath.setParentId( parent.getBackRef() );
        vfsPath.setName( name );
        vfsPath.setCreateDate( date );
        vfsPath.setModifyDate( date );
        vfsPath.setType( VFS_TYPE.DIRECTORY );

        this.em.persist( vfsPath );

        return setPath( parent, vfsPath );
    }

    @Override
    public VfsPath createFile( VfsPath parent, String name ) throws VfsPathAlreadyExistsException, NullPointerException
    {
        if ( parent == null )
        {
            throw new NullPointerException();
        }

        if ( parent.getType() != VFS_TYPE.DIRECTORY )
        {
            throw new IllegalArgumentException( "parent is not directory! parent has type " + parent.getType() );
        }

        Date date = new Date();
        VfsPath vfsPath = new VfsPath();
        vfsPath.setName( name );
        vfsPath.setCreateDate( date );
        vfsPath.setModifyDate( date );
        vfsPath.setType( VFS_TYPE.FILE );

        this.em.persist( vfsPath );

        VfsFileContent vfsFileContent = new VfsFileContent();

        // vfsFileContent.setId( vfsPath.getId() );
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
    public VfsPath move( VfsPath path, VfsPath newRoot ) throws VfsPathAlreadyExistsException, NullPointerException
    {
        if ( path == null || newRoot == null )
        {
            throw new NullPointerException();
        }

        return this.em.merge( path );
    }

    @Override
    public void remove( VfsPath path ) throws VfsDirectoryNotEmptyException, VfsPathDoNotExists, NullPointerException
    {
        if ( path == null )
        {
            throw new NullPointerException();
        }

        // if ( path.getId() == 0 )
        // {
        // throw new VfsPathDoNotExists( path );
        // }

        this.remove( path, false );
    }

    @Override
    public void remove( VfsPath path, boolean force ) throws VfsDirectoryNotEmptyException, VfsPathDoNotExists, NullPointerException
    {
        if ( path == null )
        {
            throw new NullPointerException();
        }

        // if ( path.getId() == 0 )
        // {
        // throw new VfsPathDoNotExists( path );
        // }

        if ( path.getType() == VFS_TYPE.FILE || force )
        {
            this.em.remove( this.em.merge( path ) );
        } else
        {
            List< VfsPath > childs = this.getByParent( (VfsPath) path );

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
