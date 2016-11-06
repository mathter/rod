package biz.ostw.ee.vfs;

import java.util.List;

/**
 * @author mathter
 */
public interface VfsService
{
    public List< VfsPath > getByParent( VfsDir root );

    public VfsDir createDir( VfsDir root, String name ) throws VfsPathAlreadyExistsException;

    public VfsFile createFile( VfsDir root, String name ) throws VfsPathAlreadyExistsException;

    public VfsPath rename( VfsPath path, String name ) throws VfsPathAlreadyExistsException;

    public VfsPath move( VfsPath path, VfsDir newRoot ) throws VfsPathAlreadyExistsException;

    public void remove( VfsPath path ) throws VfsDirectoryNotEmptyException, VfsPathDoNotExists, NullPointerException;

    public void remove( VfsPath path, boolean force ) throws VfsDirectoryNotEmptyException, VfsPathDoNotExists, NullPointerException;
}
