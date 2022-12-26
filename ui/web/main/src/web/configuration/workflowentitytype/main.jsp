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
        <title>Entity Types</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />">Home</a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />">Configuration</a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Workflow/Main" />">Workflows</a> &gt;&gt;
                Entity Types
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Configuration/WorkflowEntityType/Add">
                <c:param name="WorkflowName" value="${workflow.workflowName}" />
            </c:url>
            <p><a href="${addUrl}">Add Entity Type.</a></p>
            <display:table name="workflowEntityTypes" id="workflowEntityType" class="displaytag">
                <display:column titleKey="columnTitle.componentVendor">
                    <c:out value="${workflowEntityType.entityType.componentVendor.description}" />
                </display:column>
                <display:column titleKey="columnTitle.entityType">
                    <c:out value="${workflowEntityType.entityType.description}" />
                </display:column>
                <display:column>
                    <c:url var="deleteUrl" value="/action/Configuration/WorkflowEntityType/Delete">
                        <c:param name="WorkflowName" value="${workflowEntityType.workflow.workflowName}" />
                        <c:param name="ComponentVendorName" value="${workflowEntityType.entityType.componentVendor.componentVendorName}" />
                        <c:param name="EntityTypeName" value="${workflowEntityType.entityType.entityTypeName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
