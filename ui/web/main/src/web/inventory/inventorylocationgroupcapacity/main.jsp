<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>

<!--                                                                                  -->
<!-- Copyright 2002-2022 Echo Three, LLC                                              -->
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
        <title>Inventory Location Group Capacities</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />">Home</a> &gt;&gt;
                <a href="<c:url value="/action/Warehouse/Main" />">Warehouses</a> &gt;&gt;
                <a href="<c:url value="/action/Warehouse/Warehouse/Main" />">Warehouses</a> &gt;&gt;
                <c:url var="inventoryLocationGroupsUrl" value="/action/Inventory/InventoryLocationGroup/Main">
                    <c:param name="WarehouseName" value="${inventoryLocationGroup.warehouse.warehouseName}" />
                </c:url>
                <a href="${inventoryLocationGroupsUrl}">Inventory Location Groups</a> &gt;&gt;
                Capacities
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Inventory/InventoryLocationGroupCapacity/Add/Step1">
                <c:param name="WarehouseName" value="${inventoryLocationGroup.warehouse.warehouseName}" />
                <c:param name="InventoryLocationGroupName" value="${inventoryLocationGroup.inventoryLocationGroupName}" />
            </c:url>
            <p><a href="${addUrl}">Add Capacity.</a></p>
            <display:table name="inventoryLocationGroupCapacities" id="inventoryLocationGroupCapacity" class="displaytag">
                <display:column titleKey="columnTitle.unitOfMeasureKind">
                    <c:out value="${inventoryLocationGroupCapacity.unitOfMeasureType.unitOfMeasureKind.description}" />
                </display:column>
                <display:column titleKey="columnTitle.unitOfMeasureType">
                    <c:out value="${inventoryLocationGroupCapacity.unitOfMeasureType.description}" />
                </display:column>
                <display:column titleKey="columnTitle.capacity">
                    <c:out value="${inventoryLocationGroupCapacity.capacity}" />
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/Inventory/InventoryLocationGroupCapacity/Edit">
                        <c:param name="WarehouseName" value="${inventoryLocationGroupCapacity.inventoryLocationGroup.warehouse.warehouseName}" />
                        <c:param name="InventoryLocationGroupName" value="${inventoryLocationGroupCapacity.inventoryLocationGroup.inventoryLocationGroupName}" />
                        <c:param name="UnitOfMeasureKindName" value="${inventoryLocationGroupCapacity.unitOfMeasureType.unitOfMeasureKind.unitOfMeasureKindName}" />
                        <c:param name="UnitOfMeasureTypeName" value="${inventoryLocationGroupCapacity.unitOfMeasureType.unitOfMeasureTypeName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/Inventory/InventoryLocationGroupCapacity/Delete">
                        <c:param name="WarehouseName" value="${inventoryLocationGroupCapacity.inventoryLocationGroup.warehouse.warehouseName}" />
                        <c:param name="InventoryLocationGroupName" value="${inventoryLocationGroupCapacity.inventoryLocationGroup.inventoryLocationGroupName}" />
                        <c:param name="UnitOfMeasureKindName" value="${inventoryLocationGroupCapacity.unitOfMeasureType.unitOfMeasureKind.unitOfMeasureKindName}" />
                        <c:param name="UnitOfMeasureTypeName" value="${inventoryLocationGroupCapacity.unitOfMeasureType.unitOfMeasureTypeName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
