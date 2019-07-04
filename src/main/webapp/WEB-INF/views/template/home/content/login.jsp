<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:url var="loginURL" value="/login"/>

<div class="left-bg"></div>
<div class="col-xl-6 col-lg-12 col-md-12 col-sm-12 left">
  <form class="formBox mx-md-auto" action="${loginURL}" method="POST">
   <!-- 
    <input type="checkbox" name="remember-me" style="display: none" checked/>
   -->
    <h3 class="pl-0 mb-md-5 mx-md-auto">Будь ласка авторизуйтесь <br>щоб продовжити</h3>
    <div class="inputBox">
      <label class="labelText" for="email">Email:</label>
      <input type="email" name="email" class="userInput" required>
    </div>
    <div class="inputBox">
      <label class="labelText" for="password">Пароль:</label>
      <input type="password" name="password" class="userInput" required>
    </div>
    <input type="submit" value="Увійти">
  </form>
  <script type="text/javascript">
    $('.userInput').focus(function() {
      $(this).parent().addClass("focus");
    }).blur(function() {
      if($(this).val() === ''){
        $(this).parent().removeClass("focus");
      }
    });
   </script>
</div>

<div class="col-xl-6 col-lg-12 col-md-12 col-sm-12 right">
  <img src="img/pages.png" alt="pages">
</div>