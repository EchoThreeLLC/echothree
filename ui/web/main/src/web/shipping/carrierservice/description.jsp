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
        <title>Carrier Service Descriptions</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Shipping/Main" />">Shipping</a> &gt;&gt;
                <a href="<c:url value="/action/Shipping/Carrier/Main" />">Carriers</a> &gt;&gt;
                <c:url var="carrierServicesUrl" value="/action/Shipping/CarrierService/Main">
                    <c:param name="CarrierName" value="${carrierService.carrier.carrierName}" />
                </c:url>
                <a href="${carrierServicesUrl}">Carrier Services</a> &gt;&gt;
                Descriptions
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Shipping/CarrierService/DescriptionAdd">
                <c:param name="CarrierName" value="${carrierService.carrier.carrierName}" />
                <c:param name="CarrierServiceName" value="${carrierService.carrierServiceName}" />
            </c:url>
            <p><a href="${addUrl}">Add Description.</a></p>
            <display:table name="carrierServiceDescriptions" id="carrierServiceDescription" class="displaytag">
                <display:column titleKey="columnTitle.language">
                    <c:out value="${carrierServiceDescription.language.description}" />
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${carrierServiceDescription.description}" />
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/Shipping/CarrierService/DescriptionEdit">
                        <c:param name="CarrierName" value="${carrierServiceDescription.carrierService.carrier.carrierName}" />
                        <c:param name="CarrierServiceName" value="${carrierServiceDescription.carrierService.carrierServiceName}" />
                        <c:param name="LanguageIsoName" value="${carrierServiceDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/Shipping/CarrierService/DescriptionDelete">
                        <c:param name="CarrierName" value="${carrierServiceDescription.carrierService.carrier.carrierName}" />
                        <c:param name="CarrierServiceName" value="${carrierServiceDescription.carrierService.carrierServiceName}" />
                        <c:param name="LanguageIsoName" value="${carrierServiceDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
