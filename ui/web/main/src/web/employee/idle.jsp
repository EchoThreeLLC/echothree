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

<%@ include file="../include/taglibs.jsp" %>

<html:html xhtml="true">
    <head>
        <title>Idle</title>
        <html:base/>
        <%@ include file="../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                Idle
            </h2>
        </div>
        <div id="Content">
            <et:executionErrors id="errorMessage">
                <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
            </et:executionErrors>
            <c:if test="${!commandResult.executionResult.hasErrors}">
                Idled.
            </c:if>
        </div>
        <jsp:include page="../include/userSession.jsp" />
        <jsp:include page="../include/copyright.jsp" />
    </body>
</html:html>
