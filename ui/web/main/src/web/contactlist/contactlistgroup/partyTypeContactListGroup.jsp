<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>

<!--                                                                                  -->
<!-- Copyright 2002-2019 Echo Three, LLC                                              -->
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
        <title><fmt:message key="pageTitle.partyTypeContactListGroups" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/ContactList/Main" />"><fmt:message key="navigation.contactLists" /></a> &gt;&gt;
                <a href="<c:url value="/action/ContactList/ContactListGroup/Main" />"><fmt:message key="navigation.contactListGroups" /></a> &gt;&gt;
                <fmt:message key="navigation.partyTypeContactListGroups" />
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/ContactList/ContactListGroup/PartyTypeContactListGroupAdd">
                <c:param name="ContactListGroupName" value="${contactListGroup.contactListGroupName}" />
            </c:url>
            <p><a href="${addUrl}">Add Party Type.</a></p>
            <display:table name="partyTypeContactListGroups" id="partyTypeContactListGroup" class="displaytag">
                <display:column titleKey="columnTitle.partyType">
                    <c:out value="${partyTypeContactListGroup.partyType.description}" />
                </display:column>
                <display:column titleKey="columnTitle.addWhenCreated">
                    <c:choose>
                        <c:when test="${partyTypeContactListGroup.addWhenCreated}">
                            Yes
                        </c:when>
                        <c:otherwise>
                            No
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/ContactList/ContactListGroup/PartyTypeContactListGroupEdit">
                        <c:param name="ContactListGroupName" value="${partyTypeContactListGroup.contactListGroup.contactListGroupName}" />
                        <c:param name="PartyTypeName" value="${partyTypeContactListGroup.partyType.partyTypeName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/ContactList/ContactListGroup/PartyTypeContactListGroupDelete">
                        <c:param name="ContactListGroupName" value="${partyTypeContactListGroup.contactListGroup.contactListGroupName}" />
                        <c:param name="PartyTypeName" value="${partyTypeContactListGroup.partyType.partyTypeName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
           </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
