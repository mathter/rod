package biz.ostw.rod.user;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import biz.ostw.persistence.SystemId;

/**
 * @author mathter
 */
@Entity( name = "User" )
@Table( name = "users" )
@Access( AccessType.FIELD )
@NamedQueries(
{
    @NamedQuery( name = "User_getByLogin", query = "select u from User u where u.login = :login" )
} )
@Cacheable
@Cache( usage = CacheConcurrencyStrategy.TRANSACTIONAL )
public class User implements SystemId< Long >, Serializable
{
    private static final long serialVersionUID = 8495070763544425473L;

    @Id
    @Column( name = "id" )
    @SequenceGenerator( name = "users_gen", sequenceName = "users_seq", initialValue = 1, allocationSize = 1 )
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "users_gen" )
    private long systemId;

    @Column( name = "login", nullable = false, unique = true, length = 64 )
    private String login;

    @Column( name = "password", nullable = false, length = 32 )
    private String password;

    @ManyToMany( fetch = FetchType.LAZY, targetEntity = Role.class, cascade =
    {
        CascadeType.REFRESH, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.MERGE
    } )
    @JoinTable( name = "user_role_map", joinColumns = @JoinColumn( name = "user_id" ),
        inverseJoinColumns = @JoinColumn( name = "role_id" ) )
    @Fetch( FetchMode.JOIN )
    private Set< Role > roles;

    @Embedded
    @Fetch( FetchMode.JOIN )
    private AccessInfo accessInfo = new AccessInfo();

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

    public String getLogin()
    {
        return this.login;
    }

    public void setLogin( String login )
    {
        this.login = login;
    }

    public String getPassword()
    {
        return this.password;
    }

    public void setPassword( String password )
    {
        this.password = password;
    }

    public Set< Role > getRoles()
    {
        return roles;
    }

    public void setRoles( Set< Role > roles )
    {
        this.roles = roles;
    }

    public AccessInfo getAccessInfo()
    {
        return this.accessInfo;
    }

    public void setAccessInfo( AccessInfo accessInfo )
    {
        this.accessInfo = accessInfo;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append( "User [systemId=" );
        builder.append( systemId );
        builder.append( ", login=" );
        builder.append( login );
        builder.append( ", password=" );
        builder.append( password );
        builder.append( ", roles=" );
        builder.append( roles );
        builder.append( ", accessInfo=" );
        builder.append( accessInfo );
        builder.append( "]" );
        return builder.toString();
    }
}
