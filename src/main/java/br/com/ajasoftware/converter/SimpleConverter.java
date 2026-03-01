package br.com.ajasoftware.converter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.ajasoftware.repository.geral.RepositoryAll;

@Named("converter")
public class SimpleConverter implements Converter {
	@Inject
	private RepositoryAll repository;
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Class<?> entityType = component.getValueExpression("value").getType(context.getELContext());
        
        if(entityType.toString().contains("br.com.ajasoftware.model.conta.Conta")) {
        	return repository.porId(value!=null && value.length()>0?value:null, entityType); // find() is possible too
        }
        
        return repository.porId(value!=null && value.length()>0?new Integer(value):null, entityType); // find() is possible too
    }

	@Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value!=null) {
        	Object base = value;
        	Class<?> entityType = component.getValueExpression("value").getType(context.getELContext());
        	Method getId;
			try {
				getId = entityType.getMethod("getId");
				if(getId.invoke(base)!=null) {
					return getId.invoke(base).toString();
				}
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
        }
        return "";
    }   
}