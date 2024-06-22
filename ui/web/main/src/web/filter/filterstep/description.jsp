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
        <title>Filter Step Descriptions</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Filter/Main" />">Filters</a> &gt;&gt;
                <a href="<c:url value="/action/Filter/FilterKind/Main" />">Kinds</a> &gt;&gt;
                <c:url var="filterTypesUrl" value="/action/Filter/FilterType/Main">
                    <c:param name="FilterKindName" value="${filterKind.filterKindName}" />
                </c:url>
                <a href="${filterTypesUrl}">Types</a> &gt;&gt;
                <c:url var="filtersUrl" value="/action/Filter/Filter/Main">
                    <c:param name="FilterKindName" value="${filterKind.filterKindName}" />
                    <c:param name="FilterTypeName" value="${filterType.filterTypeName}" />
                </c:url>
                <a href="${filtersUrl}">Filters</a> &gt;&gt;
                <c:url var="filterStepsUrl" value="/action/Filter/FilterStep/Main">
                    <c:param name="FilterKindName" value="${filterKind.filterKindName}" />
                    <c:param name="FilterTypeName" value="${filterType.filterTypeName}" />
                    <c:param name="FilterName" value="${filter.filterName}" />
                </c:url>
                <a href="${filterStepsUrl}">Steps</a> &gt;&gt;
                Descriptions
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Filter/FilterStep/DescriptionAdd">
                    <c:param name="FilterKindName" value="${filterKind.filterKindName}" />
                    <c:param name="FilterTypeName" value="${filterType.filterTypeName}" />
                    <c:param name="FilterName" value="${filter.filterName}" />
                    <c:param name="FilterStepName" value="${filterStep.filterStepName}" />
            </c:url>
            <p><a href="${addUrl}">Add Description.</a></p>
            <display:table name="filterStepDescriptions" id="filterStepDescription" class="displaytag">
                <display:column titleKey="columnTitle.language">
                    <c:out value="${filterStepDescription.language.description}" />
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${filterStepDescription.description}" />
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/Filter/FilterStep/DescriptionEdit">
                        <c:param name="FilterKindName" value="${filterStepDescription.filterStep.filter.filterType.filterKind.filterKindName}" />
                        <c:param name="FilterTypeName" value="${filterStepDescription.filterStep.filter.filterType.filterTypeName}" />
                        <c:param name="FilterName" value="${filterStepDescription.filterStep.filter.filterName}" />
                        <c:param name="FilterStepName" value="${filterStepDescription.filterStep.filterStepName}" />
                        <c:param name="LanguageIsoName" value="${filterStepDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/Filter/FilterStep/DescriptionDelete">
                        <c:param name="FilterKindName" value="${filterStepDescription.filterStep.filter.filterType.filterKind.filterKindName}" />
                        <c:param name="FilterTypeName" value="${filterStepDescription.filterStep.filter.filterType.filterTypeName}" />
                        <c:param name="FilterName" value="${filterStepDescription.filterStep.filter.filterName}" />
                        <c:param name="FilterStepName" value="${filterStepDescription.filterStep.filterStepName}" />
                        <c:param name="LanguageIsoName" value="${filterStepDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
           </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
