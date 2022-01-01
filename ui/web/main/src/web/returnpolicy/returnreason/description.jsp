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
        <title>Return Reason Descriptions</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />">Home</a> &gt;&gt;
                <a href="<c:url value="/action/ReturnPolicy/Main" />">Returns</a> &gt;&gt;
                <a href="<c:url value="/action/ReturnPolicy/ReturnKind/Main" />">Return Kinds</a> &gt;&gt;
                <c:url var="returnReasonsUrl" value="/action/ReturnPolicy/ReturnReason/Main">
                    <c:param name="ReturnKindName" value="${returnReason.returnKind.returnKindName}" />
                </c:url>
                <a href="${returnReasonsUrl}">Return Reasons</a> &gt;&gt;
                Descriptions
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/ReturnPolicy/ReturnReason/DescriptionAdd">
                <c:param name="ReturnKindName" value="${returnReason.returnKind.returnKindName}" />
                <c:param name="ReturnReasonName" value="${returnReason.returnReasonName}" />
            </c:url>
            <p><a href="${addUrl}">Add Description.</a></p>
            <display:table name="returnReasonDescriptions" id="returnReasonDescription" class="displaytag">
                <display:column titleKey="columnTitle.language">
                    <c:out value="${returnReasonDescription.language.description}" />
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${returnReasonDescription.description}" />
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/ReturnPolicy/ReturnReason/DescriptionEdit">
                        <c:param name="ReturnKindName" value="${returnReasonDescription.returnReason.returnKind.returnKindName}" />
                        <c:param name="ReturnReasonName" value="${returnReasonDescription.returnReason.returnReasonName}" />
                        <c:param name="LanguageIsoName" value="${returnReasonDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/ReturnPolicy/ReturnReason/DescriptionDelete">
                        <c:param name="ReturnKindName" value="${returnReasonDescription.returnReason.returnKind.returnKindName}" />
                        <c:param name="ReturnReasonName" value="${returnReasonDescription.returnReason.returnReasonName}" />
                        <c:param name="LanguageIsoName" value="${returnReasonDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
