package controller;

import model.Category;
import model.Product;
import service.ProductServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ProductServlet", urlPatterns = "/product")
public class ProductServlet extends HttpServlet {
    private static final long serialVersionUID=1L;
    private ProductServiceImpl service= null;
    public void init(){
        service= new ProductServiceImpl();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String action= request.getParameter("action");
        if(action==null){
            action="";
        }
        switch (action){
            case "create":
                createProduct(request, response);
                break;
            case "edit":
                updateProduct(request, response);
                break;
            case "search":
                searchProduct(request, response);
                break;
            default:
                break;
        }

    }

    private void searchProduct(HttpServletRequest request, HttpServletResponse response) {
        String name= request.getParameter("search");
        List<Product> list= service.searchProduct(name);
        RequestDispatcher dispatcher= request.getRequestDispatcher("product/search.jsp");
        request.setAttribute("listProduct", list);
        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response) {
        String name= request.getParameter("name");
        double price= Double.parseDouble(request.getParameter("price"));
        int quantity= Integer.parseInt(request.getParameter("quantity"));
        String color= request.getParameter("color");
        String category= request.getParameter("category");
        int id= Integer.parseInt(request.getParameter("id"));
        Category category1= service.findCateByName(category);
        Product product= service.findProById((id));
        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setColor(color);
        product.setCategory(category1);
        service.updateProduct(product);
        RequestDispatcher dispatcher= request.getRequestDispatcher("product/edit.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createProduct(HttpServletRequest request, HttpServletResponse response) {
        String name= request.getParameter("name");
        double price= Double.parseDouble(request.getParameter("price"));
        int quantity= Integer.parseInt(request.getParameter("quantity"));
        String color= request.getParameter("color");
        int category= Integer.parseInt(request.getParameter("category"));
        Category category1= service.findCateById(category);
        Product product= new Product(name, price,quantity, color, category1);
        service.insertProduct(product);
        RequestDispatcher dispatcher= request.getRequestDispatcher("product/create.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String action= request.getParameter("action");
        if(action==null){
            action="";
        }
        switch (action){
            case "create":
                showCreateForm(request,response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteProduct(request, response);
                break;
            default:
                listProduct(request,response);
                break;
        }
    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response) {
        int id = Integer.parseInt(request.getParameter("id"));
        try {
            service.deleteProduct(id);

            List<Product> list= service.selectAllProduct();
            request.setAttribute("listProduct",list);
            RequestDispatcher dispatcher= request.getRequestDispatcher("product/list.jsp");
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) {
        int id= Integer.parseInt(request.getParameter("id"));
        Product product=service.selectProduct(id);
        RequestDispatcher dispatcher= request.getRequestDispatcher("product/edit.jsp");
        request.setAttribute("product", product);
        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showCreateForm(HttpServletRequest request, HttpServletResponse response) {
        RequestDispatcher dispatcher= request.getRequestDispatcher("product/create.jsp");
        List<Category> list= service.selectAllCate();
        request.setAttribute("listCategory", list);
        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listProduct(HttpServletRequest request, HttpServletResponse response) {
        List<Product> list= service.selectAllProduct();
        RequestDispatcher dispatcher= request.getRequestDispatcher("product/list.jsp");
        request.setAttribute("listProduct", list);
        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
