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
        <title>Division Contact Mechanism (<c:out value="${contactMechanism.contactMechanismName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
        <%@ include file="commentTinyMce.jsp" %>
    </head>
    <body onLoad="pageLoaded()">
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Accounting/Main" />"><fmt:message key="navigation.accounting" /></a> &gt;&gt;
                <a href="<c:url value="/action/Accounting/Company/Main" />"><fmt:message key="navigation.companies" /></a> &gt;&gt;
                <c:url var="divisionsUrl" value="/action/Accounting/Division/Main">
                    <c:param name="CompanyName" value="${division.company.companyName}" />
                </c:url>
                <a href="${divisionsUrl}">Divisions</a> &gt;&gt;
                <c:url var="reviewUrl" value="/action/Accounting/Division/Review">
                    <c:param name="CompanyName" value="${division.company.companyName}" />
                    <c:param name="DivisionName" value="${division.divisionName}" />
                </c:url>
                <a href="${reviewUrl}">Review (<c:out value="${division.divisionName}" />)</a> &gt;&gt;
                <c:url var="divisionContactMechanismsUrl" value="/action/Accounting/DivisionContactMechanism/Main">
                    <c:param name="CompanyName" value="${division.company.companyName}" />
                    <c:param name="DivisionName" value="${division.divisionName}" />
                </c:url>
                <a href="${divisionContactMechanismsUrl}">Contact Mechanisms</a> &gt;&gt;
                <c:url var="reviewUrl" value="/action/Accounting/DivisionContactMechanism/Review">
                    <c:param name="PartyName" value="${division.partyName}" />
                    <c:param name="ContactMechanismName" value="${contactMechanism.contactMechanismName}" />
                </c:url>
                <a href="${reviewUrl}">Division Contact Mechanism (<c:out value="${contactMechanism.contactMechanismName}" />)</a> &gt;&gt;
                Edit Comment
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
                    <html:form action="/Accounting/DivisionContactMechanism/CommentEdit" method="POST" focus="description">
                        <table>
                            <tr>
                                <td align=right><fmt:message key="label.language" />:</td>
                                <td>
                                    <html:select property="languageChoice">
                                        <html:optionsCollection property="languageChoices" />
                                    </html:select> (*)
                                    <et:validationErrors id="errorMessage" property="LanguageIsoName">
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
                                <td align=right><fmt:message key="label.mimeType" />:</td>
                                <td>
                                    <html:select onchange="mimeTypeChoiceChange()" property="mimeTypeChoice" styleId="mimeTypeChoices">
                                        <html:optionsCollection property="mimeTypeChoices" />
                                    </html:select> (*)
                                    <et:validationErrors id="errorMessage" property="MimeTypeName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.clobComment" />:</td>
                                <td>
                                    <html:textarea property="clobComment" cols="${divisionContactMechanismCommentEditorUse.preferredWidth}" rows="${divisionContactMechanismCommentEditorUse.preferredHeight}" styleId="clobCommentTA" /> (*)
                                    <et:validationErrors id="errorMessage" property="Clob">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <html:hidden property="partyName" />
                                    <html:hidden property="contactMechanismName" />
                                    <html:hidden property="commentName" />
                                </td>
                                <td> <html:submit onclick="onSubmitDisable(this);" />&nbsp;<html:cancel onclick="onSubmitDisable(this);" />&nbsp;<html:reset /><html:hidden property="submitButton" /></td>
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
