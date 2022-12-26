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
        <title>Review (<c:out value="${currency.currencyIsoName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />">Home</a> &gt;&gt;
                <a href="<c:url value="/action/Accounting/Main" />">Accounting</a> &gt;&gt;
                <a href="<c:url value="/action/Accounting/Currency/Main" />">Currencies</a> &gt;&gt;
                Review (<c:out value="${currency.currencyIsoName}" />)
            </h2>
        </div>
        <div id="Content">
            <p><font size="+2"><b><c:out value="${currency.description}" /></b></font></p>
            <br />
            Currency Iso Name: ${currency.currencyIsoName}<br />
            <br />
            <c:if test='${currency.symbol != null}'>
                Symbol: <c:out value="${currency.symbol}" /> <br />
                Symbol Position: <c:out value="${currency.symbolPosition.description}" /> <br />
                Symbol On List Start:
                <c:choose>
                    <c:when test="${currency.symbolOnListStart}">
                        Yes
                    </c:when>
                    <c:otherwise>
                        No
                    </c:otherwise>
                </c:choose>
                <br />
                Symbol On List Member:
                <c:choose>
                    <c:when test="${currency.symbolOnListMember}">
                        Yes
                    </c:when>
                    <c:otherwise>
                        No
                    </c:otherwise>
                </c:choose>
                <br />
                Symbol On Subtotal:
                <c:choose>
                    <c:when test="${currency.symbolOnSubtotal}">
                        Yes
                    </c:when>
                    <c:otherwise>
                        No
                    </c:otherwise>
                </c:choose>
                <br />
                Symbol On Total:
                <c:choose>
                    <c:when test="${currency.symbolOnTotal}">
                        Yes
                    </c:when>
                    <c:otherwise>
                        No
                    </c:otherwise>
                </c:choose>
                <br />
                <br />
            </c:if>
            Grouping Separator: <c:out value="${currency.groupingSeparator}" /><br />
            Grouping Size: <c:out value="${currency.groupingSize}" /><br />
            <br />
            <c:if test='${currency.fractionSeparator != null}'>
                Fraction Separator: <c:out value="${currency.fractionSeparator}" /><br />
                <br />
                Default Fraction Digits: <c:out value="${currency.defaultFractionDigits}" /><br />
                Price Unit Fraction Digits: <c:out value="${currency.priceUnitFractionDigits}" /><br />
                Price Line Fraction Digits: <c:out value="${currency.priceLineFractionDigits}" /><br />
                Cost Unit Fraction Digits: <c:out value="${currency.costUnitFractionDigits}" /><br />
                Cost Line Fraction Digits: <c:out value="${currency.costLineFractionDigits}" /><br />
                <br />
            </c:if>
            Minus Sign: <c:out value="${currency.minusSign}" /><br />
            <br />
            <br />
            <br />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
