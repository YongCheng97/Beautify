package ws.datamodel;

import entity.Staff;
import javax.annotation.PostConstruct;

public class CreateStaffReq {

    private Staff staff;

    public CreateStaffReq() {
    }

    public CreateStaffReq(Staff staff) {
        this.staff = staff;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }


}
