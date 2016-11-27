package biz.ostw.ee.vfs;

import java.io.Serializable;
import java.sql.Blob;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author mathter
 */
@Entity
@Table(name = "vfs_file_contents")
@Access(AccessType.FIELD)
public class VfsFileContent implements Serializable {
	private static final long serialVersionUID = -4992903649621898395L;

	@Id
	private long id;

	@Basic(fetch = FetchType.LAZY)
	@Column(name = "content")
	private Blob content;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Blob getContent() {
		return content;
	}

	public void setContent(Blob content) {
		this.content = content;
	}
}
