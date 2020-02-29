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
        <title>Page Areas</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
        <%@ include file="tinyMce.jsp" %>
    </head>
    <body onLoad="pageLoaded()">
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
                <c:url var="contentPagesUrl" value="/action/Content/ContentPage/Main">
                    <c:param name="ContentCollectionName" value="${contentCollectionName}" />
                    <c:param name="ContentSectionName" value="${contentSectionName}" />
                    <c:param name="ParentContentSectionName" value="${parentContentSectionName}" />
                </c:url>
                <a href="${contentPagesUrl}">Pages</a> &gt;&gt;
                <c:url var="contentPageAreasUrl" value="/action/Content/ContentPageArea/Main">
                    <c:param name="ContentCollectionName" value="${contentCollectionName}" />
                    <c:param name="ContentSectionName" value="${contentSectionName}" />
                    <c:param name="ContentPageName" value="${contentPageName}" />
                    <c:param name="ParentContentSectionName" value="${parentContentSectionName}" />
                </c:url>
                <a href="${contentPageAreasUrl}">Page Areas</a> &gt;&gt;
                Edit
            </h2>
        </div>
        <div id="Content">
            <et:executionErrors id="errorMessage">
                <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
            </et:executionErrors>
            <c:if test="${!commandResult.executionResult.hasLockErrors}">
                <html:form action="/Content/ContentPageArea/Edit" method="POST" focus="description">
                    <table>
                        <tr>
                            <td align=right><fmt:message key="label.mimeTypeChoice" />:</td>
                            <td>
                                <html:select onchange="mimeTypeChoiceChange()" property="mimeTypeChoice" styleId="mimeTypeChoices">
                                    <html:optionsCollection property="mimeTypeChoices" />
                                </html:select> (*)
                                <et:validationErrors id="errorMessage" property="MimeTypeName">
                                    <p><c:out value="${errorMessage}" /></p>
                                </et:validationErrors>
                            </td>
                        </tr>
                        <c:if test="${contentPageArea.contentPageLayoutArea.showDescriptionField}">
                            <tr>
                                <td align=right><fmt:message key="label.description" />:</td>
                                <td>
                                    <html:text property="description" size="60" maxlength="80" />
                                    <et:validationErrors id="errorMessage" property="Description">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                        </c:if>
                        <c:if test="${contentPageArea.contentPageLayoutArea.contentPageAreaType.contentPageAreaTypeName == 'LINK'}">
                            <tr>
                                <td align=right><fmt:message key="label.contentPageAreaUrl" />:</td>
                                <td>
                                    <html:text property="contentPageAreaUrl" size="80" maxlength="200" />
                                    <et:validationErrors id="errorMessage" property="ContentPageAreaUrl">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                        </c:if>
                    <tr>
                        <td align=right><fmt:message key="label.contentPageAreaClob" />:</td>
                        <td>
                            <html:textarea property="contentPageAreaClob" cols="${contentPageAreaEditorUse.preferredWidth}" rows="${contentPageAreaEditorUse.preferredHeight}" styleId="contentPageAreaClobTA" />
                            <et:validationErrors id="errorMessage" property="ContentPageAreaClob">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                        <tr>
                            <td>
                                <html:hidden property="contentCollectionName" />
                                <html:hidden property="contentSectionName" />
                                <html:hidden property="contentPageName" />
                                <html:hidden property="sortOrder" />
                                <html:hidden property="languageIsoName" />
                                <html:hidden property="parentContentSectionName" />
                            </td>
                            <td><html:submit onclick="onSubmitDisable(this);" />&nbsp;<html:cancel onclick="onSubmitDisable(this);" />&nbsp;<html:reset /><html:hidden property="submitButton" /></td>
                        </tr>
                    </table>
                </html:form>
            </c:if>
        </div>
        <jsp:include page="../../include/entityLock.jsp" />
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>