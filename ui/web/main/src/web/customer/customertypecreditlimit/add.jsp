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
        <title>Credit Limits</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Customer/Main" />">Customers</a> &gt;&gt;
                <a href="<c:url value="/action/Customer/CustomerType/Main" />">Types</a> &gt;&gt;
                <c:url var="customerTypeCreditLimitsUrl" value="/action/Customer/CustomerTypeCreditLimit/Main">
                    <c:param name="CustomerTypeName" value="${customerTypeName}" />
                </c:url>
                <a href="${customerTypeCreditLimitsUrl}">Credit Limits</a> &gt;&gt;
                Add
            </h2>
        </div>
        <div id="Content">
            <et:executionErrors id="errorMessage">
                <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
            </et:executionErrors>
            <html:form action="/Customer/CustomerTypeCreditLimit/Add" method="POST" focus="creditLimit">
                <table>
                <tr>
                    <td align=right><fmt:message key="label.currency" />:</td>
                    <td>
                        <html:select property="currencyChoice">
                            <html:optionsCollection property="currencyChoices" />
                        </html:select> (*)
                        <et:validationErrors id="errorMessage" property="CurrencyIsoName">
                            <p><c:out value="${errorMessage}" /></p>
                        </et:validationErrors>
                    </td>
                </tr>
                    <tr>
                        <td align=right><fmt:message key="label.creditLimit" />:</td>
                        <td>
                            <html:text property="creditLimit" size="15" maxlength="15" /> (*)
                            <et:validationErrors id="errorMessage" property="CreditLimit">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.potentialCreditLimit" />:</td>
                        <td>
                            <html:text property="potentialCreditLimit" size="15" maxlength="15" /> (*)
                            <et:validationErrors id="errorMessage" property="PotentialCreditLimit">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <html:hidden property="customerTypeName" />
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
