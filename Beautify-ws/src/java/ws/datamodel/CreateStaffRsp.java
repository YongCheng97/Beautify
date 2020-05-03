package ws.datamodel;

public class CreateStaffRsp {
    
    private Long staffId;

    public CreateStaffRsp() {
    }

    public CreateStaffRsp(Long staffId) {
        this.staffId = staffId;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    
}
