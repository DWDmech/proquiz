<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>

<c:url value="/registry" var="registry"/>

<div class="col-xl-7 col-lg-12 col-md-12 col-sm-12 left">
    <div class="text">
      <span>Система Proquiz для опитування</span>
      <p>Не слід, однак забувати, що консультація з широким активом <br>дозволяє виконувати важливі завдання по розробці істотних <br>фінансових і адміністративних умов</p>
    </div>
    <div class="btns">
      <button type="button" class="btn btn-registry col-xl-10 col-lg-12 col-md-12 col-sm-12" onclick="window.location.href = '${registry}'">Зареєструватися</button>
    </div>
</div>

<div class="col-xl-5 col-lg-12 col-md-12 col-sm-12 right">
  <img src="img/pages.png" alt="pages">
</div>