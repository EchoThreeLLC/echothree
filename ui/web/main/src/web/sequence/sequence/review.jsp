<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>

<!--                                                                                  -->
<!-- Copyright 2002-2018 Echo Three, LLC                                              -->
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
        <title>Review (<c:out value="${sequence.sequenceName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />">Home</a> &gt;&gt;
                <a href="<c:url value="/action/Sequence/Main" />">Sequences</a> &gt;&gt;
                <a href="<c:url value="/action/Sequence/SequenceType/Main" />">Types</a> &gt;&gt;
                <c:url var="sequenceTypesUrl" value="/action/Sequence/Sequence/Main">
                    <c:param name="SequenceTypeName" value="${sequence.sequenceType.sequenceTypeName}" />
                </c:url>
                <a href="${sequenceTypesUrl}">Sequences</a> &gt;&gt;
                Review (<c:out value="${sequence.sequenceName}" />)
            </h2>
        </div>
        <div id="Content">
            <p><font size="+2"><b><c:out value="${sequence.description}" /></b></font></p>
            <br />
            <c:url var="sequenceTypeUrl" value="/action/Sequence/SequenceType/Review">
                <c:param name="SequenceTypeName" value="${sequence.sequenceType.sequenceTypeName}" />
            </c:url>
            Sequence Type: <a href="${sequenceTypeUrl}"><c:out value="${sequence.sequenceType.description}" /></a><br />
            <br />
            Sequence Name: ${sequence.sequenceName}<br />
            <br />
            Mask: <c:out value="${sequence.mask}" /><br />
            Value: <c:out value="${sequence.value}" /><br />
            <br />
            <br />
            <br />
            Created: <c:out value="${sequence.entityInstance.entityTime.createdTime}" /><br />
            <c:if test='${sequence.entityInstance.entityTime.modifiedTime != null}'>
                Modified: <c:out value="${sequence.entityInstance.entityTime.modifiedTime}" /><br />
            </c:if>
            <c:url var="eventsUrl" value="/action/Core/Event/Main">
                <c:param name="EntityRef" value="${sequence.entityInstance.entityRef}" />
            </c:url>
            <a href="${eventsUrl}">Events</a>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
