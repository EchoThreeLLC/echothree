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
        <title>Use Name Element Descriptions</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Advertising/Main" />">Advertising</a> &gt;&gt;
                <a href="<c:url value="/action/Advertising/UseNameElement/Main" />">Use Name Elements</a> &gt;&gt;
                Descriptions
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Advertising/UseNameElement/DescriptionAdd">
                <c:param name="UseNameElementName" value="${useNameElement.useNameElementName}" />
            </c:url>
            <p><a href="${addUrl}">Add Description.</a></p>
            <display:table name="useNameElementDescriptions" id="useNameElementDescription" class="displaytag">
                <display:column titleKey="columnTitle.language">
                    <c:out value="${useNameElementDescription.language.description}" />
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${useNameElementDescription.description}" />
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/Advertising/UseNameElement/DescriptionEdit">
                        <c:param name="UseNameElementName" value="${useNameElementDescription.useNameElement.useNameElementName}" />
                        <c:param name="LanguageIsoName" value="${useNameElementDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/Advertising/UseNameElement/DescriptionDelete">
                        <c:param name="UseNameElementName" value="${useNameElementDescription.useNameElement.useNameElementName}" />
                        <c:param name="LanguageIsoName" value="${useNameElementDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
           </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
