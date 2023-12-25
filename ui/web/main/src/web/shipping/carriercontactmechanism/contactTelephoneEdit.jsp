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
        <title>Carrier Contact Mechanism (<c:out value="${contactMechanism.contactMechanismName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Shipping/Main" />">Shipping</a> &gt;&gt;
                <a href="<c:url value="/action/Shipping/Carrier/Main" />">Carriers</a> &gt;&gt;
                <c:url var="reviewUrl" value="/action/Shipping/Carrier/Review">
                    <c:param name="CarrierName" value="${carrier.carrierName}" />
                </c:url>
                <a href="${reviewUrl}">Review (<c:out value="${carrier.carrierName}" />)</a> &gt;&gt;
                <c:url var="carrierContactMechanismsUrl" value="/action/Shipping/CarrierContactMechanism/Main">
                    <c:param name="CarrierName" value="${carrier.carrierName}" />
                </c:url>
                <a href="${carrierContactMechanismsUrl}">Contact Mechanisms</a> &gt;&gt;
                Edit Telephone
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
                    <html:form action="/Shipping/CarrierContactMechanism/ContactTelephoneEdit" method="POST" focus="areaCode">
                        <table>
                            <tr>
                                <td align=right><fmt:message key="label.allowSolicitation" />:</td>
                                <td>
                                    <html:checkbox property="allowSolicitation" /> (*)
                                    <et:validationErrors id="errorMessage" property="AllowSolicitation">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.areaCode" />:</td>
                                <td>
                                    <html:text property="areaCode" size="5" maxlength="5" />
                                    <c:if test='${contactMechanism.contactTelephone.countryGeoCode.areaCodeRequired}'>
                                        (*)
                                    </c:if>
                                    <c:if test='${contactMechanism.contactTelephone.countryGeoCode.areaCodeExample != null}'>
                                        <i>(Example: <c:out value="${contactMechanism.contactTelephone.countryGeoCode.areaCodeExample}" />)</i>
                                    </c:if>
                                    <et:validationErrors id="errorMessage" property="AreaCode">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.telephoneNumber" />:</td>
                                <td>
                                    <html:text property="telephoneNumber" size="15" maxlength="25" /> (*)
                                    <c:if test='${contactMechanism.contactTelephone.countryGeoCode.telephoneNumberExample != null}'>
                                        <i>(Example: <c:out value="${contactMechanism.contactTelephone.countryGeoCode.telephoneNumberExample}" />)</i>
                                    </c:if>
                                    <et:validationErrors id="errorMessage" property="TelephoneNumber">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.telephoneExtension" />:</td>
                                <td>
                                    <html:text property="telephoneExtension" size="10" maxlength="10" />
                                    <et:validationErrors id="errorMessage" property="TelephoneExtension">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.description" />:</td>
                                <td>
                                    <html:text property="description" size="60" maxlength="132" />
                                    <et:validationErrors id="errorMessage" property="Description">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <html:hidden property="partyName" />
                                    <html:hidden property="contactMechanismName" />
                                    <html:hidden property="countryName" />
                                </td>
                                <td><html:submit onclick="onSubmitDisable(this);" />&nbsp;<html:cancel onclick="onSubmitDisable(this);" />&nbsp;<html:reset /><html:hidden property="submitButton" /></td>
                            </tr>
                        </table>
                    </html:form>
                </c:otherwise>
            </c:choose>
        </div>
        <jsp:include page="../../include/entityLock.jsp" />
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>