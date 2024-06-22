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
        <title>Sources</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Advertising/Main" />">Advertising</a> &gt;&gt;
                Sources
            </h2>
        </div>
        <div id="Content">
            <p><a href="<c:url value="/action/Advertising/Source/Add" />">Add Source.</a></p>
            <display:table name="sources" id="source" class="displaytag" sort="list" requestURI="/action/Advertising/Source/Main">
                <display:column titleKey="columnTitle.name" sortable="true" sortProperty="sourceName">
                    <c:url var="reviewUrl" value="/action/Advertising/Source/Review">
                        <c:param name="SourceName" value="${source.sourceName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${source.sourceName}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.offerOfferUse">
                    <c:url var="reviewUrl" value="/action/Advertising/OfferUse/Review">
                        <c:param name="OfferName" value="${source.offerUse.offer.offerName}" />
                        <c:param name="UseName" value="${source.offerUse.use.useName}" />
                    </c:url>
                    <c:out value="${source.offerUse.offer.description}" />:<c:out value="${source.offerUse.use.description}" /> (<a href="${reviewUrl}">${source.offerUse.offer.offerName}:${source.offerUse.use.useName}</a>)
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${source.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <c:url var="setDefaultUrl" value="/action/Advertising/Source/SetDefault">
                                <c:param name="SourceName" value="${source.sourceName}" />
                            </c:url>
                            <a href="${setDefaultUrl}">Set Default</a>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/Advertising/Source/Edit">
                        <c:param name="OriginalSourceName" value="${source.sourceName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/Advertising/Source/Delete">
                        <c:param name="SourceName" value="${source.sourceName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
