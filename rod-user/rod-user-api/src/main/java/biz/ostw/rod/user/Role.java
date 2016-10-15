package biz.ostw.rod.user;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import biz.ostw.persistence.SystemId;

/**
 * @author mathter
 */
@Entity( name = "Role" )
@Table( name = "user_roles" )
@Access( AccessType.FIELD )
@NamedQueries(
{
    @NamedQuery( name = "Role_getByName", query = "select r from Role r where name = :name" )
} )
public class Role implements SystemId< Long >, Serializable
{
    private static final long serialVersionUID = 4780338221380511382L;

    @Id
    @Column( name = "id" )
    @SequenceGenerator( name = "user_roles_gen", sequenceName = "user_roles_seq", initialValue = 1, allocationSize = 1 )
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "user_roles_gen" )
    private long systemId;

    @Column( name = "name", nullable = false, unique = true, length = 128 )
    private String name;

    @Override
    public Long getSystemId()
    {
        return this.systemId;
    }

    @Override
    public void setSystemId( Long id )
    {
        this.systemId = id;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    @Override
    public int hashCode()
    {
        return (int) this.systemId;
    }

    @Override
    public boolean equals( Object obj )
    {
        return this == obj || ( obj instanceof Role && this.systemId == ( (Role) obj ).getSystemId() );
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append( "RoleImpl [systemId=" );
        builder.append( systemId );
        builder.append( ", name=" );
        builder.append( name );
        builder.append( "]" );
        return builder.toString();
    }
}
