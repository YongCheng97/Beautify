package ws.datamodel;



public class UploadRsp 
{
    private String status;

    
    
    public UploadRsp() 
    {
    }

    
    
    public UploadRsp(String status) 
    {
        this.status = status;
    }
    

    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
