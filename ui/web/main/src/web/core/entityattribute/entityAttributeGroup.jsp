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
        <title>Entity Attribute Groups</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />">Home</a> &gt;&gt;
                <a href="<c:url value="/action/Core/Main" />">Core</a> &gt;&gt;
                <a href="<c:url value="/action/Core/ComponentVendor/Main" />">Component Vendors</a> &gt;&gt;
                <c:url var="entityTypesUrl" value="/action/Core/EntityType/Main">
                    <c:param name="ComponentVendorName" value="${entityAttribute.entityType.componentVendor.componentVendorName}" />
                </c:url>
                <a href="${entityTypesUrl}">Entity Types</a> &gt;&gt;
                <c:url var="entityAttributesUrl" value="/action/Core/EntityAttribute/Main">
                    <c:param name="ComponentVendorName" value="${entityAttribute.entityType.componentVendor.componentVendorName}" />
                    <c:param name="EntityTypeName" value="${entityAttribute.entityType.entityTypeName}" />
                </c:url>
                <a href="${entityAttributesUrl}">Entity Attributes</a> &gt;&gt;
                Groups
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Core/EntityAttribute/EntityAttributeGroupAdd">
                <c:param name="ComponentVendorName" value="${entityAttribute.entityType.componentVendor.componentVendorName}" />
                <c:param name="EntityTypeName" value="${entityAttribute.entityType.entityTypeName}" />
                <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
            </c:url>
            <p><a href="${addUrl}">Add Group.</a></p>
            <display:table name="entityAttributeEntityAttributeGroups" id="entityAttributeEntityAttributeGroup" class="displaytag">
                <display:column titleKey="columnTitle.entityAttributeGroup">
                    <c:out value="${entityAttributeEntityAttributeGroup.entityAttributeGroup.description}" />
                </display:column>
                <display:column titleKey="columnTitle.sortOrder">
                    <c:out value="${entityAttributeEntityAttributeGroup.sortOrder}" />
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/Core/EntityAttribute/EntityAttributeGroupEdit">
                        <c:param name="ComponentVendorName" value="${entityAttributeEntityAttributeGroup.entityAttribute.entityType.componentVendor.componentVendorName}" />
                        <c:param name="EntityTypeName" value="${entityAttributeEntityAttributeGroup.entityAttribute.entityType.entityTypeName}" />
                        <c:param name="EntityAttributeName" value="${entityAttributeEntityAttributeGroup.entityAttribute.entityAttributeName}" />
                        <c:param name="EntityAttributeGroupName" value="${entityAttributeEntityAttributeGroup.entityAttributeGroup.entityAttributeGroupName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/Core/EntityAttribute/EntityAttributeGroupDelete">
                        <c:param name="ComponentVendorName" value="${entityAttributeEntityAttributeGroup.entityAttribute.entityType.componentVendor.componentVendorName}" />
                        <c:param name="EntityTypeName" value="${entityAttributeEntityAttributeGroup.entityAttribute.entityType.entityTypeName}" />
                        <c:param name="EntityAttributeName" value="${entityAttributeEntityAttributeGroup.entityAttribute.entityAttributeName}" />
                        <c:param name="EntityAttributeGroupName" value="${entityAttributeEntityAttributeGroup.entityAttributeGroup.entityAttributeGroupName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
