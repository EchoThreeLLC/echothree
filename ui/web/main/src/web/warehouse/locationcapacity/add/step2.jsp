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

<%@ include file="../../../include/taglibs.jsp" %>

<html:html xhtml="true">
    <head>
        <title>Location Capacities</title>
        <html:base/>
        <%@ include file="../../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />">Home</a> &gt;&gt;
                <a href="<c:url value="/action/Warehouse/Main" />">Warehouses</a> &gt;&gt;
                <a href="<c:url value="/action/Warehouse/Warehouse/Main" />">Warehouses</a> &gt;&gt;
                <c:url var="locationsUrl" value="/action/Warehouse/Location/Main">
                    <c:param name="WarehouseName" value="${location.warehouse.warehouseName}" />
                </c:url>
                <a href="${locationsUrl}">Locations</a> &gt;&gt;
                <c:url var="locationCapacitiesUrl" value="/action/Warehouse/LocationCapacity/Main">
                    <c:param name="WarehouseName" value="${location.warehouse.warehouseName}" />
                    <c:param name="LocationName" value="${location.locationName}" />
                </c:url>
                <a href="${locationCapacitiesUrl}">Capacities</a> &gt;&gt;
                Add
            </h2>
        </div>
        <div id="Content">
            Unit Of Measure Type:<br /><br />
            <c:forEach items="${unitOfMeasureTypes}" var="unitOfMeasureType">
                <c:url var="addUrl" value="/action/Warehouse/LocationCapacity/Add/Step3">
                    <c:param name="WarehouseName" value="${location.warehouse.warehouseName}" />
                    <c:param name="LocationName" value="${location.locationName}" />
                    <c:param name="UnitOfMeasureKindName" value="${unitOfMeasureKind.unitOfMeasureKindName}" />
                    <c:param name="UnitOfMeasureTypeName" value="${unitOfMeasureType.unitOfMeasureTypeName}" />
                </c:url>
                &nbsp;&nbsp;&nbsp;&nbsp;<a href="${addUrl}"><c:out value="${unitOfMeasureType.description}" /></a><br />
             </c:forEach>
        </div>
        <jsp:include page="../../../include/userSession.jsp" />
        <jsp:include page="../../../include/copyright.jsp" />
    </body>
</html:html>
