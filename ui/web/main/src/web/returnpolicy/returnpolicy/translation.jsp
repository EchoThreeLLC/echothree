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
        <title>Return Policy Translations</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/ReturnPolicy/Main" />">Returns</a> &gt;&gt;
                <a href="<c:url value="/action/ReturnPolicy/ReturnKind/Main" />">Return Kinds</a> &gt;&gt;
                <c:url var="returnPoliciesUrl" value="/action/ReturnPolicy/ReturnPolicy/Main">
                    <c:param name="ReturnKindName" value="${returnPolicy.returnKind.returnKindName}" />
                </c:url>
                <a href="${returnPoliciesUrl}">Return Policies</a> &gt;&gt;
                Translations
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/ReturnPolicy/ReturnPolicy/TranslationAdd">
                <c:param name="ReturnKindName" value="${returnPolicy.returnKind.returnKindName}" />
                <c:param name="ReturnPolicyName" value="${returnPolicy.returnPolicyName}" />
            </c:url>
            <p><a href="${addUrl}">Add Translation.</a></p>
            <display:table name="returnPolicyTranslations" id="returnPolicyTranslation" class="displaytag">
                <display:column>
                    <c:url var="reviewUrl" value="/action/ReturnPolicy/ReturnPolicy/TranslationReview">
                        <c:param name="ReturnKindName" value="${returnPolicyTranslation.returnPolicy.returnKind.returnKindName}" />
                        <c:param name="ReturnPolicyName" value="${returnPolicyTranslation.returnPolicy.returnPolicyName}" />
                        <c:param name="LanguageIsoName" value="${returnPolicyTranslation.language.languageIsoName}" />
                    </c:url>
                    <a href="${reviewUrl}">Review</a>
                </display:column>
                <display:column titleKey="columnTitle.language">
                    <c:out value="${returnPolicyTranslation.language.description}" />
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${returnPolicyTranslation.description}" />
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/ReturnPolicy/ReturnPolicy/TranslationEdit">
                        <c:param name="ReturnKindName" value="${returnPolicyTranslation.returnPolicy.returnKind.returnKindName}" />
                        <c:param name="ReturnPolicyName" value="${returnPolicyTranslation.returnPolicy.returnPolicyName}" />
                        <c:param name="LanguageIsoName" value="${returnPolicyTranslation.language.languageIsoName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/ReturnPolicy/ReturnPolicy/TranslationDelete">
                        <c:param name="ReturnKindName" value="${returnPolicyTranslation.returnPolicy.returnKind.returnKindName}" />
                        <c:param name="ReturnPolicyName" value="${returnPolicyTranslation.returnPolicy.returnPolicyName}" />
                        <c:param name="LanguageIsoName" value="${returnPolicyTranslation.language.languageIsoName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
