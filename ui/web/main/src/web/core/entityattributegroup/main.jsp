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
        <title><fmt:message key="pageTitle.entityAttributeGroups" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Core/Main" />"><fmt:message key="navigation.core" /></a> &gt;&gt;
                <fmt:message key="navigation.entityAttributeGroups" />
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:EntityAttributeGroup.Create:EntityAttributeGroup.Edit:EntityAttributeGroup.Delete:EntityAttributeGroup.Review:EntityAttributeGroup.Description" />
            <et:hasSecurityRole securityRole="EntityAttributeGroup.Create">
                <p><a href="<c:url value="/action/Core/EntityAttributeGroup/Add" />">Add Entity Attribute Group.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="EntityAttributeGroup.Review" var="includeReviewUrl" />
            <display:table name="entityAttributeGroups" id="entityAttributeGroup" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Core/EntityAttributeGroup/Review">
                                <c:param name="EntityAttributeGroupName" value="${entityAttributeGroup.entityAttributeGroupName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${entityAttributeGroup.entityAttributeGroupName}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${entityAttributeGroup.entityAttributeGroupName}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${entityAttributeGroup.description}" />
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${entityAttributeGroup.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <et:hasSecurityRole securityRole="EntityAttributeGroup.Edit">
                                <c:url var="setDefaultUrl" value="/action/Core/EntityAttributeGroup/SetDefault">
                                    <c:param name="EntityAttributeGroupName" value="${entityAttributeGroup.entityAttributeGroupName}" />
                                </c:url>
                                <a href="${setDefaultUrl}">Set Default</a>
                            </et:hasSecurityRole>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <et:hasSecurityRole securityRoles="EntityAttributeGroup.Edit:EntityAttributeGroup.Description:EntityAttributeGroup.Delete">
                    <display:column>
                        <et:hasSecurityRole securityRole="EntityAttributeGroup.Edit">
                            <c:url var="editUrl" value="/action/Core/EntityAttributeGroup/Edit">
                                <c:param name="OriginalEntityAttributeGroupName" value="${entityAttributeGroup.entityAttributeGroupName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="EntityAttributeGroup.Description">
                            <c:url var="descriptionsUrl" value="/action/Core/EntityAttributeGroup/Description">
                                <c:param name="EntityAttributeGroupName" value="${entityAttributeGroup.entityAttributeGroupName}" />
                            </c:url>
                            <a href="${descriptionsUrl}">Descriptions</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="EntityAttributeGroup.Delete">
                            <c:url var="deleteUrl" value="/action/Core/EntityAttributeGroup/Delete">
                                <c:param name="EntityAttributeGroupName" value="${entityAttributeGroup.entityAttributeGroupName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </et:hasSecurityRole>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${entityAttributeGroup.entityInstance.entityRef}" />
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
