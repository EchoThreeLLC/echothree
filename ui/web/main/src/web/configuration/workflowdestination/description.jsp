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
        <title>Workflow Destination Descriptions</title>
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
                    <c:param name="WorkflowName" value="${workflowDestination.workflowStep.workflow.workflowName}" />
                </c:url>
                <a href="${workflowStepsUrl}">Steps</a> &gt;&gt;
                <c:url var="workflowDestinationsUrl" value="/action/Configuration/WorkflowDestination/Main">
                    <c:param name="WorkflowName" value="${workflowDestination.workflowStep.workflow.workflowName}" />
                    <c:param name="WorkflowStepName" value="${workflowDestination.workflowStep.workflowStepName}" />
                </c:url>
                <a href="${workflowDestinationsUrl}">Destinations</a> &gt;&gt;
                Descriptions
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Configuration/WorkflowDestination/DescriptionAdd">
                <c:param name="WorkflowName" value="${workflowDestination.workflowStep.workflow.workflowName}" />
                <c:param name="WorkflowStepName" value="${workflowDestination.workflowStep.workflowStepName}" />
                <c:param name="WorkflowDestinationName" value="${workflowDestination.workflowDestinationName}" />
            </c:url>
            <p><a href="${addUrl}">Add Description.</a></p>
            <display:table name="workflowDestinationDescriptions" id="workflowDestinationDescription" class="displaytag">
                <display:column titleKey="columnTitle.language">
                    <c:out value="${workflowDestinationDescription.language.description}" />
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${workflowDestinationDescription.description}" />
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/Configuration/WorkflowDestination/DescriptionEdit">
                        <c:param name="WorkflowName" value="${workflowDestinationDescription.workflowDestination.workflowStep.workflow.workflowName}" />
                        <c:param name="WorkflowStepName" value="${workflowDestinationDescription.workflowDestination.workflowStep.workflowStepName}" />
                        <c:param name="WorkflowDestinationName" value="${workflowDestinationDescription.workflowDestination.workflowDestinationName}" />
                        <c:param name="LanguageIsoName" value="${workflowDestinationDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/Configuration/WorkflowDestination/DescriptionDelete">
                        <c:param name="WorkflowName" value="${workflowDestinationDescription.workflowDestination.workflowStep.workflow.workflowName}" />
                        <c:param name="WorkflowStepName" value="${workflowDestinationDescription.workflowDestination.workflowStep.workflowStepName}" />
                        <c:param name="WorkflowDestinationName" value="${workflowDestinationDescription.workflowDestination.workflowDestinationName}" />
                        <c:param name="LanguageIsoName" value="${workflowDestinationDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
