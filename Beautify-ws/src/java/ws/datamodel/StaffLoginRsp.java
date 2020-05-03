
package ws.datamodel;

import entity.Staff;

public class StaffLoginRsp {
    private Staff staff;

    public StaffLoginRsp() {
    }

    public StaffLoginRsp(Staff staff) {
        this.staff = staff;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    
    
}
