<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>

<!--                                                                                  -->
<!-- Copyright 2002-2024 Echo Three, LLC                                              -->
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
        <title>Countries</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />">Configuration</a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Country/Main" />">Countries</a> &gt;&gt;
                Currencies
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Configuration/GeoCodeCurrency/Add">
                <c:param name="GeoCodeName" value="${geoCode.geoCodeName}" />
            </c:url>
            <p><a href="${addUrl}">Add Currency.</a></p>
            <display:table name="geoCodeCurrencies" id="geoCodeCurrency" class="displaytag">
                <display:column titleKey="columnTitle.currency">
                    <c:url var="reviewUrl" value="/action/Accounting/Currency/Review">
                        <c:param name="CurrencyIsoName" value="${geoCodeCurrency.currency.currencyIsoName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${geoCodeCurrency.currency.description}" /></a>
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${geoCodeCurrency.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <c:url var="setDefaultUrl" value="/action/Configuration/GeoCodeCurrency/SetDefault">
                                <c:param name="GeoCodeName" value="${geoCodeCurrency.geoCode.geoCodeName}" />
                                <c:param name="CurrencyIsoName" value="${geoCodeCurrency.currency.currencyIsoName}" />
                            </c:url>
                            <a href="${setDefaultUrl}">Set Default</a>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/Configuration/GeoCodeCurrency/Edit">
                        <c:param name="GeoCodeName" value="${geoCodeCurrency.geoCode.geoCodeName}" />
                        <c:param name="CurrencyIsoName" value="${geoCodeCurrency.currency.currencyIsoName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/Configuration/GeoCodeCurrency/Delete">
                        <c:param name="GeoCodeName" value="${geoCodeCurrency.geoCode.geoCodeName}" />
                        <c:param name="CurrencyIsoName" value="${geoCodeCurrency.currency.currencyIsoName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
