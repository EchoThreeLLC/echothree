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
        <title>Work Requirement Types</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />">Home</a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />">Configuration</a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/WorkEffortType/Main" />">Work Effort Types</a> &gt;&gt;
                Work Requirement Types
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Configuration/WorkRequirementType/Add">
                <c:param name="WorkEffortTypeName" value="${workEffortType.workEffortTypeName}" />
            </c:url>
            <p><a href="${addUrl}">Add Work Requirement Type.</a></p>
            <et:checkSecurityRoles securityRoles="Event.List" />
            <display:table name="workRequirementTypes" id="workRequirementType" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:url var="reviewUrl" value="/action/Configuration/WorkRequirementType/Review">
                        <c:param name="WorkEffortTypeName" value="${workRequirementType.workEffortType.workEffortTypeName}" />
                        <c:param name="WorkRequirementTypeName" value="${workRequirementType.workRequirementTypeName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${workRequirementType.workRequirementTypeName}" /></a>
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.description">
                    <c:out value="${workRequirementType.description}" />
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/Configuration/WorkRequirementType/Edit">
                        <c:param name="WorkEffortTypeName" value="${workRequirementType.workEffortType.workEffortTypeName}" />
                        <c:param name="OriginalWorkRequirementTypeName" value="${workRequirementType.workRequirementTypeName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="descriptionsUrl" value="/action/Configuration/WorkRequirementType/Description">
                        <c:param name="WorkEffortTypeName" value="${workRequirementType.workEffortType.workEffortTypeName}" />
                        <c:param name="WorkRequirementTypeName" value="${workRequirementType.workRequirementTypeName}" />
                    </c:url>
                    <a href="${descriptionsUrl}">Descriptions</a>
                    <c:url var="deleteUrl" value="/action/Configuration/WorkRequirementType/Delete">
                        <c:param name="WorkEffortTypeName" value="${workRequirementType.workEffortType.workEffortTypeName}" />
                        <c:param name="WorkRequirementTypeName" value="${workRequirementType.workRequirementTypeName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${workRequirementType.entityInstance.entityRef}" />
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
