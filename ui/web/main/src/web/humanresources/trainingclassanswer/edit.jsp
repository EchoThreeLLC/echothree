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
        <title>Training Class Answers</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
        <%@ include file="tinyMce.jsp" %>
    </head>
    <body onLoad="pageLoaded()">
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/HumanResources/Main" />"><fmt:message key="navigation.humanResources" /></a> &gt;&gt;
                <a href="<c:url value="/action/HumanResources/TrainingClass/Main" />">Training Classes</a> &gt;&gt;
                <c:url var="trainingClassSectionsUrl" value="/action/HumanResources/TrainingClassSection/Main">
                    <c:param name="TrainingClassName" value="${trainingClassAnswer.trainingClassQuestion.trainingClassSection.trainingClass.trainingClassName}" />
                </c:url>
                <a href="${trainingClassSectionsUrl}">Sections</a> &gt;&gt;
                <c:url var="trainingClassSectionsUrl" value="/action/HumanResources/TrainingClassQuestion/Main">
                    <c:param name="TrainingClassName" value="${trainingClassAnswer.trainingClassQuestion.trainingClassSection.trainingClass.trainingClassName}" />
                    <c:param name="TrainingClassSectionName" value="${trainingClassAnswer.trainingClassQuestion.trainingClassSection.trainingClassSectionName}" />
                </c:url>
                <a href="${trainingClassSectionsUrl}">Questions</a> &gt;&gt;
                <c:url var="trainingClassAnswersUrl" value="/action/HumanResources/TrainingClassAnswer/Main">
                    <c:param name="TrainingClassName" value="${trainingClassAnswer.trainingClassQuestion.trainingClassSection.trainingClass.trainingClassName}" />
                    <c:param name="TrainingClassSectionName" value="${trainingClassAnswer.trainingClassQuestion.trainingClassSection.trainingClassSectionName}" />
                    <c:param name="TrainingClassQuestionName" value="${trainingClassAnswer.trainingClassQuestion.trainingClassQuestionName}" />
                </c:url>
                <a href="${trainingClassAnswersUrl}">Answers</a> &gt;&gt;
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
                    <html:form action="/HumanResources/TrainingClassAnswer/Edit" method="POST" focus="trainingClassAnswerName">
                        <table>
                            <tr>
                                <td align=right><fmt:message key="label.trainingClassAnswerName" />:</td>
                                <td>
                                    <html:text property="trainingClassAnswerName" size="40" maxlength="40" /> (*)
                                    <et:validationErrors id="errorMessage" property="TrainingClassAnswerName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.isCorrect" />:</td>
                                <td>
                                    <html:checkbox property="isCorrect" /> (*)
                                    <et:validationErrors id="errorMessage" property="IsCorrect">
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
                                <td align=right><fmt:message key="label.answerMimeTypeChoice" />:</td>
                                <td>
                                    <html:select onchange="answerMimeTypeChoiceChange()" property="answerMimeTypeChoice" styleId="answerMimeTypeChoices">
                                        <html:optionsCollection property="answerMimeTypeChoices" />
                                    </html:select>
                                    <et:validationErrors id="errorMessage" property="AnswerMimeTypeName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.answer" />:</td>
                                <td>
                                    <html:textarea property="answer" cols="60" rows="5" styleId="answer" />
                                    <et:validationErrors id="errorMessage" property="Answer">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.selectedMimeTypeChoice" />:</td>
                                <td>
                                    <html:select onchange="selectedMimeTypeChoiceChange()" property="selectedMimeTypeChoice" styleId="selectedMimeTypeChoices">
                                        <html:optionsCollection property="selectedMimeTypeChoices" />
                                    </html:select>
                                    <et:validationErrors id="errorMessage" property="SelectedMimeTypeName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.selected" />:</td>
                                <td>
                                    <html:textarea property="selected" cols="60" rows="5" styleId="selected" />
                                    <et:validationErrors id="errorMessage" property="Selected">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <html:hidden property="trainingClassName" />
                                    <html:hidden property="trainingClassSectionName" />
                                    <html:hidden property="trainingClassQuestionName" />
                                    <html:hidden property="originalTrainingClassAnswerName" />
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