package biz.ostw.rod.user;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Access( AccessType.FIELD )
public class AccessInfo implements Serializable
{
    private static final long serialVersionUID = 3806023040575496957L;

    @Column( name = "is_registered", nullable = false )
    private boolean isRegistered = false;

    public boolean isRegistered()
    {
        return this.isRegistered;
    }

    public void setRegistered( boolean isRegistered )
    {
        this.isRegistered = isRegistered;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append( "AccessInfo [isRegistered=" );
        builder.append( isRegistered );
        builder.append( "]" );
        return builder.toString();
    }
}
