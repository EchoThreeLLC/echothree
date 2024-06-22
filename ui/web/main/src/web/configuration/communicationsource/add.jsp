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
        <title>Communication Sources</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />">Configuration</a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/CommunicationSource/Main" />">Communication Sources</a> &gt;&gt;
                Add
            </h2>
        </div>
        <div id="Content">
            <et:executionErrors id="errorMessage">
                <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
            </et:executionErrors>
            <html:form action="/Configuration/CommunicationSource/Add" method="POST" focus="communicationSourceName">
                <table>
                    <tr>
                        <td align=right><fmt:message key="label.communicationSourceName" />:</td>
                        <td>
                            <html:text property="communicationSourceName" size="40" maxlength="40" /> (*)
                            <et:validationErrors id="errorMessage" property="CommunicationSourceName">
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
                        <td align=right><fmt:message key="label.server" />:</td>
                        <td>
                            <html:select property="serverChoice">
                                <html:optionsCollection property="serverChoices" />
                            </html:select> (*)
                            <et:validationErrors id="errorMessage" property="ServerName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.username" />:</td>
                        <td>
                            <html:text size="40" property="username" maxlength="80" />
                            <et:validationErrors id="errorMessage" property="Username">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.password" />:</td>
                        <td>
                            <html:password property="password" size="20" maxlength="40" />
                            <et:validationErrors id="errorMessage" property="Password">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.receiveWorkEffortScope" />:</td>
                        <td>
                            <html:select property="receiveWorkEffortScopeChoice">
                                <html:optionsCollection property="receiveWorkEffortScopeChoices" />
                            </html:select> (*)
                            <et:validationErrors id="errorMessage" property="ReceiveWorkEffortScopeName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.sendWorkEffortScope" />:</td>
                        <td>
                            <html:select property="sendWorkEffortScopeChoice">
                                <html:optionsCollection property="sendWorkEffortScopeChoices" />
                            </html:select> (*)
                            <et:validationErrors id="errorMessage" property="SendWorkEffortScopeName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.reviewEmployeeSelector" />:</td>
                        <td>
                            <html:select property="reviewEmployeeSelectorChoice">
                                <html:optionsCollection property="reviewEmployeeSelectorChoices" />
                            </html:select>
                            <et:validationErrors id="errorMessage" property="ReviewEmployeeSelectorName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.description" />:</td>
                        <td>
                            <html:text property="description" size="60" maxlength="132" />
                            <et:validationErrors id="errorMessage" property="Description">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td><html:submit onclick="onSubmitDisable(this);" /><input type="hidden" name="submitButton" /></td>
                    </tr>
                </table>
            </html:form>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>