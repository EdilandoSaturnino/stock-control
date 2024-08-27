package main.java.com.stockcontrol.service;

import main.java.com.stockcontrol.dao.ProductDAO;
import main.java.com.stockcontrol.model.Product;

import java.sql.SQLException;
import java.util.List;

public class ProductService {
    private ProductDAO productDAO = new ProductDAO();

    public void addProduct(Product product) throws SQLException {
        productDAO.addProduct(product);
    }

    public void updateProduct(Product product) throws SQLException {
        productDAO.updateProduct(product);
    }

    public void deleteProduct(int id) throws SQLException {
        productDAO.deleteProduct(id);
    }

    public Product getProduct(int id) throws SQLException {
        return productDAO.getProduct(id);
    }

    public List<Product> getAllProducts() throws SQLException {
        return productDAO.getAllProducts();
    }

    public void generateStockReport() throws SQLException {
        List<Product> products = getAllProducts();
        System.out.println("Relatório de Estoque:");
        System.out.println("ID | Name | Price | Quantity");
        for (Product product : products) {
            System.out.printf("%d | %s | %.2f | %d%n",
                    product.getId(), product.getName(), product.getPrice(), product.getQuantity());
        }
    }
}