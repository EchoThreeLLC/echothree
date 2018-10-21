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
                <c:url var="workflowEntrancesUrl" value="/action/Configuration/WorkflowEntrance/Main">
                    <c:param name="WorkflowName" value="${workflowEntrancePartyType.workflowEntrance.workflow.workflowName}" />
                </c:url>
                <a href="${workflowEntrancesUrl}">Entrances</a> &gt;&gt;
                <c:url var="workflowEntrancePartyTypesUrl" value="/action/Configuration/WorkflowEntrancePartyType/Main">
                    <c:param name="WorkflowName" value="${workflowEntrancePartyType.workflowEntrance.workflow.workflowName}" />
                    <c:param name="WorkflowEntranceName" value="${workflowEntrancePartyType.workflowEntrance.workflowEntranceName}" />
                </c:url>
                <a href="${workflowEntrancePartyTypesUrl}">Party Types</a> &gt;&gt;
                <c:url var="workflowEntrancePartyTypesUrl" value="/action/Configuration/WorkflowEntranceSecurityRole/Main">
                    <c:param name="WorkflowName" value="${workflowEntrancePartyType.workflowEntrance.workflow.workflowName}" />
                    <c:param name="WorkflowEntranceName" value="${workflowEntrancePartyType.workflowEntrance.workflowEntranceName}" />
                    <c:param name="PartyTypeName" value="${workflowEntrancePartyType.partyType.partyTypeName}" />
                </c:url>
                <a href="${workflowEntrancePartyTypesUrl}">Security Roles</a> &gt;&gt;
                Add
            </h2>
        </div>
        <div id="Content">
            <et:executionErrors id="errorMessage">
                <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
            </et:executionErrors>
            <html:form action="/Configuration/WorkflowEntranceSecurityRole/Add" method="POST">
                <table>
                    <tr>
                        <td align=right><fmt:message key="label.securityRole" />:</td>
                        <td>
                            <html:select property="securityRoleChoice">
                                <html:optionsCollection property="securityRoleChoices" />
                            </html:select> (*)
                            <et:validationErrors id="errorMessage" property="SecurityRoleName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <html:hidden property="workflowName" />
                            <html:hidden property="workflowEntranceName" />
                            <html:hidden property="partyTypeName" />
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
