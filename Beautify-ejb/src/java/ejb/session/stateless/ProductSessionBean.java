package ejb.session.stateless;

import entity.Category;
import entity.Product;
import entity.ServiceProvider;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.CategoryNotFoundException;
import util.exception.CreateNewProductException;
import util.exception.DeleteProductException;
import util.exception.InputDataValidationException;
import util.exception.ProductExistException;
import util.exception.ProductNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateProductException;

@Stateless
@Local(ProductSessionBeanLocal.class)

public class ProductSessionBean implements ProductSessionBeanLocal {

    @EJB
    private CategorySessionBeanLocal categorySessionBeanLocal;

    @PersistenceContext(unitName = "Beautify-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public ProductSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    public Product createNewProduct(Product newProduct, Long categoryId, Long serviceProviderId) throws ProductExistException, UnknownPersistenceException, InputDataValidationException, CreateNewProductException {

        Set<ConstraintViolation<Product>> constraintViolations = validator.validate(newProduct);

        if (constraintViolations.isEmpty()) {

            try {

                if (categoryId == null) {
                    throw new CreateNewProductException("The new product must be associated a leaf category");
                }

                Category category = categorySessionBeanLocal.retrieveCategoryByCategoryId(categoryId);

                if (!category.getSubCategoryEntities().isEmpty()) {
                    throw new CreateNewProductException("Selected category for the new product is not a leaf category");
                }

                newProduct.setCategory(category);
                category.getProducts().add(newProduct);

                ServiceProvider serviceProvider = em.find(ServiceProvider.class, serviceProviderId);

                newProduct.setServiceProvider(serviceProvider);
                serviceProvider.getProducts().add(newProduct);

                em.persist(newProduct);
                em.flush();

                return newProduct;
            } catch (PersistenceException ex) {
                if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                    if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                        throw new ProductExistException();
                    } else {
                        throw new UnknownPersistenceException(ex.getMessage());
                    }
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            } catch (CategoryNotFoundException ex) {
                throw new CreateNewProductException("An error has occurred while creating the new product: " + ex.getMessage());
            }
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }

    @Override
    public List<Product> retrieveAllProducts() {
        Query query = em.createQuery("SELECT p FROM Product p ORDER BY p.skuCode ASC");
        List<Product> products = query.getResultList();

        for (Product product : products) {
            product.getServiceProvider();
            product.getCategory();
        }

        return products;
    }

    @Override
    public List<Product> searchProductsByName(String searchString) {
        Query query = em.createQuery("SELECT p FROM Product p WHERE p.name LIKE :inSearchString ORDER BY p.skuCode ASC");
        query.setParameter("inSearchString", "%" + searchString + "%");
        List<Product> products = query.getResultList();

        for (Product product : products) {
            product.getCategory();
            product.getServiceProvider();
        }

        return products;
    }

    @Override
    public List<Product> filterProductsByCategory(Long categoryId) throws CategoryNotFoundException {
        List<Product> products = new ArrayList<>();
        Category category = categorySessionBeanLocal.retrieveCategoryByCategoryId(categoryId);

        if (category.getSubCategoryEntities().isEmpty()) {
            products = category.getProducts();
        } else {
            for (Category subCategory : category.getSubCategoryEntities()) {
                products.addAll(addSubCategoryProducts(subCategory));
            }
        }

        for (Product product : products) {
            product.getCategory();
            product.getServiceProvider();
        }

        Collections.sort(products, new Comparator<Product>() {
            public int compare(Product pe1, Product pe2) {
                return pe1.getSkuCode().compareTo(pe2.getSkuCode());
            }
        });

        return products;
    }

    public Product retrieveProductByProdId(Long productId) throws ProductNotFoundException {
        Product product = em.find(Product.class, productId);

        if (product != null) {
            product.getCategory();
            product.getServiceProvider();

            return product;
        } else {
            throw new ProductNotFoundException("Product ID " + productId + " does not exist!");
        }
    }

