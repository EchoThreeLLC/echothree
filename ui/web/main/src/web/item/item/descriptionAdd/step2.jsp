<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>

<!--                                                                                  -->
<!-- Copyright 2002-2023 Echo Three, LLC                                              -->
<!--                                                                                  -->
<!-- Licensed under the Apache License, Version 2.0 (the "License");                  -->
<!-- you may not use this file except in compliance with the License.                 -->
<!-- You may obtain a copy of the License at                                          -->
<!--                                                                                  -->
<!--     http://www.apache.org/licenses/LICENSE-2.0                                   -->
<!--                                                                                  -->
<!-- Unless required by applicable law or agreed to in writing, software              -->
<!-- distributed under the License is distributed on an "AS IS" BASIS,                -->
<!-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.         -->
<!-- See the License for the specific language governing permissions and              -->
<!-- limitations under the License.                                                   -->
<!--                                                                                  -->

<%@ include file="../../../include/taglibs.jsp" %>

<html:html xhtml="true">
    <head>
        <title>Descriptions (<c:out value="${item.itemName}" />)</title>
        <html:base/>
        <%@ include file="../../../include/environment.jsp" %>
        <c:if test='${itemDescriptionType.mimeTypeUsageType.mimeTypeUsageTypeName == "TEXT"}'>
            <%@ include file="../descriptionTinyMce.jsp" %>
            <c:set var="onLoad" value='onLoad="pageLoaded()"' />
        </c:if>
    </head>
    <body ${onLoad}>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />">Home</a> &gt;&gt;
                <a href="<c:url value="/action/Item/Main" />">Items</a> &gt;&gt;
                <a href="<c:url value="/action/Item/Item/Main" />">Search</a> &gt;&gt;
                <et:countItemResults searchTypeName="ITEM_MAINTENANCE" countVar="itemResultsCount" commandResultVar="countItemResultsCommandResult" logErrors="false" />
                <c:if test="${itemResultsCount > 0}">
                    <a href="<c:url value="/action/Item/Item/Result" />"><fmt:message key="navigation.results" /></a> &gt;&gt;
                </c:if>
                <c:url var="reviewUrl" value="/action/Item/Item/Review">
                    <c:param name="ItemName" value="${item.itemName}" />
                </c:url>
                <a href="${reviewUrl}">Review (<c:out value="${item.itemName}" />)</a> &gt;&gt;
                <c:url var="descriptionsUrl" value="../../../action/Item/Item/Description">
                    <c:param name="ItemName" value="${item.itemName}" />
                </c:url>
                <a href="${descriptionsUrl}">Descriptions</a> &gt;&gt;
                Add
            </h2>
        </div>
        <div id="Content">
            <p><font size="+2"><b><c:out value="${item.description}" /></b></font></p>
            <p><font size="+1">${item.itemName}</font></p>
            <br />
            Item Description Type: <c:out value="${itemDescriptionType.description}" /><br />
            <br />
            <et:executionErrors id="errorMessage">
                <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
            </et:executionErrors>
            <c:choose>
                <c:when test='${itemDescriptionType.mimeTypeUsageType == null}'>
                    <jsp:include page="step2StringDescription.jsp" />
                </c:when>
                <c:when test='${itemDescriptionType.mimeTypeUsageType.mimeTypeUsageTypeName == "TEXT"}'>
                    <jsp:include page="step2ClobDescription.jsp" />
                </c:when>
                <c:otherwise>
                    <jsp:include page="step2BlobDescription.jsp" />
                </c:otherwise>
            </c:choose>
        </div>
        <jsp:include page="../../../include/userSession.jsp" />
        <jsp:include page="../../../include/copyright.jsp" />
    </body>
</html:html>
