package biz.ostw.rod.site.user.profile;

import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import biz.ostw.rod.user.ConfirmRegistration;
import biz.ostw.rod.user.ConfirmRegistrationService;
import biz.ostw.rod.user.Role;
import biz.ostw.rod.user.User;
import biz.ostw.rod.user.UserRepository;

/**
 * Servlet implementation class ConfirmServlet
 */
@WebServlet( "/registration/confirm" )
public class ConfirmServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LoggerFactory.getLogger( ConfirmServlet.class );

    @EJB
    private ConfirmRegistrationService confirmRegistrationService;

    @EJB
    private UserRepository userRepository;

    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        String uuidParameter = request.getParameter( "uuid" );

        try
        {
            UUID uuid = UUID.fromString( uuidParameter );

            User user = this.confirmRegistrationService.confirm( uuid );
            Role role = this.userRepository.getRegisteredRole();

            user.setRoles( Collections.singleton( role ) );
            this.userRepository.put( user );

            response.sendRedirect( request.getContextPath() + "/account/account.xhtml" );
        } catch ( IllegalStateException e )
        {
            LOG.error( "Can't confirm '" + uuidParameter + "'!", e );

            response.sendRedirect( "registration/confirmerror.xhtml?uuid=" + uuidParameter );
        } catch ( Exception e )
        {
            LOG.error( "Can't confirm '" + uuidParameter + "'!", e );
        }
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        this.doPost( request, response );
    }
}
