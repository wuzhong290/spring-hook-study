package com.demo.javabase.collection;

import org.apache.commons.collections.KeyValue;

import java.util.Map;

public class LinkedMapNode {
    protected static final Object NULL = new Object();
    protected transient HashEntry[] data;
    protected transient LinkEntry header;
    protected transient int modCount;
    protected transient int size;

    public LinkedMapNode() {
        this.data = new HashEntry[10];
        init();
    }

    protected void init() {
        header = (LinkEntry) createEntry(null, -1, null, null);
        header.before = header.after = header;
    }

    public Object put(Object key, Object value) {
        key = convertKey(key);
        int hashCode = hash(key);
        int index = hashIndex(hashCode, data.length);
        HashEntry entry = data[index];
        while (entry != null) {
            if (entry.hashCode == hashCode && isEqualKey(key, entry.key)) {
                Object oldValue = entry.getValue();
                updateEntry(entry, value);
                return oldValue;
            }
            entry = entry.next;
        }

        addMapping(index, hashCode, key, value);
        return null;
    }
    protected Object convertKey(Object key) {
        return (key == null ? NULL : key);
    }
    protected int hash(Object key) {
        // same as JDK 1.4
        int h = key.hashCode();
        h += ~(h << 9);
        h ^=  (h >>> 14);
        h +=  (h << 4);
        h ^=  (h >>> 10);
        return h;
    }
    protected int hashIndex(int hashCode, int dataSize) {
        return hashCode & (dataSize - 1);
    }
    protected boolean isEqualKey(Object key1, Object key2) {
        return (key1 == key2 || key1.equals(key2));
    }
    protected void updateEntry(HashEntry entry, Object newValue) {
        moveToMRU((LinkEntry) entry);
        entry.setValue(newValue);
    }
    protected void moveToMRU(LinkEntry entry) {
        if (entry.after != header) {
            modCount++;
            // remove
            entry.before.after = entry.after;
            entry.after.before = entry.before;
            // add first
            entry.after = header;
            entry.before = header.before;
            header.before.after = entry;
            header.before = entry;
        } else if (entry == header) {
            throw new IllegalStateException("Can't move header to MRU" +
                    " (please report this to commons-dev@jakarta.apache.org)");
        }
    }
    protected void addMapping(int hashIndex, int hashCode, Object key, Object value) {
        modCount++;
        HashEntry entry = createEntry(data[hashIndex], hashCode, key, value);
        addEntry(entry, hashIndex);
        size++;
    }
    protected HashEntry createEntry(HashEntry next, int hashCode, Object key, Object value) {
        return new LinkEntry(next, hashCode, key, value);
    }
    protected void addEntry(HashEntry entry, int hashIndex) {
        LinkEntry link = (LinkEntry) entry;
        link.after  = header;
        link.before = header.before;
        header.before.after = link;
        header.before = link;
        data[hashIndex] = entry;
    }

    public Object get(Object key) {
        LinkEntry entry = (LinkEntry) getEntry(key);
        if (entry == null) {
            return null;
        }
        moveToMRU(entry);
        return entry.getValue();
    }

    protected HashEntry getEntry(Object key) {
        key = convertKey(key);
        int hashCode = hash(key);
        HashEntry entry = data[hashIndex(hashCode, data.length)];
        while (entry != null) {
            if (entry.hashCode == hashCode && isEqualKey(key, entry.key)) {
                return entry;
            }
            entry = entry.next;
        }
        return null;
    }

    public Object remove(Object key) {
        key = convertKey(key);
        int hashCode = hash(key);
        int index = hashIndex(hashCode, data.length);
        HashEntry entry = data[index];
        HashEntry previous = null;
        while (entry != null) {
            if (entry.hashCode == hashCode && isEqualKey(key, entry.key)) {
                Object oldValue = entry.getValue();
                removeMapping(entry, index, previous);
                return oldValue;
            }
            previous = entry;
            entry = entry.next;
        }
        return null;
    }

    protected void removeMapping(HashEntry entry, int hashIndex, HashEntry previous) {
        modCount++;
        removeEntry(entry, hashIndex, previous);
        size--;
        destroyEntry(entry);
    }

    protected void removeEntry(HashEntry entry, int hashIndex, HashEntry previous) {
        LinkEntry link = (LinkEntry) entry;
        link.before.after = link.after;
        link.after.before = link.before;
        link.after = null;
        link.before = null;
        if (previous == null) {
            data[hashIndex] = entry.next;
        } else {
            previous.next = entry.next;
        }
    }

    protected static class LinkEntry extends HashEntry {
        protected LinkEntry before;
        protected LinkEntry after;

        protected LinkEntry(HashEntry next, int hashCode, Object key, Object value) {
            super(next, hashCode, key, value);
        }
    }

    protected void destroyEntry(HashEntry entry) {
        entry.next = null;
        entry.key = null;
        entry.value = null;
    }

    protected static class HashEntry implements Map.Entry, KeyValue {
        /** The next entry in the hash chain */
        protected HashEntry next;
        /** The hash code of the key */
        protected int hashCode;
        /** The key */
        protected Object key;
        /** The value */
        protected Object value;

        protected HashEntry(HashEntry next, int hashCode, Object key, Object value) {
            super();
            this.next = next;
            this.hashCode = hashCode;
            this.key = key;
            this.value = value;
        }
        @Override
        public Object getKey() {
            return (key == NULL ? null : key);
        }
        @Override
        public Object getValue() {
            return value;
        }
        @Override
        public Object setValue(Object value) {
            Object old = this.value;
            this.value = value;
            return old;
        }
        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj instanceof Map.Entry == false) {
                return false;
            }
            Map.Entry other = (Map.Entry) obj;
            return
                    (getKey() == null ? other.getKey() == null : getKey().equals(other.getKey())) &&
                            (getValue() == null ? other.getValue() == null : getValue().equals(other.getValue()));
        }
        @Override
        public int hashCode() {
            return (getKey() == null ? 0 : getKey().hashCode()) ^
                    (getValue() == null ? 0 : getValue().hashCode());
        }
        @Override
        public String toString() {
            return new StringBuffer().append(getKey()).append('=').append(getValue()).toString();
        }
    }

    public static void main(String[] args) {
        LinkedMapNode node = new LinkedMapNode();
        node.put("a","aaaa");

        System.out.println(node.get("a"));
        node.remove("a");
        System.out.println(node.get("a"));
    }
}
