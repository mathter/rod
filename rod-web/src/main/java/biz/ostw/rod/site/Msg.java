package biz.ostw.rod.site;

import java.util.Enumeration;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

import biz.ostw.rod.jsf.UTF8Control;

/**
 * @author mathter
 */
public class Msg extends ResourceBundle
{
    protected static final String BUNDLE_NAME = "biz.ostw.rod.site.messages";

    protected static final String BUNDLE_EXTENSION = "properties";

    protected static final Control UTF8_CONTROL = new UTF8Control();

    public Msg()
    {
        this.setParent(
            ResourceBundle.getBundle( BUNDLE_NAME, FacesContext.getCurrentInstance().getViewRoot().getLocale(), UTF8_CONTROL ) );
    }

    @Override
    protected Object handleGetObject( String key )
    {
        return parent.getObject( key );
    }

    @Override
    public Enumeration getKeys()
    {
        return parent.getKeys();
    }
}
