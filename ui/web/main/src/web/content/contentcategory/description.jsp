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
        <title>Catalog Descriptions</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />">Home</a> &gt;&gt;
                <a href="<c:url value="/action/Content/Main" />">Content</a> &gt;&gt;
                <a href="<c:url value="/action/Content/ContentCollection/Main" />">Collections</a> &gt;&gt;
                <c:url var="contentCatalogsUrl" value="/action/Content/ContentCatalog/Main">
                    <c:param name="ContentCollectionName" value="${contentCollectionName}" />
                </c:url>
                <a href="${contentCatalogsUrl}">Catalogs</a> &gt;&gt;
                <c:url var="contentCategoriesUrl" value="/action/Content/ContentCategory/Main">
                    <c:param name="ContentCollectionName" value="${contentCollectionName}" />
                    <c:param name="ContentCatalogName" value="${contentCatalogName}" />
                    <c:param name="ParentContentCategoryName" value="${parentContentCategoryName}" />
                </c:url>
                <a href="${contentCategoriesUrl}">Categories</a> &gt;&gt;
                Descriptions
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Content/ContentCategory/DescriptionAdd">
                <c:param name="ContentCollectionName" value="${contentCollectionName}" />
                <c:param name="ContentCatalogName" value="${contentCatalogName}" />
                <c:param name="ContentCategoryName" value="${contentCategoryName}" />
                <c:param name="ParentContentCategoryName" value="${parentContentCategoryName}" />
            </c:url>
            <p><a href="${addUrl}">Add Description.</a></p>
            <display:table name="contentCategoryDescriptions" id="contentCategoryDescription" class="displaytag">
                <display:column titleKey="columnTitle.language">
                    <c:out value="${contentCategoryDescription.language.description}" />
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${contentCategoryDescription.description}" />
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/Content/ContentCategory/DescriptionEdit">
                        <c:param name="ContentCollectionName" value="${contentCategoryDescription.contentCategory.contentCatalog.contentCollection.contentCollectionName}" />
                        <c:param name="ContentCatalogName" value="${contentCategoryDescription.contentCategory.contentCatalog.contentCatalogName}" />
                        <c:param name="ContentCategoryName" value="${contentCategoryDescription.contentCategory.contentCategoryName}" />
                        <c:param name="LanguageIsoName" value="${contentCategoryDescription.language.languageIsoName}" />
                        <c:param name="ParentContentCategoryName" value="${contentCategoryDescription.contentCategory.parentContentCategory.contentCategoryName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/Content/ContentCategory/DescriptionDelete">
                        <c:param name="ContentCollectionName" value="${contentCategoryDescription.contentCategory.contentCatalog.contentCollection.contentCollectionName}" />
                        <c:param name="ContentCatalogName" value="${contentCategoryDescription.contentCategory.contentCatalog.contentCatalogName}" />
                        <c:param name="ContentCategoryName" value="${contentCategoryDescription.contentCategory.contentCategoryName}" />
                        <c:param name="LanguageIsoName" value="${contentCategoryDescription.language.languageIsoName}" />
                        <c:param name="ParentContentCategoryName" value="${contentCategoryDescription.contentCategory.parentContentCategory.contentCategoryName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
           </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
