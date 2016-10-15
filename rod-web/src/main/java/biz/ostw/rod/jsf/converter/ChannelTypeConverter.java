package biz.ostw.rod.jsf.converter;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import biz.ostw.rod.user.channel.ChannelService;
import biz.ostw.rod.user.channel.ChannelType;

/**
 * @author mathter
 */
@ManagedBean
@RequestScoped
// @FacesConverter( value = "biz.ostw.rod.jsf.converterChannelTypeConverter", forClass = ChannelType.class )
public class ChannelTypeConverter implements Converter
{
    @EJB
    private ChannelService channelService;

    @Override
    public Object getAsObject( FacesContext facesContext, UIComponent component, String value )
    {
        return "".equals( value ) == false ? this.channelService.getChannelType( value ) : null;
    }

    @Override
    public String getAsString( FacesContext facesContext, UIComponent component, Object value )
    {
        return value != null ? ( (ChannelType) value ).getName() : "";
    }
}
