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
        <title>Inventory Location Group Capacities</title>
        <html:base/>
        <%@ include file="../../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Warehouse/Main" />"><fmt:message key="navigation.warehouses" /></a> &gt;&gt;
                <a href="<c:url value="/action/Warehouse/Warehouse/Main" />"><fmt:message key="navigation.warehouses" /></a> &gt;&gt;
                <et:countWarehouseResults searchTypeName="EMPLOYEE" countVar="warehouseResultsCount" commandResultVar="countWarehouseResultsCommandResult" logErrors="false" />
                <c:if test="${warehouseResultsCount > 0}">
                    <a href="<c:url value="/action/Warehouse/Warehouse/Result" />"><fmt:message key="navigation.results" /></a> &gt;&gt;
                </c:if>
                <c:url var="inventoryLocationGroupsUrl" value="/action/Inventory/InventoryLocationGroup/Main">
                    <c:param name="WarehouseName" value="${inventoryLocationGroup.warehouse.warehouseName}" />
                </c:url>
                <a href="${inventoryLocationGroupsUrl}">Inventory Location Groups</a> &gt;&gt;
                <c:url var="inventoryLocationGroupCapacitiesUrl" value="/action/Inventory/InventoryLocationGroupCapacity/Main">
                    <c:param name="WarehouseName" value="${inventoryLocationGroup.warehouse.warehouseName}" />
                    <c:param name="InventoryLocationGroupName" value="${inventoryLocationGroup.inventoryLocationGroupName}" />
                </c:url>
                <a href="${inventoryLocationGroupCapacitiesUrl}">Capacities</a> &gt;&gt;
                Add
            </h2>
        </div>
        <div id="Content">
            Unit Of Measure Kind:<br /><br />
            <c:forEach items="${unitOfMeasureKinds}" var="unitOfMeasureKind">
                <c:url var="addUrl" value="/action/Inventory/InventoryLocationGroupCapacity/Add/Step2">
                    <c:param name="WarehouseName" value="${inventoryLocationGroup.warehouse.warehouseName}" />
                    <c:param name="InventoryLocationGroupName" value="${inventoryLocationGroup.inventoryLocationGroupName}" />
                    <c:param name="UnitOfMeasureKindName" value="${unitOfMeasureKind.unitOfMeasureKindName}" />
                </c:url>
                &nbsp;&nbsp;&nbsp;&nbsp;<a href="${addUrl}"><c:out value="${unitOfMeasureKind.description}" /></a><br />
             </c:forEach>
        </div>
        <jsp:include page="../../../include/userSession.jsp" />
        <jsp:include page="../../../include/copyright.jsp" />
    </body>
</html:html>
