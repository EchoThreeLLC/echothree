// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// --------------------------------------------------------------------------------

package com.echothree.ui.web.cms.action;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.form.GetEntityBlobAttributeForm;
import com.echothree.control.user.core.common.result.GetEntityBlobAttributeResult;
import com.echothree.model.control.core.common.CoreOptions;
import com.echothree.model.control.core.common.MimeTypes;
import com.echothree.model.control.core.common.transfer.EntityAttributeTypeTransfer;
import com.echothree.model.control.core.common.transfer.EntityBlobAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityTimeTransfer;
import com.echothree.model.control.core.common.transfer.MimeTypeTransfer;
import com.echothree.ui.web.cms.framework.ByteArrayStreamInfo;
import com.echothree.ui.web.cms.framework.CmsBaseDownloadAction;
import com.echothree.ui.web.cms.framework.ParameterConstants;
import com.echothree.ui.web.cms.persistence.CmsCacheBean;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.io.ByteArrayInputStream;
import java.util.HashSet;
import java.util.Set;
import javax.enterprise.inject.spi.Unmanaged;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.infinispan.Cache;

@SproutAction(
    path = "/EntityBlobAttribute",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "any")
    }
)
public class EntityBlobAttributeAction
        extends CmsBaseDownloadAction {

    private static Unmanaged<CmsCacheBean> unmanagedCmsCacheBean = new Unmanaged<>(CmsCacheBean.class);

    private static final String fqnCms = "/com/echothree/ui/web/cms/EntityBlobAttribute";
    private static final String cacheTransferObject = "TransferObject";
    private static final String attributeTransferObject = "TransferObject";

    /** Creates a new instance of EntityBlobAttributeAction */
    public EntityBlobAttributeAction() {
        super(false, false);
    }

    protected static class EntityBlobAttributeNames {

        public String entityRef;
        public String entityAttributeName;
        public String languageIsoName;

        public EntityBlobAttributeNames(HttpServletRequest request) {
            entityRef = request.getParameter(ParameterConstants.ENTITY_REF);
            entityAttributeName = request.getParameter(ParameterConstants.ENTITY_ATTRIBUTE_NAME);
            languageIsoName = request.getParameter(ParameterConstants.LANGUAGE_ISO_NAME);
        }

        public boolean hasAllNames() {
            return entityRef != null && entityAttributeName != null && languageIsoName != null;
        }

    }

    protected EntityBlobAttributeTransfer getEntityBlobAttribute(HttpServletRequest request, EntityBlobAttributeNames entityBlobAttributeNames, Set<String> options)
            throws NamingException {
        GetEntityBlobAttributeForm commandForm = CoreUtil.getHome().getGetEntityBlobAttributeForm();
        EntityBlobAttributeTransfer entityBlobAttribute = null;

        commandForm.setEntityRef(entityBlobAttributeNames.entityRef);
        commandForm.setEntityAttributeName(entityBlobAttributeNames.entityAttributeName);
        commandForm.setLanguageIsoName(entityBlobAttributeNames.languageIsoName);
        commandForm.setReferrer(request.getHeader("Referer"));
        
        commandForm.setOptions(options);

        CommandResult commandResult = CoreUtil.getHome().getEntityBlobAttribute(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetEntityBlobAttributeResult result = (GetEntityBlobAttributeResult)executionResult.getResult();

            entityBlobAttribute = result.getEntityBlobAttribute();
        }

        return entityBlobAttribute;
    }

    private String getFqn(EntityBlobAttributeNames entityBlobAttributeNames) {
        // Build the Fqn from the most general component, to the most specific component. When the maximum cache size is exceeded,
        // it will prune the last component's contents, and that portion of the fqn off the tree. The first two components will
        // continue to exist.
        return new StringBuilder(fqnCms).append('/')
                .append(entityBlobAttributeNames.languageIsoName.toLowerCase()).append('/')
                .append(entityBlobAttributeNames.entityAttributeName.toLowerCase()).append('/')
                .append(entityBlobAttributeNames.entityRef.toLowerCase()).toString();
    }

    private EntityBlobAttributeTransfer getCachedEntityBlobAttribute(Cache<String, Object> cache, String fqn) {
        EntityBlobAttributeTransfer entityBlobAttribute = null;

        if(cache != null && fqn != null) {
            entityBlobAttribute = (EntityBlobAttributeTransfer)cache.get(fqn + "/" + cacheTransferObject);
        }

        return entityBlobAttribute;
    }

    private void putCachedEntityBlobAttribute(Cache<String, Object> cache, String fqn, EntityBlobAttributeTransfer entityBlobAttribute) {
        if(cache != null && fqn != null) {
            cache.put(fqn + "/" + cacheTransferObject, entityBlobAttribute);
        }
    }

    @Override
    protected String getETag(HttpServletRequest request)
            throws NamingException {
        EntityBlobAttributeNames entityBlobAttributeNames = new EntityBlobAttributeNames(request);
        String eTag = null;

        if(entityBlobAttributeNames.hasAllNames()) {
            Unmanaged.UnmanagedInstance<CmsCacheBean> cmsCacheBeanInstance = unmanagedCmsCacheBean.newInstance();
            CmsCacheBean cmsCacheBean = cmsCacheBeanInstance.produce().inject().postConstruct().get();
            Cache<String, Object> cache = cmsCacheBean.getCache();
            String fqn = getFqn(entityBlobAttributeNames);
            EntityBlobAttributeTransfer entityBlobAttribute = getCachedEntityBlobAttribute(cache, fqn);

            if(entityBlobAttribute == null) {
                Set<String> options = new HashSet<>();
                options.add(CoreOptions.EntityBlobAttributeIncludeBlob);
                options.add(CoreOptions.EntityBlobAttributeIncludeETag);

                entityBlobAttribute = getEntityBlobAttribute(request, entityBlobAttributeNames, options);
            }

            if(entityBlobAttribute != null) {
                putCachedEntityBlobAttribute(cache, fqn, entityBlobAttribute);
                request.setAttribute(attributeTransferObject, entityBlobAttribute);

                eTag = entityBlobAttribute.geteTag();
            }

            cmsCacheBeanInstance.preDestroy().dispose();
        }

        return eTag;
    }

    @Override
    protected StreamInfo getStreamInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        StreamInfo streamInfo = null;
        EntityBlobAttributeTransfer entityBlobAttribute = (EntityBlobAttributeTransfer)request.getAttribute(attributeTransferObject);

        if(entityBlobAttribute != null) {
            MimeTypeTransfer mimeType = entityBlobAttribute.getMimeType();
            EntityTimeTransfer entityTime = entityBlobAttribute.getEntityInstance().getEntityTime();
            Long modifiedTime = entityTime.getUnformattedModifiedTime();
            byte bytes[] = entityBlobAttribute.getBlobAttribute().byteArrayValue();

            if(bytes != null) {
                streamInfo = new ByteArrayStreamInfo(mimeType == null ? MimeTypes.TEXT_PLAIN.mimeTypeName() : mimeType.getMimeTypeName(),
                        new ByteArrayInputStream(bytes), modifiedTime == null ? entityTime.getUnformattedCreatedTime() : modifiedTime);
            }
        }

        return streamInfo;
    }
    
}
