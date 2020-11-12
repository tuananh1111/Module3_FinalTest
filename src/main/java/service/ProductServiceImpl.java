package service;

import model.Category;
import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class  ProductServiceImpl  implements  PService{
    public static String jdbcURL="jdbc:mysql://localhost:3306/test?useSSL=false";
    public static String jdbcUsername="root";
    public static String jdbcPassword="0964068256";
    public static final String SEARCH="{call searchProduct(?)}";


    public static Connection getConnect(){
        Connection connection= null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection= DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }
    public Category findCateById(int id){
        Category category= null;
        Connection connection= getConnect();
        PreparedStatement preparedStatement= null;
        try {
            preparedStatement = connection.prepareStatement("select * from category where cate_id=?;");
            preparedStatement.setInt(1, id);
            ResultSet resultSet= preparedStatement.executeQuery();
            while (resultSet.next()){
                int ID= resultSet.getInt("cate_id");
                String name= resultSet.getString("cate_name");
                category= new Category(ID, name);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }return  category;
    }
    public List<Product> searchProduct(String name){
        List<Product> list= new ArrayList<>();
        Connection connection = getConnect();
        try {
            CallableStatement callableStatement= connection.prepareCall(SEARCH);
            callableStatement.setString(1, name);
            ResultSet resultSet= callableStatement.executeQuery();
            while (resultSet.next()){
                int id= resultSet.getInt("id");
                String nameproduct= resultSet.getString("nameproduct");
                double price = resultSet.getDouble("price");
                int quantity= resultSet.getInt("quantity");
                String color= resultSet.getString("color");
                int category= resultSet.getInt("category");
                Category category1= findCateById(category);
                list.add(new Product(id, nameproduct, price, quantity, color, category1));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }
    public Category findCateByName(String name){
        Category category= null;
        Connection connection= getConnect();
        PreparedStatement preparedStatement= null;
        try {
            preparedStatement = connection.prepareStatement("select * from category where cate_name=?;");
            preparedStatement.setString(1, name);
            ResultSet resultSet= preparedStatement.executeQuery();
            while (resultSet.next()){
                int ID= resultSet.getInt("cate_id");
                String name1= resultSet.getString("cate_name");
                category= new Category(ID, name1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }return  category;
    }
    @Override
    public List<Product> selectAllProduct() {
        List<Product> list= new ArrayList<>();
        Connection connection= getConnect();
        try {
            PreparedStatement preparedStatement= connection.prepareStatement("select * from product;");
            ResultSet resultSet= preparedStatement.executeQuery();
            while(resultSet.next()){
                int id= resultSet.getInt("id");
                String name= resultSet.getString("nameproduct");
                double price = resultSet.getDouble("price");
                int quantity= resultSet.getInt("quantity");
                String color= resultSet.getString("color");
                int category= resultSet.getInt("category");
                Category category1= findCateById(category);
                list.add(new Product(id, name, price, quantity, color, category1));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }
    public Product findProById(int id){
        Product product= null;
        Connection connection= getConnect();
        try {
            PreparedStatement preparedStatement= connection.prepareStatement("select * from product where id=?");
            preparedStatement.setInt(1,id);
            ResultSet resultSet= preparedStatement.executeQuery();
            while (resultSet.next()){
                int ID= resultSet.getInt("id");
                String name= resultSet.getString("nameproduct");
                double price = resultSet.getDouble("price");
                int quantity= resultSet.getInt("quantity");
                String color= resultSet.getString("color");
                int category= resultSet.getInt("category");
                Category category1= findCateById(category);
                product= new Product(ID, name, price, quantity, color, category1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }return product;
    }

    @Override
    public void insertProduct(Product product) {
        Connection connection= getConnect();
        try {
            PreparedStatement preparedStatement= connection.prepareStatement("insert into product values (?,?,?,?,?,?);");
            preparedStatement.setInt(1, product.getId());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setInt(4, product.getQuantity());
            preparedStatement.setString(5, product.getColor());
            preparedStatement.setInt(6, product.getCategory().getCateId());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
    public List<Category> selectAllCate(){
        List<Category> list= new ArrayList<>();
        Connection connection= getConnect();
        try {
            PreparedStatement preparedStatement= connection.prepareStatement("select * from category;");
            ResultSet resultSet= preparedStatement.executeQuery();
            while (resultSet.next()){
                int id= resultSet.getInt("cate_id");
                String name= resultSet.getString("cate_name");
                list.add(new Category(id, name));

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    @Override
    public Product selectProduct(int id) {
        Product product= null;
        Connection connection= getConnect();
        try {
            PreparedStatement preparedStatement= connection.prepareStatement("select * from product where id=?;");
            preparedStatement.setInt(1, id);
            ResultSet resultSet= preparedStatement.executeQuery();
            while(resultSet.next()){
                int ID= resultSet.getInt("id");
                String name= resultSet.getString("nameproduct");
                double price = resultSet.getDouble("price");
                int quantity= resultSet.getInt("quantity");
                String color= resultSet.getString("color");
                int category= resultSet.getInt("category");
                Category category1= findCateById(category);
                product=new Product(id, name, price, quantity, color, category1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return product;

    }

    @Override
    public void deleteProduct(int id) {
        Connection connection= getConnect();
        try {
            PreparedStatement preparedStatement= connection.prepareStatement("delete from product where id=?;");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void updateProduct(Product product) {
        Connection connection= getConnect();
        try {
            PreparedStatement preparedStatement= connection.prepareStatement("update product set nameproduct=?, price=?, quantity=?,color=?, category=? where id=?");
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2,product.getPrice());
            preparedStatement.setInt(3, product.getQuantity());
            preparedStatement.setString(4, product.getColor());
            preparedStatement.setInt(5, product.getCategory().getCateId());
            preparedStatement.setInt(6, product.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }
}
