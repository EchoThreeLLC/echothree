<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>

<!--                                                                                  -->
<!-- Copyright 2002-2018 Echo Three, LLC                                              -->
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
        <title><fmt:message key="pageTitle.mimeTypes" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Core/Main" />"><fmt:message key="navigation.core" /></a> &gt;&gt;
                <fmt:message key="navigation.mimeTypes" />
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:MimeType.Create:MimeType.Edit:MimeType.Delete:MimeType.Review:MimeType.Description" />
            <c:set var="linksInFirstRow" value="true" />
            <et:hasSecurityRole securityRoles="MimeType.Edit:MimeType.Description:MimeType.Delete">
                <c:set var="linksInSecondRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="MimeType.Create">
                <p><a href="<c:url value="/action/Core/MimeType/Add" />">Add Mime Type.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="MimeType.Review" var="includeReviewUrl" />
            <display:table name="mimeTypes" id="mimeType" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Core/MimeType/Review">
                                <c:param name="MimeTypeName" value="${mimeType.mimeTypeName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${mimeType.mimeTypeName}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${mimeType.mimeTypeName}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${mimeType.description}" />
                </display:column>
                <display:column titleKey="columnTitle.entityAttributeType">
                    <c:out value="${mimeType.entityAttributeType.description}" />
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${mimeType.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <et:hasSecurityRole securityRole="MimeType.Edit">
                                <c:url var="setDefaultUrl" value="/action/Core/MimeType/SetDefault">
                                    <c:param name="MimeTypeName" value="${mimeType.mimeTypeName}" />
                                </c:url>
                                <a href="${setDefaultUrl}">Set Default</a>
                            </et:hasSecurityRole>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <c:if test="${linksInFirstRow || linksInSecondRow}">
                    <display:column>
                        <c:url var="mimeTypeUsagesUrl" value="/action/Core/MimeTypeUsage/Main">
                            <c:param name="MimeTypeName" value="${mimeType.mimeTypeName}" />
                        </c:url>
                        <a href="${mimeTypeUsagesUrl}">Usages</a>
                        <c:if test="${linksInFirstRow && linksInSecondRow}">
                            <br />
                        </c:if>
                        <et:hasSecurityRole securityRole="MimeType.Edit">
                            <c:url var="editUrl" value="/action/Core/MimeType/Edit">
                                <c:param name="OriginalMimeTypeName" value="${mimeType.mimeTypeName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="MimeType.Description">
                            <c:url var="descriptionsUrl" value="/action/Core/MimeType/Description">
                                <c:param name="MimeTypeName" value="${mimeType.mimeTypeName}" />
                            </c:url>
                            <a href="${descriptionsUrl}">Descriptions</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="MimeType.Delete">
                            <c:url var="deleteUrl" value="/action/Core/MimeType/Delete">
                                <c:param name="MimeTypeName" value="${mimeType.mimeTypeName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </c:if>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${mimeType.entityInstance.entityRef}" />
                        </c:url>
                        <a href="${eventsUrl}">Events</a>
                    </display:column>
                </et:hasSecurityRole>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
