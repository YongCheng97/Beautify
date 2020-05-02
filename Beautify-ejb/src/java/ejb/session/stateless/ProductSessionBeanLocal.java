/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Product;
import java.math.BigDecimal;
import java.util.List;
import util.exception.CategoryNotFoundException;
import util.exception.CreateNewProductException;
import util.exception.DeleteProductException;
import util.exception.InputDataValidationException;
import util.exception.ProductExistException;
import util.exception.ProductNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateProductException;

public interface ProductSessionBeanLocal {

    public List<Product> retrieveAllProducts();

    public Product createNewProduct(Product newProduct, Long categoryId, Long serviceProviderId, List<Long> tagIds) throws ProductExistException, UnknownPersistenceException, InputDataValidationException, CreateNewProductException;

    public List<Product> filterProductsByName(String searchString, Long categoryId);

    public List<Product> filterProductsByCategory(Long categoryId) throws CategoryNotFoundException;

    public Product retrieveProductByProductSkuCode(String skuCode) throws ProductNotFoundException;

    public void updateProduct(Product productEntity, Long categoryId) throws ProductNotFoundException, CategoryNotFoundException, UpdateProductException, InputDataValidationException;

    public void deleteProduct(Long productId) throws ProductNotFoundException, DeleteProductException;

    public List<Product> filterProductsByTags(List<Long> tagIds, String condition, Long categoryId);

    public Product retrieveProductByProdId(Long productId) throws ProductNotFoundException;

    public List<Product> retrieveAllProductsFromCategory(Long categoryId);

    public List<Product> filterProductsByMinimumPrice(BigDecimal minPrice, Long categoryId);

    public List<Product> filterProductsByMaximumPrice(BigDecimal maxPrice, Long categoryId);

    public List<Product> retrieveAllProductsByServiceProvider(Long serviceProviderId);

}
