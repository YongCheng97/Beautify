/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.ServiceProviderSessionBeanLocal;
import entity.ServiceProvider;
import java.io.Serializable;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import static org.primefaces.behavior.validate.ClientValidator.PropertyKeys.event;
import util.exception.ServiceProviderNotFoundException;

/**
 *
 * @author jilon
 */
@Named(value = "viewServiceProviderProfileManagedBean")
@ViewScoped
public class ViewServiceProviderProfileManagedBean implements Serializable {

    @EJB(name = "ServiceProviderSessionBeanLocal")
    private ServiceProviderSessionBeanLocal serviceProviderSessionBeanLocal;

    private Long providerIdToView;
    private ServiceProvider providerToView;

    public ViewServiceProviderProfileManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        setProviderIdToView((Long) Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("serviceProviderId")));
        try {
            providerToView = serviceProviderSessionBeanLocal.retrieveServiceProviderById(getProviderIdToView());
        } catch (ServiceProviderNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while retrieving the service provider details: " + ex.getMessage(), null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    public ServiceProvider getProviderToView() {
        return providerToView;
    }

    public void setProviderToView(ServiceProvider providerToView) {
        this.providerToView = providerToView;
    }

    public Long getProviderIdToView() {
        return providerIdToView;
    }

    public void setProviderIdToView(Long providerIdToView) {
        this.providerIdToView = providerIdToView;
    }

    public TimeZone getTimeZone() {
        return TimeZone.getDefault();
    }

}
