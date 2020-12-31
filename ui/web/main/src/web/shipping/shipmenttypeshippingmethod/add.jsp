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
        <title>Shipping Methods</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />">Home</a> &gt;&gt;
                <a href="<c:url value="/action/Shipping/Main" />">Returns</a> &gt;&gt;
                <a href="<c:url value="/action/Shipping/ShipmentType/Main" />">Shipment Types</a> &gt;&gt;
                <c:url var="shipmentTypeShippingMethodsUrl" value="/action/Shipping/ShipmentType/Main">
                    <c:param name="ShipmentTypeName" value="${shipmentTypeName}" />
                </c:url>
                <a href="${shipmentTypeShippingMethodsUrl}">Shipping Methods</a> &gt;&gt;
                Add
            </h2>
        </div>
        <div id="Content">
            <et:executionErrors id="errorMessage">
                <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
            </et:executionErrors>
            <html:form action="/Shipping/ShipmentTypeShippingMethod/Add" method="POST">
                <table>
                    <tr>
                        <td align=right><fmt:message key="label.shippingMethodChoice" />:</td>
                        <td>
                            <html:select property="shippingMethodChoice">
                                <html:optionsCollection property="shippingMethodChoices" />
                            </html:select>
                            <et:validationErrors id="errorMessage" property="ShipmentTypeName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.isDefault" />:</td>
                        <td>
                            <html:checkbox property="isDefault" /> (*)
                            <et:validationErrors id="errorMessage" property="IsDefault">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.sortOrder" />:</td>
                        <td>
                            <html:text property="sortOrder" size="12" maxlength="12" /> (*)
                            <et:validationErrors id="errorMessage" property="SortOrder">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <html:hidden property="shipmentTypeName" />
                        </td>
                        <td><html:submit onclick="onSubmitDisable(this);" /><input type="hidden" name="submitButton" /></td>
                    </tr>
                </table>
            </html:form>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
