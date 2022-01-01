<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>

<!--                                                                                  -->
<!-- Copyright 2002-2022 Echo Three, LLC                                              -->
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
        <title>Review (<c:out value="${cancellationKind.cancellationKindName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />">Home</a> &gt;&gt;
                <a href="<c:url value="/action/CancellationPolicy/Main" />">Cancellations</a> &gt;&gt;
                <a href="<c:url value="/action/CancellationPolicy/CancellationKind/Main" />">Cancellation Kinds</a> &gt;&gt;
                Review (<c:out value="${cancellationKind.cancellationKindName}" />)
            </h2>
        </div>
        <div id="Content">
            <p><font size="+2"><b><c:out value="${cancellationKind.description}" /></b></font></p>
            <br />
            Cancellation Kind Name: ${cancellationKind.cancellationKindName}<br />
            <br />
            <c:url var="cancellationSequenceTypeUrl" value="/action/Sequence/SequenceType/Review">
                <c:param name="SequenceTypeName" value="${cancellationKind.cancellationSequenceType.sequenceTypeName}" />
            </c:url>
            Cancellation Sequence Type: <a href="${cancellationSequenceTypeUrl}"><c:out value="${cancellationKind.cancellationSequenceType.description}" /></a>
            <br />
            <br />
            <br />
            Created: <c:out value="${cancellationKind.entityInstance.entityTime.createdTime}" /><br />
            <c:if test='${cancellationKind.entityInstance.entityTime.modifiedTime != null}'>
                Modified: <c:out value="${cancellationKind.entityInstance.entityTime.modifiedTime}" /><br />
            </c:if>
            <c:if test='${cancellationKind.entityInstance.entityTime.deletedTime != null}'>
                Deleted: <c:out value="${cancellationKind.entityInstance.entityTime.deletedTime}" /><br />
            </c:if>
            <et:checkSecurityRoles securityRoles="Event.List" />
            <et:hasSecurityRole securityRole="Event.List">
                <c:url var="eventsUrl" value="/action/Core/Event/Main">
                    <c:param name="EntityRef" value="${cancellationKind.entityInstance.entityRef}" />
                </c:url>
                <a href="${eventsUrl}">Events</a>
            </et:hasSecurityRole>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
