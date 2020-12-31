<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>

<!--                                                                                  -->
<!-- Copyright 2002-2021 Echo Three, LLC                                              -->
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
        <title>Inventory Condition Use Types</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />">Home</a> &gt;&gt;
                <a href="<c:url value="/action/Inventory/Main" />">Inventory</a> &gt;&gt;
                Inventory Condition Use Types
            </h2>
        </div>
        <div id="Content">
            <display:table name="inventoryConditionUseTypes" id="inventoryConditionUseType" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:url var="reviewUrl" value="/action/Inventory/InventoryConditionUseType/Review">
                        <c:param name="InventoryConditionUseTypeName" value="${inventoryConditionUseType.inventoryConditionUseTypeName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${inventoryConditionUseType.inventoryConditionUseTypeName}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${inventoryConditionUseType.description}" />
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${inventoryConditionUseType.isDefault}">
                            Default
                        </c:when>
                    </c:choose>
                </display:column>
                <display:column>
                    <c:url var="inventoryConditionUsesUrl" value="/action/Inventory/InventoryConditionUse/Main">
                        <c:param name="InventoryConditionUseTypeName" value="${inventoryConditionUseType.inventoryConditionUseTypeName}" />
                    </c:url>
                    <a href="${inventoryConditionUsesUrl}">Conditions</a>
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
