/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.ServiceProviderSessionBeanLocal;
import ejb.session.stateless.TagsSessionBeanLocal;
import entity.ServiceProvider;
import entity.Tag;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
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
    private TagsSessionBeanLocal tagsSessionBean;

    @EJB
    private ServiceProviderSessionBeanLocal serviceProviderSessionBean;

    private String searchString;
    private List<ServiceProvider> serviceProviders;

    private List<Tag> tags;
    private List<SelectItem> selectItems;
    private List<Long> selectedTagIds;
    private String condition;

    public viewAllServiceProvidersManagedBean() {
        condition = "OR";
    }

    @PostConstruct
    public void postConstruct() {

        tags = tagsSessionBean.retrieveAllTags();
        setSelectItems(new ArrayList<>());

        for (Tag tag : tags) {
            getSelectItems().add(new SelectItem(tag.getTagId(), tag.getName(), tag.getName()));
        }

        setCondition((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("filterCondition"));
        setSelectedTagIds((List<Long>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("filterTags"));

        filterServiceProvider();

        searchString = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("providerSearchString");
        if (searchString == null || searchString.trim().length() == 0) {
            serviceProviders = serviceProviderSessionBean.retrieveAllServiceProviders();
        } else {
            serviceProviders = serviceProviderSessionBean.searchServiceProviderByName(searchString);
        }

    }

    public void filterServiceProvider() {
        System.out.println("********** viewAllServiceProvidersManagedBean: 1");
        if (getSelectedTagIds() != null && getSelectedTagIds().size() > 0) {
            System.out.println("********** viewAllServiceProvidersManagedBean: 2");
            serviceProviders = serviceProviderSessionBean.filterServiceProviderByTags(selectedTagIds, condition);
        } else {
            System.out.println("********** viewAllServiceProvidersManagedBean: 3");
            serviceProviders = serviceProviderSessionBean.retrieveAllServiceProviders();
        }
    }

    public void searchServiceProvider() {
        if (searchString == null || searchString.trim().length() == 0) {
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

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("providerSearchString", searchString);
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

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<SelectItem> getSelectItems() {
        return selectItems;
    }

    public void setSelectItems(List<SelectItem> selectItems) {
        this.selectItems = selectItems;
    }

    public List<Long> getSelectedTagIds() {
        return selectedTagIds;
    }

    public void setSelectedTagIds(List<Long> selectedTagIds) {
        this.selectedTagIds = selectedTagIds;

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("filterTags", selectedTagIds);
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("filterCondition", condition);
    }

}
