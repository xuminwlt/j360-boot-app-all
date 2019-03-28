/**
 * Copyright (c) 2012-2019 Nikita Koksharov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.j360.disboot.websocket.configuration;

import com.corundumstudio.socketio.store.RedissonPubSubStore;
import com.corundumstudio.socketio.store.RedissonStore;
import com.corundumstudio.socketio.store.Store;
import com.corundumstudio.socketio.store.pubsub.BaseStoreFactory;
import com.corundumstudio.socketio.store.pubsub.PubSubStore;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;

import java.util.Map;
import java.util.UUID;

public class RedissonStoreFactoryV2 extends BaseStoreFactory {

    private final RedissonClient redisClient;
    private final RedissonClient redisPub;
    private final RedissonClient redisSub;

    private final PubSubStore pubSubStore;
    private final RedissonPubSubEventStore eventStore;

    public RedissonStoreFactoryV2() {
        this(Redisson.create());
    }

    public RedissonStoreFactoryV2(RedissonClient redisson) {
        this.redisClient = redisson;
        this.redisPub = redisson;
        this.redisSub = redisson;

        this.pubSubStore = new RedissonPubSubStore(redisPub, redisSub, getNodeId());
        this.eventStore = new RedissonPubSubEventStore(redisPub, redisSub, getNodeId());
    }

    public RedissonStoreFactoryV2(Redisson redisClient, Redisson redisPub, Redisson redisSub) {
        this.redisClient = redisClient;
        this.redisPub = redisPub;
        this.redisSub = redisSub;

        this.pubSubStore = new RedissonPubSubStore(redisPub, redisSub, getNodeId());
        this.eventStore = new RedissonPubSubEventStore(redisPub, redisSub, getNodeId());
    }

    @Override
    public Store createStore(UUID sessionId) {
        return new RedissonStore(sessionId, redisClient);
    }

    @Override
    public PubSubStore pubSubStore() {
        return pubSubStore;
    }

    public RedissonPubSubEventStore eventStore() {
        return eventStore;
    }

    @Override
    public void shutdown() {
        redisClient.shutdown();
        redisPub.shutdown();
        redisSub.shutdown();
    }

    @Override
    public <K, V> Map<K, V> createMap(String name) {
        return redisClient.getMap(name);
    }

}
