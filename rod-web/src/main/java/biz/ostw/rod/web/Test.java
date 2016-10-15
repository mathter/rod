package biz.ostw.rod.web;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import biz.ostw.persistence.SelectClause;
import biz.ostw.rod.user.QRole;
import biz.ostw.rod.user.QUser;
import biz.ostw.rod.user.Role;
import biz.ostw.rod.user.User;
import biz.ostw.rod.user.UserRepository;
import biz.ostw.rod.user.channel.ChannelService;

/**
 * @author mathter
 */
@WebServlet( urlPatterns = "/Test" )
public class Test extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LoggerFactory.getLogger( Test.class );

    private static MessageDigest MD;

    @EJB
    private UserRepository userRepository;

    @EJB
    private ChannelService channelService;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        this.doPost( request, response );
    }

    private String createPasswordHash( String password )
    {
        byte[] bytes = password.getBytes( Charset.forName( "UTF-8" ) );
        bytes = MD.digest( bytes );

        return Hex.encodeHexString( bytes );
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        LOG.info( "Hi XXX" );

        String name = request.getParameter( "name" );
        String id = request.getParameter( "id" );

        if ( name != null )
        {
            User user = new User();
            user.setLogin( name );
            String password = "Hello";

            user.setPassword( createPasswordHash( "password" ) );

            Role role = null;

            SelectClause< Role > roleSelectClause = this.userRepository.from( QRole.role );
            roleSelectClause.where( QRole.role.name.eq( "role2" ) );

            if ( ( role = this.userRepository.get( roleSelectClause ).stream().findFirst().orElse( null ) ) == null )
            {
                role = new Role();
                role.setName( "role2" );
            }

            user.setRoles( Collections.singleton( role ) );
            this.userRepository.put( user );

        } else
        {
            Long i = 0L;

            if ( id != null )
            {
                i = Long.parseLong( id );
            }

            SelectClause< User > sc = this.userRepository.from( QUser.user );
            sc.where( QUser.user.systemId.eq( i ) );

            List< User > u = this.userRepository.get( sc );

            response.getWriter().print( u );
        }

        response.getWriter().println( this.channelService.geChannelTypes() );
    }

    static
    {
        try
        {
            MD = MessageDigest.getInstance( "MD5" );
        } catch ( Exception e )
        {
            throw new Error( e );
        }
    }
}
