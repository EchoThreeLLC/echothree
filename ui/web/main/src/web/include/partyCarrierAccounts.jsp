<%@ include file="taglibs.jsp" %>

<h2>Carrier Accounts</h2>
<c:url var="addCarrierUrl" value="/action/${commonUrl}/Add">
    <c:param name="PartyName" value="${party.partyName}" />
</c:url>
<p><a href="${addCarrierUrl}">Add Carrier Account.</a></p>
<c:choose>
    <c:when test='${partyCarrierAccounts.size == 0}'>
        <br />
    </c:when>
    <c:otherwise>
        <display:table name="partyCarrierAccounts.list" id="partyCarrierAccount" class="displaytag">
            <display:column titleKey="columnTitle.carrier">
                <c:url var="carrierUrl" value="/action/Shipping/Carrier/Review">
                    <c:param name="CarrierName" value="${partyCarrierAccount.carrier.carrierName}" />
                </c:url>
                <a href="${carrierUrl}"><c:out value="${partyCarrierAccount.carrier.partyGroup.name}" /></a>
            </display:column>
            <display:column titleKey="columnTitle.account">
                <c:out value="${partyCarrierAccount.account}" />
            </display:column>
            <display:column titleKey="columnTitle.alwaysUseThirdPartyBilling">
                <c:choose>
                    <c:when test="${partyCarrierAccount.alwaysUseThirdPartyBilling}">
                        Yes
                    </c:when>
                    <c:otherwise>
                        No
                    </c:otherwise>
                </c:choose>
            </display:column>
            <display:column>
                <c:url var="editUrl" value="/action/${commonUrl}/Edit">
                    <c:param name="PartyName" value="${partyCarrierAccount.party.partyName}" />
                    <c:param name="CarrierName" value="${partyCarrierAccount.carrier.carrierName}" />
                </c:url>
                <a href="${editUrl}">Edit</a>
                <c:url var="deleteUrl" value="/action/${commonUrl}/Delete">
                    <c:param name="PartyName" value="${partyCarrierAccount.party.partyName}" />
                    <c:param name="CarrierName" value="${partyCarrierAccount.carrier.carrierName}" />
                </c:url>
                <a href="${deleteUrl}">Delete</a>
            </display:column>
        </display:table>
    </c:otherwise>
</c:choose>
<br />
