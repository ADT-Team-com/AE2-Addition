package appeng.me;


import appeng.api.networking.IGridNode;
import appeng.api.networking.events.MENetworkEvent;
import appeng.api.networking.events.MENetworkEventSubscribe;
import appeng.core.AELog;

import java.lang.reflect.Method;
import java.util.*;
import java.util.Map.Entry;


public class NetworkEventBus {
    private static final Collection<Class> READ_CLASSES = new HashSet<Class>();
    private static final Map<Class<? extends MENetworkEvent>, Map<Class, MENetworkEventInfo>> EVENTS = new HashMap<Class<? extends MENetworkEvent>, Map<Class, MENetworkEventInfo>>();

    void readClass(final Class listAs, final Class c) {
        if (READ_CLASSES.contains(c)) {
            return;
        }
        READ_CLASSES.add(c);

        try {
            for (final Method m : c.getMethods()) {
                final MENetworkEventSubscribe s = m.getAnnotation(MENetworkEventSubscribe.class);
                if (s != null) {
                    final Class[] types = m.getParameterTypes();
                    if (types.length == 1) {
                        if (MENetworkEvent.class.isAssignableFrom(types[0])) {

                            Map<Class, MENetworkEventInfo> classEvents = EVENTS.get(types[0]);
                            if (classEvents == null) {
                                EVENTS.put(types[0], classEvents = new HashMap<Class, MENetworkEventInfo>());
                            }

                            MENetworkEventInfo thisEvent = classEvents.get(listAs);
                            if (thisEvent == null) {
                                thisEvent = new MENetworkEventInfo();
                            }

                            thisEvent.Add(types[0], c, m);

                            classEvents.put(listAs, thisEvent);
                        } else {
                            throw new IllegalStateException("Invalid ME Network Event Subscriber, " + m.getName() + "s Parameter must extend MENetworkEvent.");
                        }
                    } else {
                        throw new IllegalStateException("Invalid ME Network Event Subscriber, " + m.getName() + " must have exactly 1 parameter.");
                    }
                }
            }
        } catch (final Throwable t) {
            throw new IllegalStateException("Error while adding " + c.getName() + " to event bus", t);
        }
    }

    MENetworkEvent postEvent(final Grid g, final MENetworkEvent e) {
        final Map<Class, MENetworkEventInfo> subscribers = EVENTS.get(e.getClass());
        int x = 0;

        try {
            if (subscribers != null) {
                for (final Entry<Class, MENetworkEventInfo> subscriber : subscribers.entrySet()) {
                    final MENetworkEventInfo target = subscriber.getValue();
                    final GridCacheWrapper cache = g.getCaches().get(subscriber.getKey());
                    if (cache != null) {
                        x++;
                        target.invoke(cache.getCache(), e);
                    }

                    for (final IGridNode obj : g.getMachines(subscriber.getKey())) {
                        x++;
                        target.invoke(obj.getMachine(), e);
                    }
                }
            }
        } catch (final NetworkEventDone done) {
            // Early out.
        }

        e.setVisitedObjects(x);
        return e;
    }

    MENetworkEvent postEventTo(final Grid grid, final GridNode node, final MENetworkEvent e) {
        final Map<Class, MENetworkEventInfo> subscribers = EVENTS.get(e.getClass());
        int x = 0;

        try {
            if (subscribers != null) {
                final MENetworkEventInfo target = subscribers.get(node.getMachineClass());
                if (target != null) {
                    x++;
                    target.invoke(node.getMachine(), e);
                }
            }
        } catch (final NetworkEventDone done) {
            // Early out.
        }

        e.setVisitedObjects(x);
        return e;
    }

    public static class NetworkEventDone extends Throwable {

        private static final long serialVersionUID = -3079021487019171205L;
    }

    public class EventMethod {

        private final Class objClass;
        private final Method objMethod;
        private final Class objEvent;

        public EventMethod(final Class Event, final Class ObjClass, final Method ObjMethod) {
            this.objClass = ObjClass;
            this.objMethod = ObjMethod;
            this.objEvent = Event;
        }

        private void invoke(final Object obj, final MENetworkEvent e) throws NetworkEventDone {
            try {
                this.objMethod.invoke(obj, e);
            } catch (final Throwable e1) {
                AELog.error("[AppEng] Network Event caused exception:");
                AELog.error("Offending Class: " + obj.getClass().getName());
                AELog.error("Offending Object: " + obj);
                AELog.debug(e1);
                throw new IllegalStateException(e1);
            }

            if (e.isCanceled()) {
                throw new NetworkEventDone();
            }
        }
    }

    public class MENetworkEventInfo {

        private final List<EventMethod> methods = new ArrayList<EventMethod>();

        private void Add(final Class Event, final Class ObjClass, final Method ObjMethod) {
            this.methods.add(new EventMethod(Event, ObjClass, ObjMethod));
        }

        private void invoke(final Object obj, final MENetworkEvent e) throws NetworkEventDone {
            for (final EventMethod em : this.methods) {
                em.invoke(obj, e);
            }
        }
    }
}