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
        <title><fmt:message key="pageTitle.harmonizedTariffScheduleCodeUseTypeDescriptions" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Item/Main" />"><fmt:message key="navigation.items" /></a> &gt;&gt;
                <a href="<c:url value="/action/Item/HarmonizedTariffScheduleCodeUseType/Main" />"><fmt:message key="navigation.harmonizedTariffScheduleCodeUseTypes" /></a> &gt;&gt;
                <fmt:message key="navigation.harmonizedTariffScheduleCodeUseTypeDescriptions" />
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Item/HarmonizedTariffScheduleCodeUseType/DescriptionAdd">
                <c:param name="HarmonizedTariffScheduleCodeUseTypeName" value="${harmonizedTariffScheduleCodeUseType.harmonizedTariffScheduleCodeUseTypeName}" />
            </c:url>
            <p><a href="${addUrl}">Add Description.</a></p>
            <display:table name="harmonizedTariffScheduleCodeUseTypeDescriptions" id="harmonizedTariffScheduleCodeUseTypeDescription" class="displaytag">
                <display:column titleKey="columnTitle.language">
                    <c:out value="${harmonizedTariffScheduleCodeUseTypeDescription.language.description}" />
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${harmonizedTariffScheduleCodeUseTypeDescription.description}" />
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/Item/HarmonizedTariffScheduleCodeUseType/DescriptionEdit">
                        <c:param name="HarmonizedTariffScheduleCodeUseTypeName" value="${harmonizedTariffScheduleCodeUseTypeDescription.harmonizedTariffScheduleCodeUseType.harmonizedTariffScheduleCodeUseTypeName}" />
                        <c:param name="LanguageIsoName" value="${harmonizedTariffScheduleCodeUseTypeDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/Item/HarmonizedTariffScheduleCodeUseType/DescriptionDelete">
                        <c:param name="HarmonizedTariffScheduleCodeUseTypeName" value="${harmonizedTariffScheduleCodeUseTypeDescription.harmonizedTariffScheduleCodeUseType.harmonizedTariffScheduleCodeUseTypeName}" />
                        <c:param name="LanguageIsoName" value="${harmonizedTariffScheduleCodeUseTypeDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
           </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
