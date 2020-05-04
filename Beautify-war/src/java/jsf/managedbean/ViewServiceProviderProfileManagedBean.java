/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.ServiceProviderSessionBeanLocal;
import entity.ServiceProvider;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author jilon
 */
@Named(value = "viewServiceProviderProfileManagedBean")
@ViewScoped
public class ViewServiceProviderProfileManagedBean implements Serializable {

    @EJB(name = "ServiceProviderSessionBeanLocal")
    private ServiceProviderSessionBeanLocal serviceProviderSessionBeanLocal;

    private ServiceProvider providerToView;
    private Date[] openingHours;
    private Date[] closingHours;
    private List<String> daysOfWeek;

    public ViewServiceProviderProfileManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        providerToView = (ServiceProvider) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("serviceProvider");

        this.openingHours = providerToView.getOpeningHours();
        this.closingHours = providerToView.getClosingHours();
        List<String> open = new ArrayList<>();
        List<String> close = new ArrayList<>();

        SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm");

        for (int i = 0; i < openingHours.length; i++) {
            if (openingHours[i] == null) {
                open.add("null");
            } else {
                String time = localDateFormat.format(openingHours[i]);
                open.add(time);
            }
        }
        for (int i = 0; i < closingHours.length; i++) {
            if (closingHours[i] == null) {
                close.add("null");
            } else {
                String time = localDateFormat.format(closingHours[i]);
                close.add(time);
            }
        }

        List<String> days = Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
        List<String> storeHours = new ArrayList<>();

        for (int i = 0; i < openingHours.length; i++) {
            if (open.get(i).equals("null")) {
                storeHours.add(days.get(i) + ": Closed");
            } else {
                storeHours.add(days.get(i) + ": " + open.get(i) + " to " + close.get(i));
            }
        }

        setDaysOfWeek(storeHours);
    }

    public ServiceProvider getProviderToView() {
        return providerToView;
    }

    public void setProviderToView(ServiceProvider providerToView) {
        this.providerToView = providerToView;
    }

    public TimeZone getTimeZone() {
        return TimeZone.getDefault();
    }

    public Date[] getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(Date[] openingHours) {
        this.openingHours = openingHours;
    }

    public Date[] getClosingHours() {
        return closingHours;
    }

    public void setClosingHours(Date[] closingHours) {
        this.closingHours = closingHours;
    }

    public List<String> getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(List<String> daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

}
