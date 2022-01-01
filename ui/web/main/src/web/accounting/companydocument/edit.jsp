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
        <title>Company Document (<c:out value="${partyDocument.document.documentName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
        <%@ include file="documentTinyMce.jsp" %>
    </head>
    <body onLoad="pageLoaded()">
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />">Home</a> &gt;&gt;
                <a href="<c:url value="/action/Accounting/Main" />">Accounting</a> &gt;&gt;
                <a href="<c:url value="/action/Accounting/Company/Main" />">Companies</a> &gt;&gt;
                <c:url var="reviewUrl" value="/action/Accounting/Company/Review">
                    <c:param name="CompanyName" value="${company.companyName}" />
                </c:url>
                <a href="${reviewUrl}">Review (<c:out value="${company.companyName}" />)</a> &gt;&gt;
                <c:url var="companyDocumentsUrl" value="/action/Accounting/CompanyDocument/Main">
                    <c:param name="CompanyName" value="${company.companyName}" />
                </c:url>
                <a href="${companyDocumentsUrl}"><fmt:message key="navigation.companyDocuments" /></a> &gt;&gt;
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
                    <c:choose>
                        <c:when test='${partyDocument.document.documentType.mimeTypeUsageType.mimeTypeUsageTypeName == "TEXT"}'>
                            <jsp:include page="clobEdit.jsp" />
                        </c:when>
                        <c:otherwise>
                            <jsp:include page="blobEdit.jsp" />
                        </c:otherwise>
                    </c:choose>
                </c:otherwise>
            </c:choose>
        </div>
        <jsp:include page="../../include/entityLock.jsp" />
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
