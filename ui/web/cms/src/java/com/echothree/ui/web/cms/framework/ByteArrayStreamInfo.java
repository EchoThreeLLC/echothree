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

package com.echothree.ui.web.cms.framework;

import com.echothree.ui.web.cms.framework.CmsBaseDownloadAction.StreamInfo;
import java.io.ByteArrayInputStream;

public class ByteArrayStreamInfo
        implements StreamInfo {
    
    private String contentType;
    private ByteArrayInputStream inputStream;
    private Long lastModified;
    
    private void init(String contentType, ByteArrayInputStream inputStream, Long lastModified) {
        this.contentType = contentType;
        this.inputStream = inputStream;
        this.lastModified = lastModified;
    }

    /** Creates a new instance of ByteArrayStreamInfo */
    public ByteArrayStreamInfo(String contentType, ByteArrayInputStream inputStream) {
        init(contentType, inputStream, null);
    }

    /** Creates a new instance of ByteArrayStreamInfo */
    public ByteArrayStreamInfo(String contentType, ByteArrayInputStream inputStream, Long lastModified) {
        init(contentType, inputStream, lastModified);
    }

    /**
     * Returns the contentType.
     * @return the contentType
     */
    @Override
    public String getContentType() {
        return contentType;
    }

    /**
     * Sets the contentType.
     * @param contentType the contentType to set
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * Returns the inputStream.
     * @return the inputStream
     */
    @Override
    public ByteArrayInputStream getInputStream() {
        return inputStream;
    }

    /**
     * Sets the inputStream.
     * @param inputStream the inputStream to set
     */
    public void setInputStream(ByteArrayInputStream inputStream) {
        this.inputStream = inputStream;
    }

    /**
     * Returns the lastModified.
     * @return the lastModified
     */
    @Override
    public Long getLastModified() {
        return lastModified;
    }

    /**
     * Sets the lastModified.
     * @param lastModified the lastModified to set
     */
    public void setLastModified(Long lastModified) {
        this.lastModified = lastModified;
    }
    
}
