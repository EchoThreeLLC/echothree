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
        <title>Filter Step Elements</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />">Home</a> &gt;&gt;
                <a href="<c:url value="/action/Filter/Main" />">Filters</a> &gt;&gt;
                <a href="<c:url value="/action/Filter/FilterKind/Main" />">Kinds</a> &gt;&gt;
                <c:url var="filterTypesUrl" value="/action/Filter/FilterType/Main">
                    <c:param name="FilterKindName" value="${filterKindName}" />
                </c:url>
                <a href="${filterTypesUrl}">Types</a> &gt;&gt;
                <c:url var="filtersUrl" value="/action/Filter/Filter/Main">
                    <c:param name="FilterKindName" value="${filterKindName}" />
                    <c:param name="FilterTypeName" value="${filterTypeName}" />
                </c:url>
                <a href="${filtersUrl}">Filters</a> &gt;&gt;
                <c:url var="filterStepsUrl" value="/action/Filter/FilterStep/Main">
                    <c:param name="FilterKindName" value="${filterKindName}" />
                    <c:param name="FilterTypeName" value="${filterTypeName}" />
                    <c:param name="FilterName" value="${filterName}" />
                </c:url>
                <a href="${filterStepsUrl}">Steps</a> &gt;&gt;
                <c:url var="filterStepElementsUrl" value="/action/Filter/FilterStepElement/Main">
                    <c:param name="FilterKindName" value="${filterKindName}" />
                    <c:param name="FilterTypeName" value="${filterTypeName}" />
                    <c:param name="FilterName" value="${filterName}" />
                    <c:param name="FilterStepName" value="${filterStepName}" />
                </c:url>
                <a href="${filterStepElementsUrl}">Elements</a> &gt;&gt;
                Add
            </h2>
        </div>
        <div id="Content">
            <et:executionErrors id="errorMessage">
                <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
            </et:executionErrors>
            <html:form action="/Filter/FilterStepElement/Add" method="POST" focus="filterStepElementName">
                <table>
                    <tr>
                        <td align=right><fmt:message key="label.filterStepElementName" />:</td>
                        <td>
                            <html:text property="filterStepElementName" size="40" maxlength="40" /> (*)
                            <et:validationErrors id="errorMessage" property="FilterStepElementName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.filterItemSelector" />:</td>
                        <td>
                            <html:select property="filterItemSelectorChoice">
                                <html:optionsCollection property="filterItemSelectorChoices" />
                            </html:select> (*)
                            <et:validationErrors id="errorMessage" property="FilterItemSelectorName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    </tr>
                        <td align=right><fmt:message key="label.filterAdjustment" />:</td>
                        <td>
                            <html:select property="filterAdjustmentChoice">
                                <html:optionsCollection property="filterAdjustmentChoices" />
                            </html:select> (*)
                            <et:validationErrors id="errorMessage" property="FilterAdjustmentName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.description" />:</td>
                        <td>
                            <html:text property="description" size="60" maxlength="80" />
                            <et:validationErrors id="errorMessage" property="Description">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <html:hidden property="filterKindName" />
                            <html:hidden property="filterTypeName" />
                            <html:hidden property="filterName" />
                            <html:hidden property="filterStepName" />
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