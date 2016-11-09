package biz.ostw.ee.vfs;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.IdClass;

/**
 * @author mathter
 */
@Entity
@DiscriminatorValue( value = "1" )
@IdClass( VfsPathId.class )
public class VfsDir extends VfsPath
{
    private static final long serialVersionUID = -7816010045654580048L;
}
