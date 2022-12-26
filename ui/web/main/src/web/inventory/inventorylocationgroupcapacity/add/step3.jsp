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
                <a href="<c:url value="/action/Portal" />">Home</a> &gt;&gt;
                <a href="<c:url value="/action/Warehouse/Main" />">Warehouses</a> &gt;&gt;
                <a href="<c:url value="/action/Warehouse/Warehouse/Main" />">Warehouses</a> &gt;&gt;
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
            <et:executionErrors id="errorMessage">
                <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
            </et:executionErrors>
            <html:form action="/Inventory/InventoryLocationGroupCapacity/Add/Step3" method="POST" focus="capacity">
                <table>
                    <tr>
                        <td align=right><fmt:message key="label.capacity" />:</td>
                        <td>
                            <html:text property="capacity" size="12" maxlength="12" /> (*)
                            <et:validationErrors id="errorMessage" property="Capacity">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <html:hidden property="warehouseName" />
                            <html:hidden property="inventoryLocationGroupName" />
                            <html:hidden property="unitOfMeasureKindName" />
                            <html:hidden property="unitOfMeasureTypeName" />
                        </td>
                        <td><html:submit onclick="onSubmitDisable(this);" /><input type="hidden" name="submitButton" /></td>
                    </tr>
                </table>
            </html:form>
        </div>
        <jsp:include page="../../../include/userSession.jsp" />
        <jsp:include page="../../../include/copyright.jsp" />
    </body>
</html:html>
