package main.java.com.stockcontrol;

import main.java.com.stockcontrol.model.Product;
import main.java.com.stockcontrol.model.User;
import main.java.com.stockcontrol.service.ProductService;
import main.java.com.stockcontrol.service.UserService;

import java.sql.SQLException;
import java.util.Scanner;

public class StockControl {
    private Scanner scanner = new Scanner(System.in);
    private ProductService productService = new ProductService();
    private UserService userService = new UserService();
    private User currentUser;

    public void start() {
        while (true) {
            if (currentUser == null) {
                if (!login()) {
                    continue;
                }
            }

            System.out.println("\n1. Adicionar produto");
            System.out.println("2. Atualizar produto");
            System.out.println("3. Deletar produto");
            System.out.println("4. Ver todos os produtos");
            System.out.println("5. Gerar relatório de estoque");
            System.out.println("6. Deslogar");
            System.out.println("7. Sair");
            System.out.print("Escolha uma opção: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); 

            try {
                switch (choice) {
                    case 1:
                        addProduct();
                        break;
                    case 2:
                        updateProduct();
                        break;
                    case 3:
                        deleteProduct();
                        break;
                    case 4:
                        viewAllProducts();
                        break;
                    case 5:
                        generateStockReport();
                        break;
                    case 6:
                        logout();
                        break;
                    case 7:
                        System.out.println("Saindo...");
                        return;
                    default:
                        System.out.println("Opção inválida");
                }
            } catch (SQLException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    private boolean login() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Senha: ");
        String password = scanner.nextLine();

        try {
            currentUser = userService.authenticateUser(username, password);
            if (currentUser != null) {
                System.out.println("Logado! Bem-vindo(a), " + currentUser.getUsername() + "!");
                return true;
            } else {
                System.out.println("Nome de usuário ou senha incorretos");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
            return false;
        }
    }

    private void logout() {
        currentUser = null;
        System.out.println("Desconectado!");
    }

    private void addProduct() throws SQLException {
        if (!currentUser.getRole().equals("ADMIN")) {
            System.out.println("Você não tem permiddão para adicionar produtos.");
            return;
        }

        System.out.print("Nome do produto: ");
        String name = scanner.nextLine();
        System.out.print("Preço do produto: ");
        double price = scanner.nextDouble();
        System.out.print("Quantidade do produto: ");
        int quantity = scanner.nextInt();

        Product product = new Product(0, name, price, quantity);
        productService.addProduct(product);
        System.out.println("Produto adicionado com sucesso.");
    }

    private void updateProduct() throws SQLException {
        if (!currentUser.getRole().equals("ADMIN")) {
            System.out.println("Você não tem permissão para atualizar produtos.");
            return;
        }

        System.out.print("Insira o ID do produto para atualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Product product = productService.getProduct(id);
        if (product == null) {
            System.out.println("Produto não encontrado.");
            return;
        }

        System.out.print("Insira o novo nome (ou pressione Enter para manter o atual): ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) {
            product.setName(name);
        }

        System.out.print("Insira o novo preço (ou -1 para manter o atual): ");
        double price = scanner.nextDouble();
        if (price != -1) {
            product.setPrice(price);
        }

        System.out.print("Insira a nova quantidade (ou -1 para manter a atual): ");
        int quantity = scanner.nextInt();
        if (quantity != -1) {
            product.setQuantity(quantity);
        }

        productService.updateProduct(product);
        System.out.println("Produto atualizado com sucesso.");
    }

    private void deleteProduct() throws SQLException {
        if (!currentUser.getRole().equals("ADMIN")) {
            System.out.println("Você não tem permissão para excluir produtos.");
            return;
        }

        System.out.print("Insira o ID do produto para excluir: ");
        int id = scanner.nextInt();
        
        productService.deleteProduct(id);
        System.out.println("Produto excluído com sucesso.");
            }

    private void viewAllProducts() throws SQLException {
        System.out.println("Todos os produtos:");
        for (Product product : productService.getAllProducts()) {
            System.out.println(product);
        }
    }

    private void generateStockReport() throws SQLException {
        productService.generateStockReport();
    }
}

