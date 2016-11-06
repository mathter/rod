package biz.ostw.ee.vfs;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author mathter
 */
@Entity
@DiscriminatorValue( value = "2" )
public class VfsFile extends VfsPath
{
    private static final long serialVersionUID = -7816010045654580048L;
}
