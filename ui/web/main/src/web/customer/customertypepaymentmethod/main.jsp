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
        <title>Payment Methods</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />">Home</a> &gt;&gt;
                <a href="<c:url value="/action/Customer/Main" />">Customers</a> &gt;&gt;
                <a href="<c:url value="/action/Customer/CustomerType/Main" />">Types</a> &gt;&gt;
                Payment Methods
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Customer/CustomerTypePaymentMethod/Add">
                <c:param name="CustomerTypeName" value="${customerType.customerTypeName}" />
            </c:url>
            <p><a href="${addUrl}">Add Payment Method.</a></p>
            <display:table name="customerTypePaymentMethods" id="customerTypePaymentMethod" class="displaytag">
                <display:column titleKey="columnTitle.paymentMethod">
                    <c:out value="${customerTypePaymentMethod.paymentMethod.description}" />
                </display:column>
                <display:column property="defaultSelectionPriority" titleKey="columnTitle.defaultSelectionPriority" />
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${customerTypePaymentMethod.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <c:url var="setDefaultUrl" value="/action/Customer/CustomerTypePaymentMethod/SetDefault">
                                <c:param name="CustomerTypeName" value="${customerTypePaymentMethod.customerType.customerTypeName}" />
                                <c:param name="PaymentMethodName" value="${customerTypePaymentMethod.paymentMethod.paymentMethodName}" />
                            </c:url>
                            <a href="${setDefaultUrl}">Set Default</a>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/Customer/CustomerTypePaymentMethod/Edit">
                        <c:param name="CustomerTypeName" value="${customerTypePaymentMethod.customerType.customerTypeName}" />
                        <c:param name="PaymentMethodName" value="${customerTypePaymentMethod.paymentMethod.paymentMethodName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/Customer/CustomerTypePaymentMethod/Delete">
                        <c:param name="CustomerTypeName" value="${customerTypePaymentMethod.customerType.customerTypeName}" />
                        <c:param name="PaymentMethodName" value="${customerTypePaymentMethod.paymentMethod.paymentMethodName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
