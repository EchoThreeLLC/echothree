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
        <title>Company Printer Group Uses (<c:out value="${company.companyName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Accounting/Main" />">Accounting</a> &gt;&gt;
                <a href="<c:url value="/action/Accounting/Company/Main" />">Companies</a> &gt;&gt;
                <c:url var="reviewUrl" value="/action/Accounting/Company/Review">
                    <c:param name="CompanyName" value="${company.companyName}" />
                </c:url>
                <a href="${reviewUrl}">Review (<c:out value="${company.companyName}" />)</a> &gt;&gt;
                Printer Group Uses
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List" />
            <p><font size="+2"><b><c:out value="${company.partyGroup.name}" /></b></font></p>
            <br />
            <br />
            <c:set var="commonUrl" scope="request" value="Accounting/CompanyPrinterGroupUse" />
            <c:set var="partyPrinterGroupUses" scope="request" value="${company.partyPrinterGroupUses}" />
            <c:set var="party" scope="request" value="${company}" />
            <jsp:include page="../../include/partyPrinterGroupUses.jsp" />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
