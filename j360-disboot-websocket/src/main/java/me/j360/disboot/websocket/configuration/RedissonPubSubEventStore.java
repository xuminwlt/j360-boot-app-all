
package me.j360.disboot.websocket.configuration;

import com.corundumstudio.socketio.store.pubsub.PubSubListener;
import com.corundumstudio.socketio.store.pubsub.PubSubType;
import io.netty.util.internal.PlatformDependent;
import lombok.extern.slf4j.Slf4j;
import me.j360.disboot.websocket.constant.PubSubEventType;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

@Slf4j
public class RedissonPubSubEventStore {

    private final RedissonClient redissonPub;
    private final RedissonClient redissonSub;
    private final Long nodeId;

    private final ConcurrentMap<String, Queue<Integer>> map = PlatformDependent.newConcurrentHashMap();

    public RedissonPubSubEventStore(RedissonClient redissonPub, RedissonClient redissonSub, Long nodeId) {
        this.redissonPub = redissonPub;
        this.redissonSub = redissonSub;
        this.nodeId = nodeId;
    }

    public void publish(PubSubEventType type, DispatchEventMessage msg) {
        msg.setNodeId(nodeId);
        redissonPub.getTopic(type.toString()).publish(msg);
    }

    public <T extends DispatchEventMessage> void subscribe(PubSubEventType type, final PubSubListener<T> listener, Class<T> clazz) {
        String name = type.toString();
        RTopic topic = redissonSub.getTopic(name);
        int regId = topic.addListener(DispatchEventMessage.class, new MessageListener<DispatchEventMessage>() {
            @Override
            public void onMessage(CharSequence channel, DispatchEventMessage msg) {
                if (!nodeId.equals(msg.getNodeId())) {
                    listener.onMessage((T)msg);
                }
            }
        });

        Queue<Integer> list = map.get(name);
        if (list == null) {
            list = new ConcurrentLinkedQueue<Integer>();
            Queue<Integer> oldList = map.putIfAbsent(name, list);
            if (oldList != null) {
                list = oldList;
            }
        }
        list.add(regId);
    }

    public void unsubscribe(PubSubType type) {
        String name = type.toString();
        Queue<Integer> regIds = map.remove(name);
        RTopic topic = redissonSub.getTopic(name);
        for (Integer id : regIds) {
            topic.removeListener(id);
        }
    }

    public void shutdown() {
    }

}
