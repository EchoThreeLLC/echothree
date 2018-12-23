<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>

<!--                                                                                  -->
<!-- Copyright 2002-2019 Echo Three, LLC                                              -->
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

<%@ include file="../../include/taglibs.jsp" %>

<html:html xhtml="true">
    <head>
        <title>Aliases (<c:out value="${item.itemName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />">Home</a> &gt;&gt;
                <a href="<c:url value="/action/Item/Main" />">Items</a> &gt;&gt;
                <a href="<c:url value="/action/Item/Item/Main" />">Search</a> &gt;&gt;
                <et:countItemResults searchTypeName="ITEM_MAINTAINENCE" countVar="itemResultsCount" commandResultVar="countItemResultsCommandResult" logErrors="false" />
                <c:if test="${itemResultsCount > 0}">
                    <a href="<c:url value="/action/Item/Item/Result" />"><fmt:message key="navigation.results" /></a> &gt;&gt;
                </c:if>
                <c:url var="reviewUrl" value="/action/Item/Item/Review">
                    <c:param name="ItemName" value="${item.itemName}" />
                </c:url>
                <a href="${reviewUrl}">Review (<c:out value="${item.itemName}" />)</a> &gt;&gt;
                Aliases
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Item/ItemAlias/Add">
                <c:param name="ItemName" value="${item.itemName}" />
            </c:url>
            <p><a href="${addUrl}">Add Item Alias.</a></p>
            <display:table name="itemAliases" id="itemAlias" class="displaytag">
                <display:column titleKey="columnTitle.unitOfMeasureType">
                    <c:out value="${itemAlias.unitOfMeasureType.description}" />
                </display:column>
                <display:column titleKey="columnTitle.itemAliasType">
                    <c:out value="${itemAlias.itemAliasType.description}" />
                </display:column>
                <display:column property="alias" titleKey="columnTitle.alias" />
                <display:column>
                    <c:url var="editUrl" value="/action/Item/ItemAlias/Edit">
                        <c:param name="ItemName" value="${itemAlias.item.itemName}" />
                        <c:param name="OriginalAlias" value="${itemAlias.alias}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/Item/ItemAlias/Delete">
                        <c:param name="ItemName" value="${itemAlias.item.itemName}" />
                        <c:param name="Alias" value="${itemAlias.alias}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
