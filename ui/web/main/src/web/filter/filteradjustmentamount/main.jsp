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
        <title>Filter Adjustments</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />">Home</a> &gt;&gt;
                <a href="<c:url value="/action/Filter/Main" />">Filters</a> &gt;&gt;
                <a href="<c:url value="/action/Filter/FilterKind/Main" />">Kinds</a> &gt;&gt;
                <c:url var="filterAdjustmentsUrl" value="/action/Filter/FilterAdjustment/Main">
                    <c:param name="FilterKindName" value="${filterAdjustment.filterKind.filterKindName}" />
                </c:url>
                <a href="${filterAdjustmentsUrl}">Adjustments</a> &gt;&gt;
                Details
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Filter/FilterAdjustmentAmount/Add">
                <c:param name="FilterKindName" value="${filterAdjustment.filterKind.filterKindName}" />
                <c:param name="FilterAdjustmentName" value="${filterAdjustment.filterAdjustmentName}" />
            </c:url>
            <p><a href="${addUrl}">Add.</a></p>
            <display:table name="filterAdjustmentAmounts" id="filterAdjustmentAmount" class="displaytag">
                <display:column titleKey="columnTitle.unitOfMeasure">
                    ${filterAdjustmentAmount.unitOfMeasureType.unitOfMeasureKind.description},
                    ${filterAdjustmentAmount.unitOfMeasureType.description}
                </display:column>
                <display:column property="currency.description" titleKey="columnTitle.currency" />
                <display:column property="amount" titleKey="columnTitle.amount" class="amount" />
                <display:column>
                    <c:url var="editUrl" value="/action/Filter/FilterAdjustmentAmount/Edit">
                        <c:param name="FilterKindName" value="${filterAdjustmentAmount.filterAdjustment.filterKind.filterKindName}" />
                        <c:param name="FilterAdjustmentName" value="${filterAdjustmentAmount.filterAdjustment.filterAdjustmentName}" />
                        <c:param name="UnitOfMeasureName"
                        value="${filterAdjustmentAmount.unitOfMeasureType.unitOfMeasureKind.unitOfMeasureKindName}:${filterAdjustmentAmount.unitOfMeasureType.unitOfMeasureTypeName}" />
                        <c:param name="CurrencyIsoName" value="${filterAdjustmentAmount.currency.currencyIsoName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/Filter/FilterAdjustmentAmount/Delete">
                        <c:param name="FilterKindName" value="${filterAdjustmentAmount.filterAdjustment.filterKind.filterKindName}" />
                        <c:param name="FilterAdjustmentName" value="${filterAdjustmentAmount.filterAdjustment.filterAdjustmentName}" />
                        <c:param name="UnitOfMeasureName"
                        value="${filterAdjustmentAmount.unitOfMeasureType.unitOfMeasureKind.unitOfMeasureKindName}:${filterAdjustmentAmount.unitOfMeasureType.unitOfMeasureTypeName}" />
                        <c:param name="CurrencyIsoName" value="${filterAdjustmentAmount.currency.currencyIsoName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
