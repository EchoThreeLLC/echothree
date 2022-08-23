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
        <title>Training Classes</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
        <%@ include file="tinyMce.jsp" %>
    </head>
    <body onLoad="pageLoaded()">
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />">Home</a> &gt;&gt;
                <a href="<c:url value="/action/HumanResources/Main" />">Human Resources</a> &gt;&gt;
                <a href="<c:url value="/action/HumanResources/TrainingClass/Main" />">Training Classes</a> &gt;&gt;
                <c:url var="translationsUrl" value="/action/HumanResources/TrainingClass/Translation">
                    <c:param name="TrainingClassName" value="${trainingClassTranslation.trainingClass.trainingClassName}" />
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
                    <html:form action="/HumanResources/TrainingClass/TranslationEdit" method="POST" focus="description">
                        <table>
                            <tr>
                                <td align=right><fmt:message key="label.description" />:</td>
                                <td>
                                    <html:text property="description" size="60" maxlength="132" /> (*)
                                    <et:validationErrors id="errorMessage" property="Description">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.overviewMimeTypeChoice" />:</td>
                                <td>
                                    <html:select onchange="overviewMimeTypeChoiceChange()" property="overviewMimeTypeChoice" styleId="overviewMimeTypeChoices">
                                        <html:optionsCollection property="overviewMimeTypeChoices" />
                                    </html:select>
                                    <et:validationErrors id="errorMessage" property="OverviewMimeTypeName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.overview" />:</td>
                                <td>
                                    <html:textarea property="overview" cols="${trainingClassOverviewEditorUse.preferredWidth}" rows="${trainingClassOverviewEditorUse.preferredHeight}" styleId="overviewTA" />
                                    <et:validationErrors id="errorMessage" property="Overview">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.introductionMimeTypeChoice" />:</td>
                                <td>
                                    <html:select onchange="introductionMimeTypeChoiceChange()" property="introductionMimeTypeChoice" styleId="introductionMimeTypeChoices">
                                        <html:optionsCollection property="introductionMimeTypeChoices" />
                                    </html:select>
                                    <et:validationErrors id="errorMessage" property="IntroductionMimeTypeName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.introduction" />:</td>
                                <td>
                                    <html:textarea property="introduction" cols="${trainingClassIntroductionEditorUse.preferredWidth}" rows="${trainingClassIntroductionEditorUse.preferredHeight}" styleId="introductionTA" />
                                    <et:validationErrors id="errorMessage" property="Introduction">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <html:hidden property="trainingClassName" />
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