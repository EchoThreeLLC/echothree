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
        <title>Work Effort Types</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />">Home</a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />">Configuration</a> &gt;&gt;
                Work Effort Types
            </h2>
        </div>
        <div id="Content">
            <p><a href="<c:url value="/action/Configuration/WorkEffortType/Add" />">Add Work Effort Type.</a></p>
            <et:checkSecurityRoles securityRoles="Event.List" />
            <display:table name="workEffortTypes" id="workEffortType" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:url var="reviewUrl" value="/action/Configuration/WorkEffortType/Review">
                        <c:param name="WorkEffortTypeName" value="${workEffortType.workEffortTypeName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${workEffortType.workEffortTypeName}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${workEffortType.description}" />
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column>
                    <c:url var="workRequirementTypesUrl" value="/action/Configuration/WorkRequirementType/Main">
                        <c:param name="WorkEffortTypeName" value="${workEffortType.workEffortTypeName}" />
                    </c:url>
                    <a href="${workRequirementTypesUrl}">Work Requirement Types</a>
                    <c:url var="workEffortScopesUrl" value="/action/Configuration/WorkEffortScope/Main">
                        <c:param name="WorkEffortTypeName" value="${workEffortType.workEffortTypeName}" />
                    </c:url>
                    <a href="${workEffortScopesUrl}">Work Effort Scopes</a><br />
                    <c:url var="editUrl" value="/action/Configuration/WorkEffortType/Edit">
                        <c:param name="OriginalWorkEffortTypeName" value="${workEffortType.workEffortTypeName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="descriptionsUrl" value="/action/Configuration/WorkEffortType/Description">
                        <c:param name="WorkEffortTypeName" value="${workEffortType.workEffortTypeName}" />
                    </c:url>
                    <a href="${descriptionsUrl}">Descriptions</a>
                    <c:url var="deleteUrl" value="/action/Configuration/WorkEffortType/Delete">
                        <c:param name="WorkEffortTypeName" value="${workEffortType.workEffortTypeName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${workEffortType.entityInstance.entityRef}" />
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
