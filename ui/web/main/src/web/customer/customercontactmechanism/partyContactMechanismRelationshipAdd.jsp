<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>

<!--                                                                                  -->
<!-- Copyright 2002-2025 Echo Three, LLC                                              -->
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
        <title>Customer Contact Mechanism (<c:out value="${partyContactMechanism.contactMechanism.contactMechanismName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Customer/Main" />">Customers</a> &gt;&gt;
                <a href="<c:url value="/action/Customer/Customer/Main" />">Search</a> &gt;&gt;
                <et:countCustomerResults searchTypeName="ORDER_ENTRY" countVar="customerResultsCount" commandResultVar="countCustomerResultsCommandResult" logErrors="false" />
                <c:if test="${customerResultsCount > 0}">
                    <a href="<c:url value="/action/Customer/Customer/Result" />"><fmt:message key="navigation.results" /></a> &gt;&gt;
                </c:if>
                <c:url var="reviewUrl" value="/action/Customer/Customer/Review">
                    <c:param name="CustomerName" value="${customer.customerName}" />
                </c:url>
                <a href="${reviewUrl}">Review (<c:out value="${customer.customerName}" />)</a> &gt;&gt;
                <c:url var="customerContactMechanismsUrl" value="/action/Customer/CustomerContactMechanism/Main">
                    <c:param name="CustomerName" value="${customer.customerName}" />
                </c:url>
                <a href="${customerContactMechanismsUrl}">Contact Mechanisms</a> &gt;&gt;
                Add Contact Mechanism Relationship
            </h2>
        </div>
        <div id="Content">
            <et:executionErrors id="errorMessage">
                <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
            </et:executionErrors>
            <html:form action="/Customer/CustomerContactMechanism/PartyContactMechanismRelationshipAdd" method="POST">
                <table>
                    <tr>
                        <td align=right><fmt:message key="label.toContactMechanism"/>:</td>
                        <td>
                            <html:select property="toContactMechanismChoice">
                                <html:optionsCollection property="toContactMechanismChoices" />
                            </html:select> (*)
                            <et:validationErrors id="errorMessage" property="ToContactMechanismName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <html:hidden property="partyName" />
                            <html:hidden property="fromContactMechanismName" />
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
