package biz.ostw.rod.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import biz.ostw.ee.vfs.VfsDir;
import biz.ostw.ee.vfs.VfsPath;
import biz.ostw.ee.vfs.VfsService;

/**
 * Servlet implementation class TestConfig
 */
@WebServlet( "/TestVfs" )
public class TestAddFile extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    @EJB
    private VfsService vfsService;

    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        String name = request.getParameter( "name" );
        String type = request.getParameter( "type" );

        switch ( type )
        {
        case "dir":
            this.vfsService.createDir( null, name );
            break;

        case "file":
            this.vfsService.createFile( null, name );
            break;

        default:
        {
            List< VfsPath > paths = this.vfsService.getByParent( null );

            final PrintWriter w = response.getWriter();

            paths.stream().forEach( path -> {
                w.println( "------------------" );
                w.print( "is dir=" + ( path instanceof VfsDir ) + " " );
                w.println( path.getPath() );
            } );
        }
            break;
        }

    }

    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        this.doGet( request, response );
    }

}
