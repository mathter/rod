package biz.ostw.ee.vfs;

import java.util.List;

/**
 * @author mathter
 */
public interface VfsService {
	public char getPathSeparator();

	/**
	 * Return root directory if parent is null.
	 * 
	 * @param parent
	 * @return
	 */
	public List<VfsPath> getByParent(VfsPath parent);

	public VfsPath getByPath(String path) throws NullPointerException, IllegalArgumentException;

	public VfsPath createDir(VfsPath parent, String name) throws VfsPathAlreadyExistsException, NullPointerException;

	public VfsPath createFile(VfsPath parent, String name) throws VfsPathAlreadyExistsException, NullPointerException;

	public VfsPath rename(VfsPath path, String name) throws VfsPathAlreadyExistsException;

	public VfsPath move(VfsPath path, VfsPath newRoot) throws VfsPathAlreadyExistsException;

	public void remove(VfsPath path) throws VfsDirectoryNotEmptyException, VfsPathDoNotExists, NullPointerException;

	public void remove(VfsPath path, boolean force)
			throws VfsDirectoryNotEmptyException, VfsPathDoNotExists, NullPointerException;
}
