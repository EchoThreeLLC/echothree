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
        <title>Section Descriptions</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />">Home</a> &gt;&gt;
                <a href="<c:url value="/action/Content/Main" />">Content</a> &gt;&gt;
                <a href="<c:url value="/action/Content/ContentCollection/Main" />">Collections</a> &gt;&gt;
                <c:url var="contentSectionsUrl" value="/action/Content/ContentSection/Main">
                    <c:param name="ContentCollectionName" value="${contentCollectionName}" />
                    <c:param name="ParentContentSectionName" value="${parentContentSectionName}" />
                </c:url>
                <a href="${contentSectionsUrl}">Sections</a> &gt;&gt;
                Descriptions
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Content/ContentSection/DescriptionAdd">
                <c:param name="ContentCollectionName" value="${contentCollectionName}" />
                <c:param name="ParentContentSectionName" value="${parentContentSectionName}" />
                <c:param name="ContentSectionName" value="${contentSectionName}" />
            </c:url>
            <p><a href="${addUrl}">Add Description.</a></p>
            <display:table name="contentSectionDescriptions" id="contentSectionDescription" class="displaytag">
                <display:column titleKey="columnTitle.language">
                    <c:out value="${contentSectionDescription.language.description}" />
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${contentSectionDescription.description}" />
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/Content/ContentSection/DescriptionEdit">
                        <c:param name="ContentCollectionName" value="${contentSectionDescription.contentSection.contentCollection.contentCollectionName}" />
                        <c:param name="ContentSectionName" value="${contentSectionDescription.contentSection.contentSectionName}" />
                        <c:param name="LanguageIsoName" value="${contentSectionDescription.language.languageIsoName}" />
                        <c:param name="ParentContentSectionName" value="${contentSectionDescription.contentSection.parentContentSection.contentSectionName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/Content/ContentSection/DescriptionDelete">
                        <c:param name="ContentCollectionName" value="${contentSectionDescription.contentSection.contentCollection.contentCollectionName}" />
                        <c:param name="ContentSectionName" value="${contentSectionDescription.contentSection.contentSectionName}" />
                        <c:param name="LanguageIsoName" value="${contentSectionDescription.language.languageIsoName}" />
                        <c:param name="ParentContentSectionName" value="${contentSectionDescription.contentSection.parentContentSection.contentSectionName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
           </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
