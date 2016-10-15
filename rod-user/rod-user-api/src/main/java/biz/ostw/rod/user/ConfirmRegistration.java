package biz.ostw.rod.user;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.AccessType;
import javax.persistence.Access;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author mathter
 */
@Entity
@Table( name = "user_confirm_registration" )
@Access( AccessType.FIELD )
@NamedQueries(
{
    @NamedQuery( name = "ConfirmRegistration_remove", query = "delete from ConfirmRegistration where uuid = :uuid" ),
} )
public class ConfirmRegistration implements Serializable
{
    private static final long serialVersionUID = 8604632482320748255L;

    @Id
    @Column( name = "uuid" )
    private UUID uuid;

    @Column( name = "date", nullable = false )
    @Temporal( TemporalType.TIMESTAMP )
    private Date date;

    @ManyToOne( fetch = FetchType.EAGER )
    @JoinColumn( name = "user_id", referencedColumnName = "id", nullable = false )
    private User user;

    private boolean isComplete;

    public UUID getUuid()
    {
        return uuid;
    }

    public void setUuid( UUID uuid )
    {
        this.uuid = uuid;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate( Date date )
    {
        this.date = date;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser( User user )
    {
        this.user = user;
    }

    public boolean isComplete()
    {
        return isComplete;
    }

    public void setComplete( boolean isComplete )
    {
        this.isComplete = isComplete;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append( "ConfirmRegistration [uuid=" );
        builder.append( uuid );
        builder.append( ", date=" );
        builder.append( date );
        builder.append( ", user=" );
        builder.append( user );
        builder.append( ", isComplete=" );
        builder.append( isComplete );
        builder.append( "]" );
        return builder.toString();
    }
}
