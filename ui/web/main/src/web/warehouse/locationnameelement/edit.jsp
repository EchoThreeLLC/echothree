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
        <title>Name Elements</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />">Home</a> &gt;&gt;
                <a href="<c:url value="/action/Warehouse/Main" />">Warehouses</a> &gt;&gt;
                <a href="<c:url value="/action/Warehouse/Warehouse/Main" />">Warehouses</a> &gt;&gt;
                <c:url var="locationTypesUrl" value="/action/Warehouse/LocationType/Main">
                    <c:param name="WarehouseName" value="${warehouseName}" />
                </c:url>
                <a href="${locationTypesUrl}">Location Types</a> &gt;&gt;
                <c:url var="locationNameElementsUrl" value="/action/Warehouse/LocationNameElement/Main">
                    <c:param name="WarehouseName" value="${warehouseName}" />
                    <c:param name="LocationTypeName" value="${locationTypeName}" />
                </c:url>
                <a href="${locationNameElementsUrl}">Name Elements</a> &gt;&gt;
                Edit
            </h2>
        </div>
        <div id="Content">
            <c:choose>
                <c:when test="${commandResult.executionResult.hasLockErrors}">
                    <et:executionErrors id="errorMessage">
                        <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
                    </et:executionErrors>
                </c:when>
                <c:otherwise>
                    <et:executionErrors id="errorMessage">
                        <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
                    </et:executionErrors>
                    <html:form action="/Warehouse/LocationNameElement/Edit" method="POST" focus="locationNameElementName">
                        <table>
                            <tr>
                                <td align=right><fmt:message key="label.locationNameElementName" />:</td>
                                <td>
                                    <html:text property="locationNameElementName" size="40" maxlength="40" /> (*)
                                    <et:validationErrors id="errorMessage" property="LocationNameElementName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.offset" />:</td>
                                <td>
                                    <html:text property="offset" size="12" maxlength="12" /> (*)
                                    <et:validationErrors id="errorMessage" property="Offset">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.length" />:</td>
                                <td>
                                    <html:text property="length" size="12" maxlength="12" /> (*)
                                    <et:validationErrors id="errorMessage" property="Length">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.validationPattern" />:</td>
                                <td>
                                    <html:text property="validationPattern" size="40" maxlength="128" />
                                    <et:validationErrors id="errorMessage" property="ValidationPattern">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.description" />:</td>
                                <td>
                                    <html:text property="description" size="60" maxlength="80" />
                                    <et:validationErrors id="errorMessage" property="Description">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <html:hidden property="warehouseName" />
                                    <html:hidden property="locationTypeName" />
                                    <html:hidden property="originalLocationNameElementName" />
                                </td>
                                <td><html:submit onclick="onSubmitDisable(this);" />&nbsp;<html:reset /><html:hidden property="submitButton" /></td>
                            </tr>
                        </table>
                    </html:form>
                </c:otherwise>
            </c:choose>
        </div>
        <jsp:include page="../../include/entityLock.jsp" />
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
