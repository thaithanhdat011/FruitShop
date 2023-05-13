<%-- 
    Document   : index
    Created on : Feb 26, 2023, 9:20:14 PM
    Author     : DELL
--%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Cart"%>
<%@page import="model.Cart"%>
<%@page import="model.Product"%>
<%@page import="java.util.List"%>
<%@page import="dao.ProductDao"%>
<%@page import="model.User"%>
<%@page import="connection.DbCon"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    User auth = (User) request.getSession().getAttribute("auth");

    if (auth != null) {
        request.setAttribute("auth", auth);
    }
    ProductDao pd = new ProductDao(DbCon.getConnection());
    List<Product> products = pd.getAllProducts();

    ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list");
    List<Cart> cartProduct = null;
    if (cart_list != null) {
        ProductDao pDao = new ProductDao(DbCon.getConnection());
        cartProduct = pDao.getCartProducts(cart_list);
        request.setAttribute("cart_list", cart_list);
    }%>
<html>
    <head>
        <title>Welcome Page</title>
        <%@include file="includes/head.jsp" %>
    </head>
    <body>
        <%@include file="includes/navbar.jsp" %>


        <div class="container">
            <div class="card-header my-3">All Products</div>
            <div class="row">
                <c:forEach var="p" items="${listS}" varStatus="status">
                    <c:if test="${status.index >= startIndex && status.index < endIndex}">



                        <div class="col-md-3 my-3">
                            <div class="card w-100">
                                <img class="card-img-top" src="product-images/${p.image}"
                                     alt="Card image cap">
                                <div class="card-body">
                                    <h5 class="card-title">${p.name}</h5>
                                    <h6 class="price">Price: ${p.price}</h6>
                                    <h6 class="category">Category: ${p.category}></h6>
                                    <div class="mt-3 d-flex justify-content-between">
                                        <a class="btn btn-dark" href="add-to-cart?id=${p.id}">Add to Cart</a> <a
                                            class="btn btn-primary" href="order-now?quantity=1&id=${p.id}">Buy Now</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:if>
                </c:forEach>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="product-pagination text-center">
                    <nav>
                        <ul class="pagination">
                            <c:if test="${currentPage > 1}">
                                <li>
                                    <a href="?page=${currentPage-1}" aria-label="Previous">
                                        <span aria-hidden="true">&laquo;</span>
                                    </a>
                                </li>
                            </c:if>
                            <c:forEach var="i" begin="1" end="${totalPages}">
                                <c:choose>
                                    <c:when test="${currentPage eq i}">
                                        <li class="active"><a href="?page=${i}">${i}</a></li>
                                        </c:when>
                                        <c:otherwise>
                                        <li><a href="?page=${i}">${i}</a></li>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                                <c:if test="${currentPage < totalPages}">
                                <li>
                                    <a href="?page=${currentPage+1}" aria-label="Next">
                                        <span aria-hidden="true">&raquo;</span>
                                    </a>
                                </li>
                            </c:if>
                        </ul>
                    </nav>
                </div>
            </div>
        </div>



        <%@include file="includes/footer.jsp" %>

    </body><!-- comment -->
</html>

