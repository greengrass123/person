<%@ page language="java" contentType="text/xml; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<xml_api_reply version="1">
	<post unitname="${unitname}" pastename="${pastename}" requestcode="${requestcode}" count="${count}">
		 <c:forEach items="${adTypeList}" var="type">
           <type data="${type}"/>
        </c:forEach>  
	</post>
</xml_api_reply>