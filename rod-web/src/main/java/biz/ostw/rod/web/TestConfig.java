package biz.ostw.rod.web;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import biz.ostw.ee.config.ConfigService;

/**
 * Servlet implementation class TestConfig
 */
@WebServlet( "/TestConfig" )
public class TestConfig extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    @EJB
    private ConfigService configService;

    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        String name = request.getParameter( "name" );

        if ( name != null )
        {
            String value = request.getParameter( "value" );

            if ( value != null )
            {
            } else
            {
                Object object = this.configService.get( name, "NULL" );

                response.getWriter().write( String.valueOf( object ) );
            }
        }
    }

    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        this.doGet( request, response );
    }

}
