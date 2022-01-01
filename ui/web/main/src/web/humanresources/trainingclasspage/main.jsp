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
        <title>Training Class Pages</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />">Home</a> &gt;&gt;
                <a href="<c:url value="/action/HumanResources/Main" />">Human Resources</a> &gt;&gt;
                <a href="<c:url value="/action/HumanResources/TrainingClass/Main" />">Training Classes</a> &gt;&gt;
                <c:url var="trainingClassSectionsUrl" value="/action/HumanResources/TrainingClassSection/Main">
                    <c:param name="TrainingClassName" value="${trainingClassSection.trainingClass.trainingClassName}" />
                </c:url>
                <a href="${trainingClassSectionsUrl}">Sections</a> &gt;&gt;
                Pages
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:TrainingClassPage.Create:TrainingClassPage.Edit:TrainingClassPage.Delete:TrainingClassPage.Review:TrainingClassPage.Translation" />
            <et:hasSecurityRole securityRole="TrainingClassPage.Create">
                <c:url var="addUrl" value="/action/HumanResources/TrainingClassPage/Add">
                    <c:param name="TrainingClassName" value="${trainingClassSection.trainingClass.trainingClassName}" />
                    <c:param name="TrainingClassSectionName" value="${trainingClassSection.trainingClassSectionName}" />
                </c:url>
                <p><a href="${addUrl}">Add Training Class Page.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="TrainingClassPage.Review" var="includeReviewUrl" />
            <display:table name="trainingClassPages" id="trainingClassPage" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/HumanResources/TrainingClassPage/Review">
                                <c:param name="TrainingClassName" value="${trainingClassPage.trainingClassSection.trainingClass.trainingClassName}" />
                                <c:param name="TrainingClassSectionName" value="${trainingClassPage.trainingClassSection.trainingClassSectionName}" />
                                <c:param name="TrainingClassPageName" value="${trainingClassPage.trainingClassPageName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${trainingClassPage.trainingClassPageName}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${trainingClassPage.trainingClassPageName}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${trainingClassPage.description}" />
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <et:hasSecurityRole securityRoles="TrainingClassPage.Edit:TrainingClassPage.Translation:TrainingClassPage.Delete">
                    <display:column>
                        <et:hasSecurityRole securityRole="TrainingClassPage.Edit">
                            <c:url var="editUrl" value="/action/HumanResources/TrainingClassPage/Edit">
                                <c:param name="TrainingClassName" value="${trainingClassPage.trainingClassSection.trainingClass.trainingClassName}" />
                                <c:param name="TrainingClassSectionName" value="${trainingClassPage.trainingClassSection.trainingClassSectionName}" />
                                <c:param name="OriginalTrainingClassPageName" value="${trainingClassPage.trainingClassPageName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="TrainingClassPage.Translation">
                            <c:url var="translationsUrl" value="/action/HumanResources/TrainingClassPage/Translation">
                                <c:param name="TrainingClassName" value="${trainingClassPage.trainingClassSection.trainingClass.trainingClassName}" />
                                <c:param name="TrainingClassSectionName" value="${trainingClassPage.trainingClassSection.trainingClassSectionName}" />
                                <c:param name="TrainingClassPageName" value="${trainingClassPage.trainingClassPageName}" />
                            </c:url>
                            <a href="${translationsUrl}">Translations</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="TrainingClassPage.Delete">
                            <c:url var="deleteUrl" value="/action/HumanResources/TrainingClassPage/Delete">
                                <c:param name="TrainingClassName" value="${trainingClassPage.trainingClassSection.trainingClass.trainingClassName}" />
                                <c:param name="TrainingClassSectionName" value="${trainingClassPage.trainingClassSection.trainingClassSectionName}" />
                                <c:param name="TrainingClassPageName" value="${trainingClassPage.trainingClassPageName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </et:hasSecurityRole>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${trainingClassPage.entityInstance.entityRef}" />
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
