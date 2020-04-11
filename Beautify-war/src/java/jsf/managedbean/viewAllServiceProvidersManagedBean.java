/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.ServiceProviderSessionBeanLocal;
import entity.ServiceProvider;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author fooyo
 */
@Named(value = "viewAllServiceProvidersManagedBean")
@ViewScoped
public class viewAllServiceProvidersManagedBean implements Serializable {

    @EJB
    private ServiceProviderSessionBeanLocal serviceProviderSessionBean;

    private List<ServiceProvider> serviceProviders;

    public viewAllServiceProvidersManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        setServiceProviders(serviceProviderSessionBean.retrieveAllServiceProviders());
    }

    public void clickLink(Long serviceProviderId) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/customerOperations/serviceProviderProfile.xhtml?serviceProviderId=" + serviceProviderId);
    }

    /**
     * @return the serviceProviders
     */
    public List<ServiceProvider> getServiceProviders() {
        return serviceProviders;
    }

    /**
     * @param serviceProviders the serviceProviders to set
     */
    public void setServiceProviders(List<ServiceProvider> serviceProviders) {
        this.serviceProviders = serviceProviders;
    }

    public TimeZone getTimeZone() {
        return TimeZone.getDefault();
    }

}
