<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>

<!--                                                                                  -->
<!-- Copyright 2002-2025 Echo Three, LLC                                              -->
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
        <title>Chain Action Sets</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Chain/Main" />">Chains</a> &gt;&gt;
                <a href="<c:url value="/action/Chain/ChainKind/Main" />">Kinds</a> &gt;&gt;
                <c:url var="chainTypesUrl" value="/action/Chain/ChainType/Main">
                    <c:param name="ChainKindName" value="${chainActionSet.chain.chainType.chainKind.chainKindName}" />
                </c:url>
                <a href="${chainTypesUrl}">Types</a> &gt;&gt;
                <c:url var="chainsUrl" value="/action/Chain/Chain/Main">
                    <c:param name="ChainKindName" value="${chainActionSet.chain.chainType.chainKind.chainKindName}" />
                    <c:param name="ChainTypeName" value="${chainActionSet.chain.chainType.chainTypeName}" />
                </c:url>
                <a href="${chainsUrl}">Chains</a> &gt;&gt;
                <c:url var="chainActionSetsUrl" value="/action/Chain/ChainActionSet/Main">
                    <c:param name="ChainKindName" value="${chainActionSet.chain.chainType.chainKind.chainKindName}" />
                    <c:param name="ChainTypeName" value="${chainActionSet.chain.chainType.chainTypeName}" />
                    <c:param name="ChainName" value="${chainActionSet.chain.chainName}" />
                </c:url>
                <a href="${chainActionSetsUrl}">Action Set</a> &gt;&gt;
                Actions
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Chain/ChainAction/Add">
                <c:param name="ChainKindName" value="${chainActionSet.chain.chainType.chainKind.chainKindName}" />
                <c:param name="ChainTypeName" value="${chainActionSet.chain.chainType.chainTypeName}" />
                <c:param name="ChainName" value="${chainActionSet.chain.chainName}" />
                <c:param name="ChainActionSetName" value="${chainActionSet.chainActionSetName}" />
            </c:url>
            <p><a href="${addUrl}">Add Action.</a></p>
            <et:checkSecurityRoles securityRoles="Event.List" />
            <display:table name="chainActions" id="chainAction" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:url var="reviewUrl" value="/action/Chain/ChainAction/Review">
                        <c:param name="ChainKindName" value="${chainAction.chainActionSet.chain.chainType.chainKind.chainKindName}" />
                        <c:param name="ChainTypeName" value="${chainAction.chainActionSet.chain.chainType.chainTypeName}" />
                        <c:param name="ChainName" value="${chainAction.chainActionSet.chain.chainName}" />
                        <c:param name="ChainActionSetName" value="${chainAction.chainActionSet.chainActionSetName}" />
                        <c:param name="ChainActionName" value="${chainAction.chainActionName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${chainAction.chainActionSet.chainActionSetName}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${chainAction.chainActionSet.description}" />
                </display:column>
                <display:column titleKey="columnTitle.type">
                    <c:out value="${chainAction.chainActionType.description}" />
                </display:column>
                <display:column>
                    <c:choose>
                        <c:when test="${chainAction.chainActionType.chainActionTypeName == 'LETTER'}">
                            <c:url var="letterReviewUrl" value="/action/Chain/Letter/Review">
                                <c:param name="ChainKindName" value="${chainAction.chainActionLetter.letter.chainType.chainKind.chainKindName}" />
                                <c:param name="ChainTypeName" value="${chainAction.chainActionLetter.letter.chainType.chainTypeName}" />
                                <c:param name="LetterName" value="${chainAction.chainActionLetter.letter.letterName}" />
                            </c:url>
                            Send Letter &quot;<a href="${letterReviewUrl}"><c:out value="${chainAction.chainActionLetter.letter.description}" /></a>&quot;
                        </c:when>
                        <c:when test="${chainAction.chainActionType.chainActionTypeName == 'SURVEY'}">
                            TODO
                        </c:when>
                        <c:when test="${chainAction.chainActionType.chainActionTypeName == 'CHAIN_ACTION_SET'}">
                            <c:url var="reviewUrl" value="/action/Chain/ChainActionSet/Review">
                                <c:param name="ChainKindName" value="${chainAction.chainActionChainActionSet.nextChainActionSet.chain.chainType.chainKind.chainKindName}" />
                                <c:param name="ChainTypeName" value="${chainAction.chainActionChainActionSet.nextChainActionSet.chain.chainType.chainTypeName}" />
                                <c:param name="ChainName" value="${chainAction.chainActionChainActionSet.nextChainActionSet.chain.chainName}" />
                                <c:param name="ChainActionSetName" value="${chainAction.chainActionChainActionSet.nextChainActionSet.chainActionSetName}" />
                            </c:url>
                            Go To &quot;<a href="${reviewUrl}"><c:out value="${chainAction.chainActionChainActionSet.nextChainActionSet.description}" /></a>&quot;
                        </c:when>
                    </c:choose>
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column>
                    <c:url var="editUrl" value="/action/Chain/ChainAction/Edit">
                        <c:param name="ChainKindName" value="${chainAction.chainActionSet.chain.chainType.chainKind.chainKindName}" />
                        <c:param name="ChainTypeName" value="${chainAction.chainActionSet.chain.chainType.chainTypeName}" />
                        <c:param name="ChainName" value="${chainAction.chainActionSet.chain.chainName}" />
                        <c:param name="ChainActionSetName" value="${chainAction.chainActionSet.chainActionSetName}" />
                        <c:param name="OriginalChainActionName" value="${chainAction.chainActionName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="descriptionsUrl" value="/action/Chain/ChainAction/Description">
                        <c:param name="ChainKindName" value="${chainAction.chainActionSet.chain.chainType.chainKind.chainKindName}" />
                        <c:param name="ChainTypeName" value="${chainAction.chainActionSet.chain.chainType.chainTypeName}" />
                        <c:param name="ChainName" value="${chainAction.chainActionSet.chain.chainName}" />
                        <c:param name="ChainActionSetName" value="${chainAction.chainActionSet.chainActionSetName}" />
                        <c:param name="ChainActionName" value="${chainAction.chainActionName}" />
                    </c:url>
                    <a href="${descriptionsUrl}">Descriptions</a>
                    <c:url var="deleteUrl" value="/action/Chain/ChainAction/Delete">
                        <c:param name="ChainKindName" value="${chainAction.chainActionSet.chain.chainType.chainKind.chainKindName}" />
                        <c:param name="ChainTypeName" value="${chainAction.chainActionSet.chain.chainType.chainTypeName}" />
                        <c:param name="ChainName" value="${chainAction.chainActionSet.chain.chainName}" />
                        <c:param name="ChainActionSetName" value="${chainAction.chainActionSet.chainActionSetName}" />
                        <c:param name="ChainActionName" value="${chainAction.chainActionName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${chainAction.entityInstance.entityRef}" />
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
