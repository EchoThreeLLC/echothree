<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>

<!--                                                                                  -->
<!-- Copyright 2002-2019 Echo Three, LLC                                              -->
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
        <title>Cancellation Reason Descriptions</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />">Home</a> &gt;&gt;
                <a href="<c:url value="/action/CancellationPolicy/Main" />">Cancellations</a> &gt;&gt;
                <a href="<c:url value="/action/CancellationPolicy/CancellationKind/Main" />">Cancellation Kinds</a> &gt;&gt;
                <c:url var="cancellationReasonsUrl" value="/action/CancellationPolicy/CancellationReason/Main">
                    <c:param name="CancellationKindName" value="${cancellationReason.cancellationKind.cancellationKindName}" />
                </c:url>
                <a href="${cancellationReasonsUrl}">Cancellation Reasons</a> &gt;&gt;
                Descriptions
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/CancellationPolicy/CancellationReason/DescriptionAdd">
                <c:param name="CancellationKindName" value="${cancellationReason.cancellationKind.cancellationKindName}" />
                <c:param name="CancellationReasonName" value="${cancellationReason.cancellationReasonName}" />
            </c:url>
            <p><a href="${addUrl}">Add Description.</a></p>
            <display:table name="cancellationReasonDescriptions" id="cancellationReasonDescription" class="displaytag">
                <display:column titleKey="columnTitle.language">
                    <c:out value="${cancellationReasonDescription.language.description}" />
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${cancellationReasonDescription.description}" />
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/CancellationPolicy/CancellationReason/DescriptionEdit">
                        <c:param name="CancellationKindName" value="${cancellationReasonDescription.cancellationReason.cancellationKind.cancellationKindName}" />
                        <c:param name="CancellationReasonName" value="${cancellationReasonDescription.cancellationReason.cancellationReasonName}" />
                        <c:param name="LanguageIsoName" value="${cancellationReasonDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/CancellationPolicy/CancellationReason/DescriptionDelete">
                        <c:param name="CancellationKindName" value="${cancellationReasonDescription.cancellationReason.cancellationKind.cancellationKindName}" />
                        <c:param name="CancellationReasonName" value="${cancellationReasonDescription.cancellationReason.cancellationReasonName}" />
                        <c:param name="LanguageIsoName" value="${cancellationReasonDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
