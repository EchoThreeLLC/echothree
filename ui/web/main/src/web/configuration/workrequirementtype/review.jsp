<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>

<!--                                                                                  -->
<!-- Copyright 2002-2024 Echo Three, LLC                                              -->
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
        <title>Review (<c:out value="${workRequirementType.workRequirementTypeName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />">Configuration</a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/WorkEffortType/Main" />">Work Effort Types</a> &gt;&gt;
                <c:url var="workRequirementTypesUrl" value="/action/Configuration/WorkRequirementType/Main">
                    <c:param name="WorkEffortTypeName" value="${workRequirementType.workEffortType.workEffortTypeName}" />
                </c:url>
                <a href="${workRequirementTypesUrl}">Work Requirement Types</a> &gt;&gt;
                Review (<c:out value="${workRequirementType.workRequirementTypeName}" />)
            </h2>
        </div>
        <div id="Content">
            <p><font size="+2"><b><c:out value="${workRequirementType.description}" /></b></font></p>
            <br />
            <c:url var="workEffortTypeUrl" value="/action/Configuration/WorkEffortType/Review">
                <c:param name="WorkEffortTypeName" value="${workRequirementType.workEffortType.workEffortTypeName}" />
            </c:url>
            Work Effort Type: <a href="${workEffortTypeUrl}"><c:out value="${workRequirementType.workEffortType.description}" /></a><br />
            Work Requirement Type Name: ${workRequirementType.workRequirementTypeName}<br />
            <br />
            Work Requirement Sequence:
            <c:choose>
                <c:when test="${workRequirementType.workRequirementSequence == null}">
                    Using Default
                </c:when>
                <c:otherwise>
                    <c:url var="workRequirementSequenceUrl" value="/action/Sequence/Sequence/Review">
                        <c:param name="SequenceTypeName" value="${workRequirementType.workRequirementSequence.sequenceType.sequenceTypeName}" />
                        <c:param name="SequenceName" value="${workRequirementType.workRequirementSequence.sequenceName}" />
                    </c:url>
                    <a href="${workRequirementSequenceUrl}"><c:out value="${workRequirementType.workRequirementSequence.description}" /></a>
                </c:otherwise>
            </c:choose>
            <br />
            <br />
            Workflow Step:
            <c:choose>
                <c:when test="${workRequirementType.workflowStep == null}">
                    <i><fmt:message key="phrase.notSet" /></i>
                </c:when>
                <c:otherwise>
                    <c:url var="workflowStepUrl" value="/action/Configuration/WorkflowStep/Review">
                        <c:param name="WorkflowName" value="${workRequirementType.workflowStep.workflow.workflowName}" />
                        <c:param name="WorkflowStepName" value="${workRequirementType.workflowStep.workflowStepName}" />
                    </c:url>
                    <a href="${workflowStepUrl}"><c:out value="${workRequirementType.workflowStep.workflow.description}" />:<c:out value="${workRequirementType.workflowStep.description}" /></a>
                </c:otherwise>
            </c:choose>
            <br />
            <br />
            Estimated Time Allowed:
            <c:choose>
                <c:when test="${workRequirementType.estimatedTimeAllowed == null}">
                    <c:choose>
                        <c:when test="${workRequirementType.workEffortType.estimatedTimeAllowed == null}">
                            <i><fmt:message key="phrase.notSet" /></i>
                        </c:when>
                        <c:otherwise>
                            Using Default (<c:out value="${workRequirementType.workEffortType.estimatedTimeAllowed}" />)
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <c:out value="${workRequirementType.estimatedTimeAllowed}" />
                </c:otherwise>
            </c:choose>
            <br />
            Maximum Time Allowed: 
            <c:choose>
                <c:when test="${workRequirementType.maximumTimeAllowed == null}">
                    <c:choose>
                        <c:when test="${workRequirementType.workEffortType.maximumTimeAllowed == null}">
                            <i><fmt:message key="phrase.notSet" /></i>
                        </c:when>
                        <c:otherwise>
                            Using Default (<c:out value="${workRequirementType.workEffortType.maximumTimeAllowed}" />)
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <c:out value="${workRequirementType.maximumTimeAllowed}" />
                </c:otherwise>
            </c:choose>
            <br />
            <br />
            Allow Reassignment:
            <c:choose>
                <c:when test="${workRequirementType.allowReassignment}">
                    <fmt:message key="phrase.yes" />
                </c:when>
                <c:otherwise>
                    <fmt:message key="phrase.no" />
                </c:otherwise>
            </c:choose>
            <br />
            <br />
            <br />
            Created: <c:out value="${workRequirementType.entityInstance.entityTime.createdTime}" /><br />
            <c:if test='${workRequirementType.entityInstance.entityTime.modifiedTime != null}'>
                Modified: <c:out value="${workRequirementType.entityInstance.entityTime.modifiedTime}" /><br />
            </c:if>
            <c:url var="eventsUrl" value="/action/Core/Event/Main">
                <c:param name="EntityRef" value="${workRequirementType.entityInstance.entityRef}" />
            </c:url>
            <a href="${eventsUrl}">Events</a>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
