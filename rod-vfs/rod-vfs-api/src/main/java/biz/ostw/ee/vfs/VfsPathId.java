package biz.ostw.ee.vfs;

import java.io.Serializable;

/**
 * @author mathter
 */
public class VfsPathId implements Serializable {
	private static final long serialVersionUID = -4058902084265921709L;

	private long id;

	private String name;

	public VfsPathId() {
	}

	public VfsPathId(long id, String name) {
		this.id = id;
		this.name = name;
	}
}
