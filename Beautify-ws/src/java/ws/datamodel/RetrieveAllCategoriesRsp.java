package ws.datamodel;

import entity.Category;
import java.util.List;



public class RetrieveAllCategoriesRsp
{
    private List<Category> categories;

    
    
    public RetrieveAllCategoriesRsp()
    {
    }
    
    
    
    public RetrieveAllCategoriesRsp(List<Category> categories)
    {
        this.categories = categories;
    }

    
    
    public List<Category> getCategoryEntities() {
        return categories;
    }

    public void setCategoryEntities(List<Category> categories) {
        this.categories = categories;
    }
}