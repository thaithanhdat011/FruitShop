/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import connection.DbCon;
import dao.OrderDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.*;

/**
 *
 * @author DELL
 */
public class OrderNowServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();

            User auth = (User) request.getSession().getAttribute("auth");

            if (auth != null) {
                String productId = request.getParameter("id");
                int productQuantity = Integer.parseInt(request.getParameter("quantity"));
                if (productQuantity <= 0) {
                    productQuantity = 1;
                }
                Order orderModel = new Order();
                orderModel.setId(Integer.parseInt(productId));
                orderModel.setUid(auth.getId());
                orderModel.setQuantity(productQuantity);
                orderModel.setDate(formatter.format(date));

                OrderDao orderDao = new OrderDao(DbCon.getConnection());
                boolean result = orderDao.insertOrder(orderModel);
                if (result) {
                    ArrayList<Cart> cart_list = (ArrayList<Cart>) request.getSession().getAttribute("cart-list");
                    if (cart_list != null) {
                        for (Cart c : cart_list) {
                            if (c.getId() == Integer.parseInt(productId)) {
                                cart_list.remove(cart_list.indexOf(c));
                                break;
                            }
                        }
                    }
                    response.sendRedirect("orders.jsp");
                } else {
                    out.println("order failed");
                }
            } else {
                response.sendRedirect("login.jsp");
            }

        } catch (ClassNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
