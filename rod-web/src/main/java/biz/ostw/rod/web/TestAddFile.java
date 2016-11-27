package biz.ostw.rod.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

        VfsPath root = (VfsPath) this.vfsService.getByParent( null ).stream().findFirst().get();

        switch ( type )
        {
        case "dir":
            this.vfsService.createDir( root, name );
            break;

        case "file":
            this.vfsService.createFile( root, name );
            break;

        default:
        {
            PrintWriter w = response.getWriter();

            List< VfsPath > paths = this.vfsService.getByParent( null );
            LinkedList< List< VfsPath > > stack = new LinkedList<>();
            stack.push( null );

            do
            {
                stack.push( paths );
                
                for ( VfsPath path : paths )
                {
                    w.println( path.getPath() );
                }
            } while ( stack.size() > 0 );
        }
            break;
        }

    }

    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        this.doGet( request, response );
    }

}
