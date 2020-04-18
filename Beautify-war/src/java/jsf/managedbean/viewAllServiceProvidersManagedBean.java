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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import util.exception.ServiceProviderNotFoundException;

/**
 *
 * @author fooyo
 */
@Named(value = "viewAllServiceProvidersManagedBean")
@ViewScoped
public class viewAllServiceProvidersManagedBean implements Serializable {

    @EJB
    private ServiceProviderSessionBeanLocal serviceProviderSessionBean;

    private String searchString;
    private List<ServiceProvider> serviceProviders;

    public viewAllServiceProvidersManagedBean() {
        searchString = ""; 
    }

    @PostConstruct
    public void postConstruct() {

        setServiceProviders(serviceProviderSessionBean.retrieveAllServiceProviders());
        /*if (searchString == "" || searchString.trim().length() == 0) {
            serviceProviders = serviceProviderSessionBean.retrieveAllServiceProviders();
        } else {
            serviceProviders = serviceProviderSessionBean.searchServiceProviderByName(searchString);
        }
        */
        
    }

    public void searchServiceProvider() {
        if (searchString == "" || searchString.trim().length() == 0) {
            serviceProviders = serviceProviderSessionBean.retrieveAllServiceProviders();
        } else {
            serviceProviders = serviceProviderSessionBean.searchServiceProviderByName(searchString);
        }
    }

    public void clickLink(Long serviceProviderId) throws IOException {
        try {
            ServiceProvider serviceProvider = serviceProviderSessionBean.retrieveServiceProviderById(serviceProviderId);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("serviceProvider", serviceProvider);
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/customerOperations/serviceProviderProfile.xhtml");
        } catch (ServiceProviderNotFoundException ex) {
            Logger.getLogger(viewAllServiceProvidersManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("ProviderSearchString", searchString);
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
