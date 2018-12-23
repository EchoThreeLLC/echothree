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
        <title>Command Messages</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body onLoad="pageLoaded()">
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />">Home</a> &gt;&gt;
                <a href="<c:url value="/action/Core/Main" />">Core</a> &gt;&gt;
                <a href="<c:url value="/action/Core/CommandMessageType/Main" />">Command Message Types</a> &gt;&gt;
                <c:url var="commandMessagesUrl" value="/action/Core/CommandMessage/Main">
                    <c:param name="CommandMessageTypeName" value="${commandMessageTranslation.commandMessage.commandMessageType.commandMessageTypeName}" />
                </c:url>
                <a href="${commandMessagesUrl}">Command Messages</a> &gt;&gt;
                <c:url var="translationsUrl" value="/action/Core/CommandMessage/Translation">
                    <c:param name="CommandMessageTypeName" value="${commandMessageTranslation.commandMessage.commandMessageType.commandMessageTypeName}" />
                    <c:param name="CommandMessageKey" value="${commandMessageTranslation.commandMessage.commandMessageKey}" />
                </c:url>
                <a href="${translationsUrl}">Translations</a> &gt;&gt;
                Edit
            </h2>
        </div>
        <div id="Content">
            <c:choose>
                <c:when test="${commandResult.executionResult.hasLockErrors}">
                    <et:executionErrors id="errorMessage">
                        <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
                    </et:executionErrors>
                </c:when>
                <c:otherwise>
                    <et:executionErrors id="errorMessage">
                        <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
                    </et:executionErrors>
                    <html:form action="/Core/CommandMessage/TranslationEdit" method="POST" focus="translation">
                        <table>
                            <tr>
                                <td align=right><fmt:message key="label.translation" />:</td>
                                <td>
                                    <html:text property="translation" size="60" maxlength="512" />
                                    <et:validationErrors id="errorMessage" property="Translation">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <html:hidden property="commandMessageTypeName" />
                                    <html:hidden property="commandMessageKey" />
                                    <html:hidden property="languageIsoName" />
                                </td>
                                <td><html:submit onclick="onSubmitDisable(this);" />&nbsp;<html:cancel onclick="onSubmitDisable(this);" />&nbsp;<html:reset /><html:hidden property="submitButton" /></td>
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