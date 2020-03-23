package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    @Column(nullable = false, unique = true, length = 32)
    @NotNull
    @Size(max = 32)
    private String name;
    @Column(nullable = false, length = 128)
    @NotNull
    @Size(max = 128)
    private String description;

    @OneToMany(mappedBy = "parentCategoryEntity")
    private List<Category> subCategoryEntities;
    @ManyToOne
    private Category parentCategoryEntity;

    @OneToMany(mappedBy = "category")
    private List<Product> products;
    
    @OneToMany(mappedBy = "category")
    private List<Service> services;

    public Category() {
        subCategoryEntities = new ArrayList<>();
        products = new ArrayList<>();
        services = new ArrayList<>();
    }

    public Category(String name, String description) {
        this();

        this.name = name;
        this.description = description;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (categoryId != null ? categoryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the categoryId fields are not set
        if (!(object instanceof Category)) {
            return false;
        }
        Category other = (Category) object;
        if ((this.categoryId == null && other.categoryId != null) || (this.categoryId != null && !this.categoryId.equals(other.categoryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Category[ id=" + categoryId + " ]";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the products
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     * @param products the products to set
     */
    public void setProducts(List<Product> products) {
        this.products = products;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the subCategoryEntities
     */
    public List<Category> getSubCategoryEntities() {
        return subCategoryEntities;
    }

    /**
     * @param subCategoryEntities the subCategoryEntities to set
     */
    public void setSubCategoryEntities(List<Category> subCategoryEntities) {
        this.subCategoryEntities = subCategoryEntities;
    }

    /**
     * @return the parentCategoryEntity
     */
    public Category getParentCategoryEntity() {
        return parentCategoryEntity;
    }

    /**
     * @param parentCategoryEntity the parentCategoryEntity to set
     */
    public void setParentCategoryEntity(Category parentCategoryEntity) {
        if (this.parentCategoryEntity != null) {
            if (this.parentCategoryEntity.getSubCategoryEntities().contains(this)) {
                this.parentCategoryEntity.getSubCategoryEntities().remove(this);
            }
        }

        this.parentCategoryEntity = parentCategoryEntity;

        if (this.parentCategoryEntity != null) {
            if (!this.parentCategoryEntity.getSubCategoryEntities().contains(this)) {
                this.parentCategoryEntity.getSubCategoryEntities().add(this);
            }
        }
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

}
