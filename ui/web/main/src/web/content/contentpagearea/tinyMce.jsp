<%@ include file="../../include/taglibs.jsp" %>
<%@ include file="../../include/tinyMce.jsp" %>

<et:partyApplicationEditorUse applicationName="main" applicationEditorUseName="ContentPageArea" var="contentPageAreaEditorUse" commandResultVar="unused" scope="request" />

<script type="text/javascript">
    var contentPageAreaClobTAHasEditor = false;
    
    function mimeTypeChoiceChange() {
        <c:if test="${contentPageAreaEditorUse.applicationEditor.editor.editorName == 'TINYMCE'}">
            var choicesObj = document.getElementById("mimeTypeChoices");

            if(choicesObj !== null) {
                var mimeType = choicesObj.options[choicesObj.selectedIndex].value;

                if(mimeType === 'text/html') {
                    tinymce.init($.extend({}, globalTinyMceProperties, { selector : '#contentPageAreaClobTA' }));
                    contentPageAreaClobTAHasEditor = true;
                } else {
                    if(contentPageAreaClobTAHasEditor) {
                        tinymce.remove('#contentPageAreaClobTA');
                        contentPageAreaClobTAHasEditor = false;
                    }
                }
            }
        </c:if>
    }

    function pageLoaded() {
        mimeTypeChoiceChange();
    }
</script>
