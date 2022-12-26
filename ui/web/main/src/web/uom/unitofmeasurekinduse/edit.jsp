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
        <title>Uses</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />">Home</a> &gt;&gt;
                <a href="<c:url value="/action/UnitOfMeasure/Main" />">Units of Measure</a> &gt;&gt;
                <c:choose>
                    <c:when test="${forwardParameter == 'UnitOfMeasureKindName'}">
                        <a href="<c:url value="/action/UnitOfMeasure/UnitOfMeasureKind/Main" />">Kinds</a>
                    </c:when>
                    <c:when test="${forwardParameter == 'UnitOfMeasureKindUseTypeName'}">
                        <a href="<c:url value="/action/UnitOfMeasure/UnitOfMeasureKindUseType/Main" />">Use Types</a>
                    </c:when>
                </c:choose>
                &gt;&gt;
                <c:url var="unitOfMeasureKindUsesUrl" value="/action/UnitOfMeasure/UnitOfMeasureKindUse/Main">
                    <c:choose>
                        <c:when test="${forwardParameter == 'UnitOfMeasureKindName'}">
                            <c:param name="UnitOfMeasureKindName" value="${unitOfMeasureKindName}" />
                        </c:when>
                        <c:when test="${forwardParameter == 'UnitOfMeasureKindUseTypeName'}">
                            <c:param name="UnitOfMeasureKindUseTypeName" value="${unitOfMeasureKindUseTypeName}" />
                        </c:when>
                    </c:choose>
                </c:url>
                <a href="${unitOfMeasureKindUsesUrl}">Uses</a> &gt;&gt;
                Edit
            </h2>
        </div>
        <div id="Content">
            <c:choose>
                <c:when test="${commandResult.executionResult.hasErrors}">
                    <et:executionErrors id="errorMessage">
                        <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
                    </et:executionErrors>
                </c:when>
                <c:otherwise>
                    <html:form action="/UnitOfMeasure/UnitOfMeasureKindUse/Edit" method="POST" focus="sortOrder">
                        <table>
                            <tr>
                                <td align=right><fmt:message key="label.isDefault" />:</td>
                                <td>
                                    <html:checkbox property="isDefault" /> (*)
                                    <et:validationErrors id="errorMessage" property="IsDefault">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.sortOrder" />:</td>
                                <td>
                                    <html:text property="sortOrder" size="12" maxlength="12" /> (*)
                                    <et:validationErrors id="errorMessage" property="SortOrder">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <html:hidden property="unitOfMeasureKindName" />
                                    <html:hidden property="unitOfMeasureKindUseTypeName" />
                                    <html:hidden property="forwardParameter" />
                                </td>
                                <td><html:submit onclick="onSubmitDisable(this);" />&nbsp;<html:reset /><html:hidden property="submitButton" /></td>
                            </tr>
                        </table>
                    </html:form>
                </c:otherwise>
            </c:choose>
        </div>
        <jsp:include page="../../include/entityLock.jsp" />
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>