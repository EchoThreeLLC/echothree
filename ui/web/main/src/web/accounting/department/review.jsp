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
        <title>Review (<c:out value="${department.departmentName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Accounting/Main" />"><fmt:message key="navigation.accounting" /></a> &gt;&gt;
                <a href="<c:url value="/action/Accounting/Company/Main" />">Companies</a> &gt;&gt;
                <c:url var="divisionsUrl" value="/action/Accounting/Division/Main">
                    <c:param name="CompanyName" value="${department.division.company.companyName}" />
                </c:url>
                <a href="${divisionsUrl}">Divisions</a> &gt;&gt;
                <c:url var="divisionsUrl" value="/action/Accounting/Division/Main">
                    <c:param name="CompanyName" value="${department.division.company.companyName}" />
                    <c:param name="DivisionName" value="${department.division.divisionName}" />
                </c:url>
                <a href="${divisionsUrl}">Departments</a> &gt;&gt;
                Review (<c:out value="${department.departmentName}" />)
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List" />
            <p><font size="+2"><b><c:out value="${department.partyGroup.name}" /></b></font></p>
            <br />
            Company Name: ${department.division.company.companyName}<br />
            Division Name: ${department.division.divisionName}<br />
            Department Name: ${department.departmentName}<br />
            <br />
            <br />
            <br />
            <c:set var="commonUrl" scope="request" value="Accounting/DepartmentContactMechanism" />
            <c:set var="party" scope="request" value="${department}" />
            <c:set var="partyContactMechanisms" scope="request" value="${department.partyContactMechanisms}" />
            <jsp:include page="../../include/partyContactMechanisms.jsp" />
            <c:set var="tagScopes" scope="request" value="${department.tagScopes}" />
            <c:set var="entityAttributeGroups" scope="request" value="${department.entityAttributeGroups}" />
            <c:set var="entityInstance" scope="request" value="${department.entityInstance}" />
            <c:url var="returnUrl" scope="request" value="/../action/Accounting/Department/Review">
                <c:param name="PartyName" value="${department.partyName}" />
            </c:url>
            <jsp:include page="../../include/tagScopes.jsp" />
            <jsp:include page="../../include/entityAttributeGroups.jsp" />
            <jsp:include page="../../include/entityInstance.jsp" />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
