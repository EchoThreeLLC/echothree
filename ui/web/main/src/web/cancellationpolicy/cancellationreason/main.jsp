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
        <title>Cancellation Reasons</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/CancellationPolicy/Main" />">Cancellations</a> &gt;&gt;
                <a href="<c:url value="/action/CancellationPolicy/CancellationKind/Main" />">Cancellation Kinds</a> &gt;&gt;
                Cancellation Reasons
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/CancellationPolicy/CancellationReason/Add">
                <c:param name="CancellationKindName" value="${cancellationKind.cancellationKindName}" />
            </c:url>
            <p><a href="${addUrl}">Add Cancellation Reason.</a></p>
            <et:checkSecurityRoles securityRoles="Event.List:CancellationReasonType.List" />
            <display:table name="cancellationReasons" id="cancellationReason" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:url var="reviewUrl" value="/action/CancellationPolicy/CancellationReason/Review">
                        <c:param name="CancellationKindName" value="${cancellationReason.cancellationKind.cancellationKindName}" />
                        <c:param name="CancellationReasonName" value="${cancellationReason.cancellationReasonName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${cancellationReason.cancellationReasonName}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${cancellationReason.description}" />
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${cancellationReason.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <c:url var="setDefaultUrl" value="/action/CancellationPolicy/CancellationReason/SetDefault">
                                <c:param name="CancellationKindName" value="${cancellationReason.cancellationKind.cancellationKindName}" />
                                <c:param name="CancellationReasonName" value="${cancellationReason.cancellationReasonName}" />
                            </c:url>
                            <a href="${setDefaultUrl}">Set Default</a>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column>
                    <et:hasSecurityRole securityRole="CancellationReasonType.List">
                        <c:url var="cancellationReasonTypesUrl" value="/action/CancellationPolicy/CancellationReasonType/Main">
                            <c:param name="CancellationKindName" value="${cancellationReason.cancellationKind.cancellationKindName}" />
                            <c:param name="CancellationReasonName" value="${cancellationReason.cancellationReasonName}" />
                        </c:url>
                        <a href="${cancellationReasonTypesUrl}">Cancellation Types</a><br />
                    </et:hasSecurityRole>
                    <c:url var="editUrl" value="/action/CancellationPolicy/CancellationReason/Edit">
                        <c:param name="CancellationKindName" value="${cancellationReason.cancellationKind.cancellationKindName}" />
                        <c:param name="OriginalCancellationReasonName" value="${cancellationReason.cancellationReasonName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="descriptionsUrl" value="/action/CancellationPolicy/CancellationReason/Description">
                        <c:param name="CancellationKindName" value="${cancellationReason.cancellationKind.cancellationKindName}" />
                        <c:param name="CancellationReasonName" value="${cancellationReason.cancellationReasonName}" />
                    </c:url>
                    <a href="${descriptionsUrl}">Descriptions</a>
                    <c:url var="deleteUrl" value="/action/CancellationPolicy/CancellationReason/Delete">
                        <c:param name="CancellationKindName" value="${cancellationReason.cancellationKind.cancellationKindName}" />
                        <c:param name="CancellationReasonName" value="${cancellationReason.cancellationReasonName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${cancellationReason.entityInstance.entityRef}" />
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
