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
        <title><fmt:message key="pageTitle.securityRoleGroupDescriptions" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />"><fmt:message key="navigation.configuration" /></a> &gt;&gt;
                <c:url var="securityRoleGroupsUrl" value="/action/Configuration/SecurityRoleGroup/Main">
                    <c:param name="ParentSecurityRoleGroupName" value="${securityRoleGroup.parentSecurityRoleGroup.securityRoleGroupName}" />
                </c:url>
                <a href="${securityRoleGroupsUrl}"><fmt:message key="navigation.securityRoleGroups" /></a> &gt;&gt;
                <fmt:message key="navigation.securityRoleGroupDescriptions" />
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Configuration/SecurityRoleGroup/DescriptionAdd">
                <c:param name="SecurityRoleGroupName" value="${securityRoleGroup.securityRoleGroupName}" />
            </c:url>
            <p><a href="${addUrl}">Add Description.</a></p>
            <display:table name="securityRoleGroupDescriptions" id="securityRoleGroupDescription" class="displaytag">
                <display:column titleKey="columnTitle.language">
                    <c:out value="${securityRoleGroupDescription.language.description}" />
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${securityRoleGroupDescription.description}" />
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/Configuration/SecurityRoleGroup/DescriptionEdit">
                        <c:param name="SecurityRoleGroupName" value="${securityRoleGroupDescription.securityRoleGroup.securityRoleGroupName}" />
                        <c:param name="LanguageIsoName" value="${securityRoleGroupDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/Configuration/SecurityRoleGroup/DescriptionDelete">
                        <c:param name="SecurityRoleGroupName" value="${securityRoleGroupDescription.securityRoleGroup.securityRoleGroupName}" />
                        <c:param name="LanguageIsoName" value="${securityRoleGroupDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
           </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
