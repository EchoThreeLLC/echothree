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
        <title>Party Types</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />">Home</a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />">Configuration</a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Workflow/Main" />">Workflows</a> &gt;&gt;
                <c:url var="workflowStepsUrl" value="/action/Configuration/WorkflowStep/Main">
                    <c:param name="WorkflowName" value="${workflowDestinationPartyType.workflowDestination.workflowStep.workflow.workflowName}" />
                </c:url>
                <a href="${workflowStepsUrl}">Steps</a> &gt;&gt;
                <c:url var="workflowDestinationsUrl" value="/action/Configuration/WorkflowDestination/Main">
                    <c:param name="WorkflowName" value="${workflowDestinationPartyType.workflowDestination.workflowStep.workflow.workflowName}" />
                    <c:param name="WorkflowStepName" value="${workflowDestinationPartyType.workflowDestination.workflowStep.workflowStepName}" />
                </c:url>
                <a href="${workflowDestinationsUrl}">Destinations</a> &gt;&gt;
                <c:url var="workflowDestinationPartyTypesUrl" value="/action/Configuration/WorkflowDestinationPartyType/Main">
                    <c:param name="WorkflowName" value="${workflowDestinationPartyType.workflowDestination.workflowStep.workflow.workflowName}" />
                    <c:param name="WorkflowStepName" value="${workflowDestinationPartyType.workflowDestination.workflowStep.workflowStepName}" />
                    <c:param name="WorkflowDestinationName" value="${workflowDestinationPartyType.workflowDestination.workflowDestinationName}" />
                </c:url>
                <a href="${workflowDestinationPartyTypesUrl}">Party Types</a> &gt;&gt;
                Security Roles
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Configuration/WorkflowDestinationSecurityRole/Add">
                <c:param name="WorkflowName" value="${workflowDestinationPartyType.workflowDestination.workflowStep.workflow.workflowName}" />
                <c:param name="WorkflowStepName" value="${workflowDestinationPartyType.workflowDestination.workflowStep.workflowStepName}" />
                <c:param name="WorkflowDestinationName" value="${workflowDestinationPartyType.workflowDestination.workflowDestinationName}" />
                <c:param name="PartyTypeName" value="${workflowDestinationPartyType.partyType.partyTypeName}" />
            </c:url>
            <p><a href="${addUrl}">Add Security Role.</a></p>
            <display:table name="workflowDestinationSecurityRoles" id="workflowDestinationSecurityRole" class="displaytag">
                <display:column titleKey="columnTitle.securityRoleGroup">
                    <c:out value="${workflowDestinationSecurityRole.securityRole.securityRoleGroup.description}" />
                </display:column>
                <display:column titleKey="columnTitle.securityRole">
                    <c:out value="${workflowDestinationSecurityRole.securityRole.description}" />
                </display:column>
                <display:column>
                    <c:url var="deleteUrl" value="/action/Configuration/WorkflowDestinationSecurityRole/Delete">
                        <c:param name="WorkflowName" value="${workflowDestinationSecurityRole.workflowDestinationPartyType.workflowDestination.workflowStep.workflow.workflowName}" />
                        <c:param name="WorkflowStepName" value="${workflowDestinationSecurityRole.workflowDestinationPartyType.workflowDestination.workflowStep.workflowStepName}" />
                        <c:param name="WorkflowDestinationName" value="${workflowDestinationSecurityRole.workflowDestinationPartyType.workflowDestination.workflowDestinationName}" />
                        <c:param name="PartyTypeName" value="${workflowDestinationSecurityRole.workflowDestinationPartyType.partyType.partyTypeName}" />
                        <c:param name="SecurityRoleGroupName" value="${workflowDestinationSecurityRole.securityRole.securityRoleGroup.securityRoleGroupName}" />
                        <c:param name="SecurityRoleName" value="${workflowDestinationSecurityRole.securityRole.securityRoleName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
