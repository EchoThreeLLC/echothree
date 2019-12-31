<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>

<!--                                                                                  -->
<!-- Copyright 2002-2020 Echo Three, LLC                                              -->
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
        <title>Location Descriptions</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
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
                Descriptions
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Warehouse/Location/DescriptionAdd">
                <c:param name="WarehouseName" value="${location.warehouse.warehouseName}" />
                <c:param name="LocationName" value="${location.locationName}" />
            </c:url>
            <p><a href="${addUrl}">Add Description.</a></p>
            <display:table name="locationDescriptions" id="locationDescription" class="displaytag">
                <display:column titleKey="columnTitle.language">
                    <c:out value="${locationDescription.language.description}" />
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${locationDescription.description}" />
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/Warehouse/Location/DescriptionEdit">
                        <c:param name="WarehouseName" value="${locationDescription.location.warehouse.warehouseName}" />
                        <c:param name="LocationName" value="${locationDescription.location.locationName}" />
                        <c:param name="LanguageIsoName" value="${locationDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/Warehouse/Location/DescriptionDelete">
                        <c:param name="WarehouseName" value="${locationDescription.location.warehouse.warehouseName}" />
                        <c:param name="LocationName" value="${locationDescription.location.locationName}" />
                        <c:param name="LanguageIsoName" value="${locationDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
