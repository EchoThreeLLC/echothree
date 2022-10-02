// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.util.server.kafka;

import fish.payara.cloud.connectors.kafka.api.KafkaConnectionFactory;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.kafka.clients.producer.ProducerRecord;

public class KafkaQueueResource {

    private static final String KCF = "java:/KafkaConnectionFactory";

    private static final KafkaQueueResource instance = new KafkaQueueResource();

    private final KafkaConnectionFactory kafkaConnectionFactory;

    @SuppressWarnings("BanJNDI")
    protected KafkaQueueResource() {
        KafkaConnectionFactory kafkaConnectionFactory;

        // This provides a soft failure vs. a hard failure from using @Resource.
        try {
            kafkaConnectionFactory = InitialContext.doLookup(KCF);
        } catch (NamingException ne) {
            kafkaConnectionFactory = null;
        }

        this.kafkaConnectionFactory = kafkaConnectionFactory;
    }

    public static KafkaQueueResource getInstance() {
        return instance;
    }

    public KafkaConnectionFactory getKafkaConnectionFactory() {
        return kafkaConnectionFactory;
    }

    public void test(String bar) {
        try {
            if(kafkaConnectionFactory != null) {
                try(var kafkaConnection = kafkaConnectionFactory.createConnection()) {
                    var future = kafkaConnection.send(new ProducerRecord("my-topic", bar));

                    future.get();
                }
            }
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

}
