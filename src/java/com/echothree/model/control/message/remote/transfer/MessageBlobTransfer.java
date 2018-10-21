// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.model.control.message.remote.transfer;

import com.echothree.model.control.core.remote.transfer.MimeTypeTransfer;
import com.echothree.model.control.party.remote.transfer.LanguageTransfer;
import com.echothree.util.remote.persistence.type.ByteArray;
import com.echothree.util.remote.transfer.BaseTransfer;

public class MessageBlobTransfer
        extends BaseTransfer {
    
    private MessageTransfer message;
    private LanguageTransfer language;
    private MimeTypeTransfer mimeType;
    private ByteArray blob;
    
    /** Creates a new instance of MessageBlobTransfer */
    public MessageBlobTransfer(MessageTransfer message, LanguageTransfer language, MimeTypeTransfer mimeType,
            ByteArray blob) {
        this.message = message;
        this.language = language;
        this.mimeType = mimeType;
        this.blob = blob;
    }
    
    public MessageTransfer getMessage() {
        return message;
    }
    
    public void setMessage(MessageTransfer message) {
        this.message = message;
    }
    
    public LanguageTransfer getLanguage() {
        return language;
    }
    
    public void setLanguage(LanguageTransfer language) {
        this.language = language;
    }
    
    public MimeTypeTransfer getMimeType() {
        return mimeType;
    }
    
    public void setMimeType(MimeTypeTransfer mimeType) {
        this.mimeType = mimeType;
    }
    
    public ByteArray getBlob() {
        return blob;
    }
    
    public void setBlob(ByteArray blob) {
        this.blob = blob;
    }
    
}