    @Override
    public Product retrieveProductByProductSkuCode(String skuCode) throws ProductNotFoundException {
        Query query = em.createQuery("SELECT p FROM ProductEntity p WHERE p.skuCode = :inSkuCode");
        query.setParameter("inSkuCode", skuCode);

        try {
            Product productEntity = (Product) query.getSingleResult();
            productEntity.getCategory();
            productEntity.getServiceProvider();

            return productEntity;
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new ProductNotFoundException("Sku Code " + skuCode + " does not exist!");
        }
    }

    @Override
    public void updateProduct(Product productEntity, Long categoryId /*List<Long> tagIds*/) throws ProductNotFoundException, CategoryNotFoundException, /*TagNotFoundException,*/ UpdateProductException, InputDataValidationException {
        if (productEntity != null && productEntity.getProductId() != null) {
            Set<ConstraintViolation<Product>> constraintViolations = validator.validate(productEntity);

            if (constraintViolations.isEmpty()) {
                Product productToUpdate = retrieveProductByProdId(productEntity.getProductId());

                if (productToUpdate.getSkuCode().equals(productEntity.getSkuCode())) {
                    // Added in v5.1
                    if (categoryId != null && (!productToUpdate.getCategory().getCategoryId().equals(categoryId))) {
                        Category categoryEntityToUpdate = categorySessionBeanLocal.retrieveCategoryByCategoryId(categoryId);

                        if (!categoryEntityToUpdate.getSubCategoryEntities().isEmpty()) {
                            throw new UpdateProductException("Selected category for the new product is not a leaf category");
                        }

                        productToUpdate.setCategory(categoryEntityToUpdate);
                    }

                    /*
                    // Added in v5.1
                    if (tagIds != null) {
                        for (TagEntity tagEntity : productEntityToUpdate.getTagEntities()) {
                            tagEntity.getProductEntities().remove(productEntityToUpdate);
                        }

                        productEntityToUpdate.getTagEntities().clear();

                        for (Long tagId : tagIds) {
                            TagEntity tagEntity = tagEntitySessionBeanLocal.retrieveTagByTagId(tagId);
                            productEntityToUpdate.addTag(tagEntity);
                        }
                    }
                     */
                    productToUpdate.setDescription(productEntity.getDescription());
                    productToUpdate.setName(productEntity.getName());
                    productToUpdate.setPhoto(productEntity.getPhoto());
                    productToUpdate.setPrice(productEntity.getPrice());
                    // Removed in v5.0
                    // productEntityToUpdate.setCategory(productEntity.getCategory());
                    // Added in v5.1
                    //productEntityToUpdate.setProductRating((productEntity.getProductRating()));
                } else {
                    throw new UpdateProductException("SKU Code of product record to be updated does not match the existing record");
                }
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } else {
            throw new ProductNotFoundException("Product ID not provided for product to be updated");
        }
    }

    @Override
    public void deleteProduct(Long productId) throws ProductNotFoundException, DeleteProductException {
        Product productEntityToRemove = retrieveProductByProdId(productId);

        //List<SaleTransactionLineItemEntity> saleTransactionLineItemEntities = saleTransactionEntitySessionBeanLocal.retrieveSaleTransactionLineItemsByProductId(productId);
        //if (saleTransactionLineItemEntities.isEmpty()) {
        productEntityToRemove.getCategory().getProducts().remove(productEntityToRemove);

        /*
            for (TagEntity tagEntity : productEntityToRemove.getTagEntities()) {
                tagEntity.getProductEntities().remove(productEntityToRemove);
            }

            productEntityToRemove.getTagEntities().clear();
         */
        em.remove(productEntityToRemove);
        //} else {
        throw new DeleteProductException("Product ID " + productId + " is associated with existing sale transaction line item(s) and cannot be deleted!");
        //}
    }

    private List<Product> addSubCategoryProducts(Category categoryEntity) {
        List<Product> productEntities = new ArrayList<>();

        if (categoryEntity.getSubCategoryEntities().isEmpty()) {
            return categoryEntity.getProducts();
        } else {
            for (Category subCategoryEntity : categoryEntity.getSubCategoryEntities()) {
                productEntities.addAll(addSubCategoryProducts(subCategoryEntity));
            }

            return productEntities;
        }
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Product>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }
}