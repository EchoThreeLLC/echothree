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
        <title>Return Policies</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />">Home</a> &gt;&gt;
                <a href="<c:url value="/action/ReturnPolicy/Main" />">Returns</a> &gt;&gt;
                <a href="<c:url value="/action/ReturnPolicy/ReturnKind/Main" />">Return Kinds</a> &gt;&gt;
                <c:url var="returnPoliciesUrl" value="/action/ReturnPolicy/ReturnPolicy/Main">
                    <c:param name="ReturnKindName" value="${returnPolicy.returnKind.returnKindName}" />
                </c:url>
                <a href="${returnPoliciesUrl}">Return Policies</a> &gt;&gt;
                Return Reasons
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/ReturnPolicy/ReturnPolicyReason/Add">
                <c:param name="ReturnKindName" value="${returnPolicy.returnKind.returnKindName}" />
                <c:param name="ReturnPolicyName" value="${returnPolicy.returnPolicyName}" />
            </c:url>
            <p><a href="${addUrl}">Add Return Policy.</a></p>
            <display:table name="returnPolicyReasons" id="returnPolicyReason" class="displaytag">
                <display:column titleKey="columnTitle.returnReason">
                    <c:url var="returnReasonUrl" value="/action/ReturnPolicy/ReturnReason/Review">
                        <c:param name="ReturnKindName" value="${returnPolicyReason.returnReason.returnKind.returnKindName}" />
                        <c:param name="ReturnReasonName" value="${returnPolicyReason.returnReason.returnReasonName}" />
                    </c:url>
                    <a href="${returnReasonUrl}"><c:out value="${returnPolicyReason.returnReason.description}" /></a>
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${returnPolicyReason.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <c:url var="setDefaultUrl" value="/action/ReturnPolicy/ReturnPolicyReason/SetDefault">
                                <c:param name="ReturnKindName" value="${returnPolicyReason.returnPolicy.returnKind.returnKindName}" />
                                <c:param name="ReturnPolicyName" value="${returnPolicyReason.returnPolicy.returnPolicyName}" />
                                <c:param name="ReturnReasonName" value="${returnPolicyReason.returnReason.returnReasonName}" />
                            </c:url>
                            <a href="${setDefaultUrl}">Set Default</a>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/ReturnPolicy/ReturnPolicyReason/Edit">
                        <c:param name="ReturnKindName" value="${returnPolicyReason.returnPolicy.returnKind.returnKindName}" />
                        <c:param name="ReturnPolicyName" value="${returnPolicyReason.returnPolicy.returnPolicyName}" />
                        <c:param name="ReturnReasonName" value="${returnPolicyReason.returnReason.returnReasonName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/ReturnPolicy/ReturnPolicyReason/Delete">
                        <c:param name="ReturnKindName" value="${returnPolicyReason.returnPolicy.returnKind.returnKindName}" />
                        <c:param name="ReturnPolicyName" value="${returnPolicyReason.returnPolicy.returnPolicyName}" />
                        <c:param name="ReturnReasonName" value="${returnPolicyReason.returnReason.returnReasonName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
