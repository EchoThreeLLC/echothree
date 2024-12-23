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
        <title>Geo Code Alias Type Descriptions</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />">Configuration</a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/GeoCodeType/Main" />">Geo Code Types</a> &gt;&gt;
                <c:url var="geoCodeAliasTypesUrl" value="/action/Configuration/GeoCodeAliasType/Main">
                    <c:param name="GeoCodeTypeName" value="${geoCodeAliasType.geoCodeType.geoCodeTypeName}" />
                </c:url>
                <a href="${geoCodeAliasTypesUrl}">Geo Code Alias Types</a> &gt;&gt;
                Descriptions
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Configuration/GeoCodeAliasType/DescriptionAdd">
                <c:param name="GeoCodeTypeName" value="${geoCodeAliasType.geoCodeType.geoCodeTypeName}" />
                <c:param name="GeoCodeAliasTypeName" value="${geoCodeAliasType.geoCodeAliasTypeName}" />
            </c:url>
            <p><a href="${addUrl}">Add Description.</a></p>
            <display:table name="geoCodeAliasTypeDescriptions" id="geoCodeAliasTypeDescription" class="displaytag">
                <display:column titleKey="columnTitle.language">
                    <c:out value="${geoCodeAliasTypeDescription.language.description}" />
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${geoCodeAliasTypeDescription.description}" />
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/Configuration/GeoCodeAliasType/DescriptionEdit">
                        <c:param name="GeoCodeTypeName" value="${geoCodeAliasTypeDescription.geoCodeAliasType.geoCodeType.geoCodeTypeName}" />
                        <c:param name="GeoCodeAliasTypeName" value="${geoCodeAliasTypeDescription.geoCodeAliasType.geoCodeAliasTypeName}" />
                        <c:param name="LanguageIsoName" value="${geoCodeAliasTypeDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/Configuration/GeoCodeAliasType/DescriptionDelete">
                        <c:param name="GeoCodeTypeName" value="${geoCodeAliasTypeDescription.geoCodeAliasType.geoCodeType.geoCodeTypeName}" />
                        <c:param name="GeoCodeAliasTypeName" value="${geoCodeAliasTypeDescription.geoCodeAliasType.geoCodeAliasTypeName}" />
                        <c:param name="LanguageIsoName" value="${geoCodeAliasTypeDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
