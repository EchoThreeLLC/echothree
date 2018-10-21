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
                    <c:param name="WorkflowName" value="${workflowDestination.workflowStep.workflow.workflowName}" />
                </c:url>
                <a href="${workflowStepsUrl}">Steps</a> &gt;&gt;
                <c:url var="workflowDestinationsUrl" value="/action/Configuration/WorkflowDestination/Main">
                    <c:param name="WorkflowName" value="${workflowDestination.workflowStep.workflow.workflowName}" />
                    <c:param name="WorkflowStepName" value="${workflowDestination.workflowStep.workflowStepName}" />
                </c:url>
                <a href="${workflowDestinationsUrl}">Destinations</a> &gt;&gt;
                <c:url var="workflowDestinationPartyTypesUrl" value="/action/Configuration/WorkflowDestinationPartyType/Main">
                    <c:param name="WorkflowName" value="${workflowDestination.workflowStep.workflow.workflowName}" />
                    <c:param name="WorkflowStepName" value="${workflowDestination.workflowStep.workflowStepName}" />
                    <c:param name="WorkflowDestinationName" value="${workflowDestination.workflowDestinationName}" />
                </c:url>
                <a href="${workflowDestinationPartyTypesUrl}">Party Types</a> &gt;&gt;
                Add
            </h2>
        </div>
        <div id="Content">
            <et:executionErrors id="errorMessage">
                <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
            </et:executionErrors>
            <html:form action="/Configuration/WorkflowDestinationPartyType/Add" method="POST">
                <table>
                    <tr>
                        <td align=right><fmt:message key="label.partyType" />:</td>
                        <td>
                            <html:select property="partyTypeChoice">
                                <html:optionsCollection property="partyTypeChoices" />
                            </html:select> (*)
                            <et:validationErrors id="errorMessage" property="PartyTypeName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <html:hidden property="workflowName" />
                            <html:hidden property="workflowStepName" />
                            <html:hidden property="workflowDestinationName" />
                        </td>
                        <td> <html:submit onclick="onSubmitDisable(this);" />&nbsp;<html:cancel onclick="onSubmitDisable(this);" /><html:hidden property="submitButton" /></td>
                    </tr>
                </table>
            </html:form>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
