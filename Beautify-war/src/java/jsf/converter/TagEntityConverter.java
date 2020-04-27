package jsf.converter;

import entity.Tag;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;



@FacesConverter(value = "tagEntityConverter", forClass = Tag.class)

public class TagEntityConverter implements Converter 
{
    public Object getAsObject(FacesContext context, UIComponent component, String value)
    {
        if (value == null || value.length() == 0 || value.equals("null")) 
        {
            return null;
        }

        try
        {            
            Long objLong = Long.parseLong(value);
            
            List<Tag> tagEntities = (List<Tag>)context.getExternalContext().getSessionMap().get("TagEntityConverter_tagEntities");
            
            for(Tag tagEntity:tagEntities)
            {
                if(tagEntity.getTagId().equals(objLong))
                {
                    return tagEntity;
                }
            }
        }
        catch(Exception ex)
        {
            throw new IllegalArgumentException("Please select a valid value");
        }
        
        return null;
    }

    
    
    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
        if (value == null) 
        {
            return "";
        }
        
        if (value instanceof String)
        {
            return "";
        }

        
        
        if (value instanceof Tag)
        {            
            Tag tagEntity = (Tag) value;                        
            
            try
            {
                return tagEntity.getTagId().toString();
            }
            catch(Exception ex)
            {
                throw new IllegalArgumentException("Invalid value");
            }
        }
        else 
        {
            throw new IllegalArgumentException("Invalid value");
        }
    }
}
