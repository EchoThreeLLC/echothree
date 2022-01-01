<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>

<!--                                                                                  -->
<!-- Copyright 2002-2022 Echo Three, LLC                                              -->
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
        <title>Sales Order Batches</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />">Home</a> &gt;&gt;
                <a href="<c:url value="/action/SalesOrder/Main" />">Sales Orders</a> &gt;&gt;
                Sales Order Batches &gt;&gt;
                <a href="<c:url value="/action/SalesOrder/SalesOrderBatch/Main" />">Search</a> &gt;&gt;
                Add
            </h2>
        </div>
        <div id="Content">
            <et:executionErrors id="errorMessage">
                <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
            </et:executionErrors>
            <html:form action="/SalesOrder/SalesOrderBatch/Add" method="POST" focus="amount">
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
                        <td align=right><fmt:message key="label.paymentMethodChoice" />:</td>
                        <td>
                            <html:select property="paymentMethodChoice">
                                <html:optionsCollection property="paymentMethodChoices" />
                            </html:select> (*)
                            <et:validationErrors id="errorMessage" property="PaymentMethodName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.count" />:</td>
                        <td>
                            <html:text property="count" size="12" maxlength="12" />
                            <et:validationErrors id="errorMessage" property="Count">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.amount" />:</td>
                        <td>
                            <html:text property="amount" size="15" maxlength="15" />
                            <et:validationErrors id="errorMessage" property="Amount">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td><html:submit onclick="onSubmitDisable(this);" />&nbsp;<html:cancel onclick="onSubmitDisable(this);" /><html:hidden property="submitButton" /></td>
                    </tr>
                </table>
            </html:form>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>