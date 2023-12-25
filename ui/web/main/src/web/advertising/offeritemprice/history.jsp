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
        <title><fmt:message key="pageTitle.offerItemPrices" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <et:checkSecurityRoles securityRoles="Offer.List:Use.List:Source.List:UseType.List:OfferNameElement.List:UseNameElement.List:OfferItem.List:OfferItemPrice.List:Offer.Review:Item.Review:InventoryCondition.Review:UnitOfMeasureType.Review:Currency.Review" />
        <div id="Header">
            <et:hasSecurityRole securityRoles="Offer.List:Use.List:Source.List:UseType.List:OfferNameElement.List:UseNameElement.List" var="includeAdvertisingUrl" />
            <et:hasSecurityRole securityRole="Offer.List" var="includeOffersUrl" />
            <et:hasSecurityRole securityRole="OfferItem.List" var="includeOfferItemsUrl" />
            <et:hasSecurityRole securityRole="OfferItemPrice.List" var="includeOfferItemPricesUrl" />
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <c:choose>
                    <c:when test="${includeAdvertisingUrl}">
                        <a href="<c:url value="/action/Advertising/Main" />"><fmt:message key="navigation.advertising" /></a>
                    </c:when>
                    <c:otherwise>
                        <fmt:message key="navigation.advertising" />
                    </c:otherwise>
                </c:choose>
                &gt;&gt;
                <c:choose>
                    <c:when test="${includeOffersUrl}">
                        <a href="<c:url value="/action/Advertising/Offer/Main" />"><fmt:message key="navigation.offers" /></a>
                    </c:when>
                    <c:otherwise>
                        <fmt:message key="navigation.offers" />
                    </c:otherwise>
                </c:choose>
                &gt;&gt;
                <c:choose>
                    <c:when test="${includeOfferItemsUrl}">
                        <c:url var="offerItemsUrl" value="/action/Advertising/OfferItem/Main">
                            <c:param name="OfferName" value="${offerItemPrice.offerItem.offer.offerName}" />
                        </c:url>
                        <a href="${offerItemsUrl}"><fmt:message key="navigation.offerItems" /></a>
                    </c:when>
                    <c:otherwise>
                        <fmt:message key="navigation.offerItems" />
                    </c:otherwise>
                </c:choose>
                &gt;&gt;
                <c:choose>
                    <c:when test="${includeOfferItemPricesUrl}">
                        <c:url var="offerItemPricesUrl" value="/action/Advertising/OfferItemPrice/Main">
                            <c:param name="OfferName" value="${offerItemPrice.offerItem.offer.offerName}" />
                            <c:param name="ItemName" value="${offerItemPrice.offerItem.item.itemName}" />
                        </c:url>
                        <a href="${offerItemPricesUrl}"><fmt:message key="navigation.offerItemPrices" /></a>
                    </c:when>
                    <c:otherwise>
                        <fmt:message key="navigation.offerItemPrices" />
                    </c:otherwise>
                </c:choose>
                &gt;&gt;
                <fmt:message key="navigation.history" />
            </h2>
        </div>
        <div id="Content">
            <et:hasSecurityRole securityRole="Offer.Review" var="includeOfferUrl" />
            <et:hasSecurityRole securityRole="Item.Review" var="includeItemUrl" />
            <et:hasSecurityRole securityRole="InventoryCondition.Review" var="includeInventoryConditionUrl" />
            <et:hasSecurityRole securityRole="UnitOfMeasureType.Review" var="includeUnitOfMeasureTypeUrl" />
            <et:hasSecurityRole securityRole="Currency.Review" var="includeCurrencyUrl" />
            Offer:
            <c:choose>
                <c:when test="${includeOfferUrl}">
                    <c:url var="reviewUrl" value="/action/Advertising/Offer/Review">
                        <c:param name="OfferName" value="${offerItemPrice.offerItem.offer.offerName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${offerItemPrice.offerItem.offer.description}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${offerItemPrice.offerItem.offer.description}" />
                </c:otherwise>
            </c:choose>
            <br />
            Item:
            <c:choose>
                <c:when test="${includeItemUrl}">
                    <c:url var="reviewUrl" value="/action/Item/Item/Review">
                        <c:param name="ItemName" value="${offerItemPrice.offerItem.item.itemName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${offerItemPrice.offerItem.item.description}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${offerItemPrice.offerItem.item.description}" />
                </c:otherwise>
            </c:choose>
            <br />
            Inventory Condition:
            <c:choose>
                <c:when test="${includeInventoryConditionUrl}">
                    <c:url var="reviewUrl" value="/action/Inventory/InventoryCondition/Review">
                        <c:param name="InventoryConditionName" value="${offerItemPrice.inventoryCondition.inventoryConditionName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${offerItemPrice.inventoryCondition.description}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${offerItemPrice.inventoryCondition.description}" />
                </c:otherwise>
            </c:choose>
            <br />
            Unit Of Measure Type:
            <c:choose>
                <c:when test="${includeUnitOfMeasureTypeUrl}">
                    <c:url var="reviewUrl" value="/action/UnitOfMeasure/UnitOfMeasureType/Review">
                        <c:param name="UnitOfMeasureKindName" value="${offerItemPrice.unitOfMeasureType.unitOfMeasureKind.unitOfMeasureKindName}" />
                        <c:param name="UnitOfMeasureTypeName" value="${offerItemPrice.unitOfMeasureType.unitOfMeasureTypeName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${offerItemPrice.unitOfMeasureType.description}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${offerItemPrice.unitOfMeasureType.description}" />
                </c:otherwise>
            </c:choose>
            <br />
            Currency:
            <c:choose>
                <c:when test="${includeCurrencyUrl}">
                    <c:url var="reviewUrl" value="/action/Accounting/Currency/Review">
                        <c:param name="CurrencyIsoName" value="${offerItemPrice.currency.currencyIsoName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${offerItemPrice.currency.description}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${offerItemPrice.currency.description}" />
                </c:otherwise>
            </c:choose>
            <br />
            <br />
            <display:table name="offerItemPriceHistory.list" id="history" class="displaytag" export="true" sort="list" requestURI="/action/Advertising/OfferItemPrice/History">
                <display:setProperty name="export.csv.filename" value="OfferItemPriceHistory.csv" />
                <display:setProperty name="export.excel.filename" value="OfferItemPriceHistory.xls" />
                <display:setProperty name="export.pdf.filename" value="OfferItemPriceHistory.pdf" />
                <display:setProperty name="export.rtf.filename" value="OfferItemPriceHistory.rtf" />
                <display:setProperty name="export.xml.filename" value="OfferItemPriceHistory.xml" />
                <display:column titleKey="columnTitle.price" class="amount" media="html" sortable="true" sortProperty="snapshot.unformattedUnitPrice">
                    <c:choose>
                        <c:when test="${history.snapshot.offerItem.item.itemPriceType.itemPriceTypeName == 'FIXED'}">
                            <c:out value="${history.snapshot.unitPrice}" />
                        </c:when>
                        <c:when test="${history.snapshot.offerItem.item.itemPriceType.itemPriceTypeName == 'VARIABLE'}">
                            Minimum: <c:out value="${history.snapshot.minimumUnitPrice}" /><br />
                            Maximum: <c:out value="${history.snapshot.maximumUnitPrice}" /><br />
                            Increment: <c:out value="${history.snapshot.unitPriceIncrement}" />
                        </c:when>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.fromTime" media="html" sortable="true" sortProperty="unformattedFromTime">
                    <c:out value="${history.fromTime}" />
                </display:column>
                <display:column titleKey="columnTitle.thruTime" media="html" sortable="true" sortProperty="unformattedThruTime">
                    <c:if test="${history.unformattedThruTime != 9223372036854775807}">
                        <c:out value="${history.thruTime}" />
                    </c:if>
                </display:column>
                <display:column property="snapshot.offerItem.item.itemPriceType.description" titleKey="columnTitle.itemPriceType" media="csv excel pdf rtf xml" />
                <display:column property="snapshot.unitPrice" titleKey="columnTitle.unitPrice" media="csv excel pdf rtf xml" />
                <display:column property="snapshot.minimumUnitPrice" titleKey="columnTitle.minimumUnitPrice" media="csv excel pdf rtf xml" />
                <display:column property="snapshot.maximumUnitPrice" titleKey="columnTitle.maximumUnitPrice" media="csv excel pdf rtf xml" />
                <display:column property="snapshot.unitPriceIncrement" titleKey="columnTitle.unitPriceIncrement" media="csv excel pdf rtf xml" />
                <display:column property="fromTime" titleKey="columnTitle.fromTime" media="csv excel pdf rtf xml" />
                <display:column property="thruTime" titleKey="columnTitle.thruTime" media="csv excel pdf rtf xml" />
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
