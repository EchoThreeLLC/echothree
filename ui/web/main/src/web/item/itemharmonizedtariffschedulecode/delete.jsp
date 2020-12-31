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
        <title>Item Harmonized Tariff Schedule Codes (<c:out value="${itemHarmonizedTariffScheduleCode.item.itemName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />">Home</a> &gt;&gt;
                <a href="<c:url value="/action/Item/Main" />">Items</a> &gt;&gt;
                <a href="<c:url value="/action/Item/Item/Main" />">Search</a> &gt;&gt;
                <et:countItemResults searchTypeName="ITEM_MAINTENANCE" countVar="itemResultsCount" commandResultVar="countItemResultsCommandResult" logErrors="false" />
                <c:if test="${itemResultsCount > 0}">
                    <a href="<c:url value="/action/Item/Item/Result" />"><fmt:message key="navigation.results" /></a> &gt;&gt;
                </c:if>
                <c:url var="reviewUrl" value="/action/Item/Item/Review">
                    <c:param name="ItemName" value="${itemHarmonizedTariffScheduleCode.item.itemName}" />
                </c:url>
                <a href="${reviewUrl}">Review (<c:out value="${itemHarmonizedTariffScheduleCode.item.itemName}" />)</a> &gt;&gt;
                <c:url var="itemHarmonizedTariffScheduleCodesUrl" value="/action/Item/ItemHarmonizedTariffScheduleCode/Main">
                    <c:param name="ItemName" value="${itemHarmonizedTariffScheduleCode.item.itemName}" />
                </c:url>
                <a href="${itemHarmonizedTariffScheduleCodesUrl}">Harmonized Tariff Schedule Codes</a> &gt;&gt;
                Delete
            </h2>
        </div>
        <div id="Content">
            <p>You are about to delete the <c:out value="${fn:toLowerCase(partyEntityType.entityType.description)}" />:</p>
            &nbsp;&nbsp;&nbsp;&nbsp;Item: <c:out value="${itemHarmonizedTariffScheduleCode.item.description}" /><br />
            &nbsp;&nbsp;&nbsp;&nbsp;Country: <c:out value="${itemHarmonizedTariffScheduleCode.countryGeoCode.description}" /><br />
            &nbsp;&nbsp;&nbsp;&nbsp;Harmonized Tariff Schedule Code Use Type: <c:out value="${itemHarmonizedTariffScheduleCode.harmonizedTariffScheduleCodeUseType.description}" /><br />
            <et:executionErrors id="errorMessage">
                <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
            </et:executionErrors>
            <html:form action="/Item/ItemHarmonizedTariffScheduleCode/Delete" method="POST">
                <table>
                    <tr>
                        <td align=right><fmt:message key="label.confirmDelete" />:</td>
                        <td>
                            <html:checkbox property="confirmDelete" /> (*)
                            <et:validationErrors id="errorMessage" property="ConfirmDelete">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                </table>
                <html:submit value="Delete" onclick="onSubmitDisable(this);" />&nbsp;<html:cancel onclick="onSubmitDisable(this);" /><html:hidden property="submitButton" />
                <html:hidden property="itemName" />
                <html:hidden property="countryName" />
                <html:hidden property="harmonizedTariffScheduleCodeUseTypeName" />
            </html:form>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>