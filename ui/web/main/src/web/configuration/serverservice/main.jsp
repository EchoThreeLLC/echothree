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
        <title><fmt:message key="pageTitle.serverServices" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />"><fmt:message key="navigation.configuration" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Server/Main" />"><fmt:message key="navigation.servers" /></a> &gt;&gt;
                <fmt:message key="navigation.serverServices" />
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Configuration/ServerService/Add">
                <c:param name="ServerName" value="${server.serverName}" />
            </c:url>
            <p><a href="${addUrl}">Add Service.</a></p>
            <display:table name="serverServices" id="serverService" class="displaytag">
                <display:column titleKey="columnTitle.service">
                    <c:url var="serverReviewUrl" value="/action/Configuration/Service/Review">
                        <c:param name="ServiceName" value="${serverService.service.serviceName}" />
                    </c:url>
                    <a href="${serverReviewUrl}"><c:out value="${serverService.service.description}" /></a>
                </display:column>
                <display:column>
                    <c:url var="deleteUrl" value="/action/Configuration/ServerService/Delete">
                        <c:param name="ServerName" value="${serverService.server.serverName}" />
                        <c:param name="ServiceName" value="${serverService.service.serviceName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
           </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
