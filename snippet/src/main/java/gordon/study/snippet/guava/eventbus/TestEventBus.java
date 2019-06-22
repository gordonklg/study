package gordon.study.snippet.guava.eventbus;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import gordon.study.snippet.guava.eventbus.event.BaseGuavaEvent;
import gordon.study.snippet.guava.eventbus.event.CustomGuavaEvent;

import java.time.LocalDateTime;

public class TestEventBus {

    public static void main(String[] args) {

        EventBus eventBus = new EventBus();

        eventBus.register(new Object() {

            @Subscribe
            public void handle(CustomGuavaEvent customGuavaEvent) {
                System.out.println("Name：" + customGuavaEvent.getName());
            }

            @Subscribe
            public void handleDefault(BaseGuavaEvent event) {
                System.out.println("Base event " + event + ", time = " + event.getTime());
            }

            @Subscribe
            public void handleDead(DeadEvent event) {
                System.out.println("Dead event " + event.toString());
            }

        });
        CustomGuavaEvent event = new CustomGuavaEvent();
        event.setName("Event01");
        eventBus.post(event);
        eventBus.post(new BaseGuavaEvent());
        eventBus.post(new Object());
        System.out.println("exit");
    }
}
