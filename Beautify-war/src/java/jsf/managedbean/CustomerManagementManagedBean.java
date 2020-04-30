package jsf.managedbean;

import ejb.session.stateless.BookingSessionBeanLocal;
import ejb.session.stateless.CustomerSessionBeanLocal;
import entity.Booking;
import entity.CreditCard;
import entity.Customer;
import entity.Product;
import entity.Service;
import java.io.File;
import java.io.FileOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.event.FileUploadEvent;
import util.exception.CustomerExistException;
import util.exception.CustomerNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateCustomerException;
import util.security.CryptographicHelper;

@Named(value = "customerManagementManagedBean")
@ViewScoped

public class CustomerManagementManagedBean implements Serializable {

    @EJB
    private CustomerSessionBeanLocal customerSessionBeanLocal;
    @EJB
    private BookingSessionBeanLocal bookingSessionBeanLocal;

    private Customer newCustomer;
    private Customer currentCustomer;

    //to update customer
    private Customer selectedCustomerEntityToUpdate;

    //to change password
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;

    //to get credit card
    private List<CreditCard> creditCards;

    //to get bookings
    private List<Booking> upcomingBookings;
    private List<Booking> completedBookings;

    //to get favourites
    private List<Service> favouriteServices;
    private List<Product> favouriteProducts;

    public CustomerManagementManagedBean() {
        newCustomer = new Customer();
    }

    @PostConstruct
    public void postConstruct() {
        currentCustomer = (Customer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentCustomerEntity");

        if (currentCustomer != null) {
            upcomingBookings = new ArrayList<>();
            completedBookings = new ArrayList<>();
            List<Booking> bookings = bookingSessionBeanLocal.retrieveAllBookingsByCustomerId(currentCustomer.getCustomerId());

            for (Booking booking : bookings) {
                if (!booking.getStatus().equals("Completed")) {
                    upcomingBookings.add(booking);
                } else {
                    completedBookings.add(booking);
                }
            }

            favouriteServices = currentCustomer.getFavouriteServices();
            favouriteProducts = currentCustomer.getFavouriteProducts();

            creditCards = currentCustomer.getCreditCards();
        }
    }

    public void viewService(ActionEvent event) throws IOException {
        Long serviceIdToView = (Long) event.getComponent().getAttributes().get("serviceId");
        FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("serviceIdToView", serviceIdToView);
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/customerOperations/viewServiceDetails.xhtml");
    }

    public void viewProduct(ActionEvent event) throws IOException {
        Long productIdToView = (Long) event.getComponent().getAttributes().get("productId");
        FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("productIdToView", productIdToView);
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/customerOperations/viewProductDetails.xhtml");
    }

    public void createNewCustomer(ActionEvent event) throws IOException {
        try {
            Long customerId = customerSessionBeanLocal.createNewCustomer(getNewCustomer());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Account successfully created!", null));
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/index.xhtml");
        } catch (InputDataValidationException | CustomerExistException | UnknownPersistenceException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating new account: " + ex.getMessage(), null));
        }
    }

    public void doUpdateCustomer(ActionEvent event) {
        selectedCustomerEntityToUpdate = currentCustomer;
    }

    public void updateCustomer(ActionEvent event) {
        try {
            customerSessionBeanLocal.updateCustomerDetails(selectedCustomerEntityToUpdate);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Profile updated successfully", null));
        } catch (UpdateCustomerException | CustomerNotFoundException | InputDataValidationException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating product: " + ex.getMessage(), null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    public void changePassword() {
        try {
            String passwordHash = CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing(oldPassword + currentCustomer.getSalt()));
            if (passwordHash.equals(currentCustomer.getPassword())) {
                if (newPassword.equals(confirmPassword)) {
                    Customer changePWCustomer = currentCustomer;
                    changePWCustomer.setPassword(newPassword);
                    customerSessionBeanLocal.updateCustomerDetails(changePWCustomer);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Password changed successfully", null));
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "New password does not match", null));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Old password is incorrect", null));
            }

        } catch (UpdateCustomerException | CustomerNotFoundException | InputDataValidationException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating product: " + ex.getMessage(), null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        try {
            String newFilePath = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("alternatedocroot_1") + System.getProperty("file.separator") + "Customer"+currentCustomer.getCustomerId()+".jpg";

            System.err.println("********** Demo03ManagedBean.handleFileUpload(): File name: " + event.getFile().getFileName());
            System.err.println("********** Demo03ManagedBean.handleFileUpload(): newFilePath: " + newFilePath);

            File file = new File(newFilePath);
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            int a;
            int BUFFER_SIZE = 8192;
            byte[] buffer = new byte[BUFFER_SIZE];

            InputStream inputStream = event.getFile().getInputstream();

            while (true) {
                a = inputStream.read(buffer);

                if (a < 0) {
                    break;
                }

                fileOutputStream.write(buffer, 0, a);
                fileOutputStream.flush();
            }

            fileOutputStream.close();
            inputStream.close();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "File uploaded successfully", ""));
        } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "File upload error: " + ex.getMessage(), ""));
        }
    }

    public Customer getNewCustomer() {
        return newCustomer;
    }

    public void setNewCustomer(Customer newCustomer) {
        this.newCustomer = newCustomer;
    }

    public Customer getCurrentCustomer() {
        return currentCustomer;
    }

    public void setCurrentCustomer(Customer currentCustomer) {
        this.currentCustomer = currentCustomer;
    }

    public Customer getSelectedCustomerEntityToUpdate() {
        return selectedCustomerEntityToUpdate;
    }

    public void setSelectedCustomerEntityToUpdate(Customer selectedCustomerEntityToUpdate) {
        this.selectedCustomerEntityToUpdate = selectedCustomerEntityToUpdate;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public List<Booking> getUpcomingBookings() {
        return upcomingBookings;
    }

    public void setUpcomingBookings(List<Booking> upcomingBookings) {
        this.upcomingBookings = upcomingBookings;
    }

    public List<Booking> getCompletedBookings() {
        return completedBookings;
    }

    public void setCompletedBookings(List<Booking> completedBookings) {
        this.completedBookings = completedBookings;
    }

    public List<Service> getFavouriteServices() {
        return favouriteServices;
    }

    public void setFavouriteServices(List<Service> favouriteServices) {
        this.favouriteServices = favouriteServices;
    }

    public List<Product> getFavouriteProducts() {
        return favouriteProducts;
    }

    public void setFavouriteProducts(List<Product> favouriteProducts) {
        this.favouriteProducts = favouriteProducts;
    }

    public List<CreditCard> getCreditCards() {
        return creditCards;
    }

    public void setCreditCards(List<CreditCard> creditCards) {
        this.creditCards = creditCards;
    }
}
