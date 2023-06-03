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

<%@ include file="../../include/taglibs.jsp" %>

<html:html xhtml="true">
    <head>
        <title>Customer Types</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Advertising/Main" />">Advertising</a> &gt;&gt;
                <a href="<c:url value="/action/Advertising/Offer/Main" />">Offers</a> &gt;&gt;
                Customer Types
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Advertising/OfferCustomerType/Add">
                <c:param name="OfferName" value="${offer.offerName}" />
            </c:url>
            <p><a href="${addUrl}">Add Customer Type.</a></p>
            <display:table name="offerCustomerTypes" id="offerCustomerType" class="displaytag" sort="list" requestURI="/action/Advertising/OfferCustomerType/Main">
                <display:column titleKey="columnTitle.name" sortable="true" sortProperty="offerName">
                    <c:url var="reviewUrl" value="/action/Customer/CustomerType/Review">
                        <c:param name="CustomerTypeName" value="${offerCustomerType.customerType.customerTypeName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${offerCustomerType.customerType.description}" /></a>
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${offerCustomerType.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <c:url var="setDefaultUrl" value="/action/Advertising/OfferCustomerType/SetDefault">
                                <c:param name="OfferName" value="${offerCustomerType.offer.offerName}" />
                                <c:param name="CustomerTypeName" value="${offerCustomerType.customerType.customerTypeName}" />
                            </c:url>
                            <a href="${setDefaultUrl}">Set Default</a>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column>
                    <c:url var="deleteUrl" value="/action/Advertising/OfferCustomerType/Delete">
                        <c:param name="OfferName" value="${offerCustomerType.offer.offerName}" />
                        <c:param name="CustomerTypeName" value="${offerCustomerType.customerType.customerTypeName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
